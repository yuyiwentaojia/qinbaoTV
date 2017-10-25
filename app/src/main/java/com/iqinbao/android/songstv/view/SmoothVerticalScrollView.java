package com.iqinbao.android.songstv.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2016/9/6.
 */
public class SmoothVerticalScrollView extends ScrollView {
    final String TAG = "SmoothVerticalScrollView";
    private boolean isScrolledToTop = true;// 初始化的时候设置一下值
    private boolean isScrolledToBottom = false;

    private ISmartScrollChangedListener mSmartScrollChangedListener;
    private int key_up_down_num = 100;

    /** 定义监听接口 */
    public interface ISmartScrollChangedListener {
        void onScrolledToBottom();
        void onScrolledToTop();
    }

    public SmoothVerticalScrollView(Context context) {
        this(context, null, 0);
    }
    public SmoothVerticalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }
    public SmoothVerticalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    void init(){
    }

    @Override
    public void scrollBy(int dx, int dy) {
        super.scrollBy(dx, dy);
    }

    @Override
    public void computeScroll(){
        super.computeScroll();
    }


    private int _index = -1;
    public void setTabIndex(int index){
        _index = index;
    }

    private int _tabcount = -1;
    public void setTabCount(int _count){
        _tabcount = _count;
    }

    private long mPreKeytime = 0;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        View currentFocused = findFocus();
        if (currentFocused == this) currentFocused = null;

        if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP && event.getAction() == KeyEvent.ACTION_DOWN){
//            long time = System.currentTimeMillis();
//            if (time - mPreKeytime < 500) {
//                return true;
//            }
//            mPreKeytime = System.currentTimeMillis();

            View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused, View.FOCUS_UP);
            if(nextFocused == null){
                Log.d(TAG, "no move to up");
                return true;
            }
            else{
                key_up_down_num = 101;
                Log.d(TAG, "move to up");
            }
        }
        else if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN && event.getAction() == KeyEvent.ACTION_DOWN) {
//            long time = System.currentTimeMillis();
//            if (time - mPreKeytime < 500) {
//                return true;
//            }
//            mPreKeytime = System.currentTimeMillis();

            View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused, View.FOCUS_DOWN);
            if (nextFocused == null) {
                Log.d(TAG, "no move to down");
                return true;
            }
            else{
                key_up_down_num = 102;
                Log.d(TAG, "move to down");
            }
        }

        boolean ret = super.dispatchKeyEvent(event);
        if(ret == true && (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN && event.getAction() == KeyEvent.ACTION_DOWN ||
                event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP && event.getAction() == KeyEvent.ACTION_DOWN)){
        }
        return ret;
    }

    //onOverScrolled


    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        Log.d(TAG, "====onOverScrolled==="+scrollY);
//        if(key_up_down_num == 101){
//            scrollY = scrollY - 30;
//        }
//        else if(key_up_down_num == 102){
//            scrollY = scrollY + 30;
//        }
       super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (scrollY == 0) {
            isScrolledToTop = clampedY;
            isScrolledToBottom = false;
//            System.out.println("onOverScrolled isScrolledToTop:" + isScrolledToTop);
        } else {
            isScrolledToTop = false;
            isScrolledToBottom = clampedY;
//            System.out.println("onOverScrolled isScrolledToBottom:" + isScrolledToBottom);
        }
        notifyScrollChangedListeners();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        if (android.os.Build.VERSION.SDK_INT < 9) {  // API 9及之后走onOverScrolled方法监听
//            if (getScrollY() == 0) {    // 小心踩坑1: 这里不能是getScrollY() <= 0
//                isScrolledToTop = true;
//                isScrolledToBottom = false;
//            } else if (getScrollY() + getHeight() - getPaddingTop()-getPaddingBottom() == getChildAt(0).getHeight()) {
//                // 小心踩坑2: 这里不能是 >=
//                // 小心踩坑3（可能忽视的细节2）：这里最容易忽视的就是ScrollView上下的padding　
//                isScrolledToBottom = true;
//                isScrolledToTop = false;
//            } else {
//                isScrolledToTop = false;
//                isScrolledToBottom = false;
//            }
//            notifyScrollChangedListeners();
//        }
        // 这个log可以研究ScrollView的上下padding对结果的影响
//        System.out.println("onScrollChanged getScrollY():" + getScrollY() + " t: " + t + " paddingTop: " + getPaddingTop());
        if (getScrollY() == 0) {
            isScrolledToTop = true;
            isScrolledToBottom = false;
//            System.out.println("onScrollChanged isScrolledToTop:" + isScrolledToTop);
        } else if (getScrollY() + getHeight() - getPaddingTop() - getPaddingBottom() == getChildAt(0).getHeight()) {
            isScrolledToBottom = true;
//            System.out.println("onScrollChanged isScrolledToBottom:" + isScrolledToBottom);
            isScrolledToTop = false;
        } else {
            isScrolledToTop = false;
            isScrolledToBottom = false;
        }
        notifyScrollChangedListeners();
        // 有时候写代码习惯了，为了兼容一些边界奇葩情况，上面的代码就会写成<=,>=的情况，结果就出bug了
        // 我写的时候写成这样：getScrollY() + getHeight() >= getChildAt(0).getHeight()
        // 结果发现快滑动到底部但是还没到时，会发现上面的条件成立了，导致判断错误
        // 原因：getScrollY()值不是绝对靠谱的，它会超过边界值，但是它自己会恢复正确，导致上面的计算条件不成立
        // 仔细想想也感觉想得通，系统的ScrollView在处理滚动的时候动态计算那个scrollY的时候也会出现超过边界再修正的情况
    }

    private void notifyScrollChangedListeners() {
        if (isScrolledToTop) {
            if (mSmartScrollChangedListener != null) {
                mSmartScrollChangedListener.onScrolledToTop();
            }
        } else if (isScrolledToBottom) {
            if (mSmartScrollChangedListener != null) {
                mSmartScrollChangedListener.onScrolledToBottom();
            }
        }
    }

    public boolean isScrolledToTop() {
        return isScrolledToTop;
    }

    public boolean isScrolledToBottom() {
        return isScrolledToBottom;
    }
//
//    @Override
//    protected boolean onRequestFocusInDescendants(int direction,
//                                                  Rect previouslyFocusedRect) {
//
//        // convert from forward / backward notation to up / down / left / right
//        // (ugh).
//
//        if(previouslyFocusedRect != null){
//            if (direction == View.FOCUS_FORWARD) {
//                direction = View.FOCUS_DOWN;//.FOCUS_RIGHT;
//            } else if (direction == View.FOCUS_BACKWARD) {
//                direction = View.FOCUS_UP;
//            }
//            View nextFocus = FocusFinder.getInstance().findNextFocusFromRect(this,
//                    previouslyFocusedRect, direction);
//            if (nextFocus == null) {
//                return false;
//            }
//            return nextFocus.requestFocus(direction, previouslyFocusedRect);
//        }else{
//            int index;
//            int increment;
//            int end;
//            int count = this.getChildCount();
//            if ((direction & FOCUS_FORWARD) != 0) {
//                index = 0;
//                increment = 1;
//                end = count;
//            } else {
//                index = count - 1;
//                increment = -1;
//                end = -1;
//            }
//            for (int i = index; i != end; i += increment) {
//                View child = this.getChildAt(i);
//                if (child.getVisibility()==View.VISIBLE) {
//                    if (child.requestFocus(direction, previouslyFocusedRect)) {
//                        return true;
//                    }
//                }
//            }
//            return false;
//        }
//    }
}
