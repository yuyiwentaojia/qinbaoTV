package com.iqinbao.android.songstv.videocache;

import android.text.TextUtils;

import com.iqinbao.android.songstv.videocache.file.FileCache;
import com.iqinbao.android.songstv.videocache.file.MyLog;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * {@link ProxyCache} that read http url and writes data to {@link Socket}
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
class HttpProxyCache extends ProxyCache {

    private static final float NO_CACHE_BARRIER = .2f;

    private final HttpUrlSource source;
    private final FileCache cache;
    private CacheListener listener;

    public HttpProxyCache(HttpUrlSource source, FileCache cache) {
        super(source, cache);
        this.cache = cache;
        this.source = source;
    }

    public void registerCacheListener(CacheListener cacheListener) {
        this.listener = cacheListener;
    }

    /**
     * HttpProxyCache 请求
     * @param request
     * @param socket
     * @throws IOException
     * @throws ProxyCacheException
     */
    public void processRequest(GetRequest request, Socket socket) throws IOException, ProxyCacheException {
        OutputStream out = new BufferedOutputStream(socket.getOutputStream());
        String responseHeaders = newResponseHeaders(request);
        out.write(responseHeaders.getBytes("UTF-8"));

        long offset = request.rangeOffset;
        if (isUseCache(request)) {
            responseWithCache(out, offset);
            MyLog.d(ProxyCacheUtils.LOG_TAG, "responseWithCache");
        } else {
            responseWithoutCache(out, offset);
            MyLog.d(ProxyCacheUtils.LOG_TAG, "responseWithCache");
        }
    }

    private boolean isUseCache(GetRequest request) throws ProxyCacheException {
        int sourceLength = source.length();
        boolean sourceLengthKnown = sourceLength > 0;
        int cacheAvailable = cache.available();
        // do not use cache for partial requests which too far from available cache. It seems user seek video.
        return !sourceLengthKnown || !request.partial || request.rangeOffset <= cacheAvailable + sourceLength * NO_CACHE_BARRIER;
    }

    /**
     * 返回响应头
     * @param request
     * @return
     * @throws IOException
     * @throws ProxyCacheException
     */
    private String newResponseHeaders(GetRequest request) throws IOException, ProxyCacheException {
        String mime = source.getMime();
        boolean mimeKnown = !TextUtils.isEmpty(mime);
        int length = cache.isCompleted() ? cache.available() : source.length();
        boolean lengthKnown = length >= 0;
        long contentLength = request.partial ? length - request.rangeOffset : length;
        boolean addRange = lengthKnown && request.partial;
        return new StringBuilder()
                .append(request.partial ? "HTTP/1.1 206 PARTIAL CONTENT\n" : "HTTP/1.1 200 OK\n")
                .append("Accept-Ranges: bytes\n")
                .append(lengthKnown ? String.format("Content-Length: %d\n", contentLength) : "")
                .append(addRange ? String.format("Content-Range: bytes %d-%d/%d\n", request.rangeOffset, length - 1, length) : "")
                .append(mimeKnown ? String.format("Content-Type: %s\n", mime) : "")
                .append("\n") // headers end
                .toString();
    }

    private void responseWithCache(OutputStream out, long offset) throws ProxyCacheException, IOException {
        byte[] buffer = new byte[ProxyCacheUtils.DEFAULT_BUFFER_SIZE];
        int readBytes;
        if (!cache.isCompleted() && cache.available() != 0 ){
            MyLog.d("xxa", "source.length = " + source.length());
            MyLog.d("xxa", "cache.available = " + cache.available() );
            onCacheAvailable(cache.available(), source.length());
        }
        while ((readBytes = read(buffer, offset, buffer.length)) != -1) {
            out.write(buffer, 0, readBytes);
            offset += readBytes;
        }
        out.flush();
    }

    private void responseWithoutCache(OutputStream out, long offset) throws ProxyCacheException, IOException {
        try {
            HttpUrlSource source = new HttpUrlSource(this.source);
            source.open((int) offset);
            byte[] buffer = new byte[ProxyCacheUtils.DEFAULT_BUFFER_SIZE];
            int readBytes;
            while ((readBytes = source.read(buffer)) != -1) {
                out.write(buffer, 0, readBytes);
                offset += readBytes;
            }
            out.flush();
        } finally {
//            当MediaPlayer有多个请求时，HttpProxyCache.processRequest()方法会分别运行在多个线程中，
//            在HttpProxyCache.responseWithoutCache()方法的finally语句块则会导致其他线程ProxyCache.readSource()方法出错。
//            而此时HttpProxyCache.responseWithoutCache()方法中的source.close()则会导致父类ProxyCache中的source被close，
// 而导致ProxyCache.readSource()方法抛出异常，进而导致MediaPlayer的一个请求线程出错，
// 最后的结果是MediaPlayer出错，触发OnErrorListener。解决办法则是修改HttpProxyCache.responseWithoutCache()方法，关闭try语句块中的source。
            source.close();
        }
    }

    @Override
    protected void onCachePercentsAvailableChanged(int percents) {
        if (listener != null) {
            listener.onCacheAvailable(cache.file, source.url, percents);
        }
    }
}
