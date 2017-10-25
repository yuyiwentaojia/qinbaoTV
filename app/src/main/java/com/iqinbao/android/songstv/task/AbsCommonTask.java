package com.iqinbao.android.songstv.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;

import com.iqinbao.android.songstv.R;

public abstract class AbsCommonTask extends AsyncTask<Object, Integer, Integer> {

	public static final int SUCCESS = 1;
	public static final int FAIL = 0;
	public static final int CANCEL = 2;
	public static final int PWD_USERNAME = 3;
	public static final int NO_USERNAME = 4;
	public static final int ERROR = 5;// 错误
	public static final int NO_MESSAGE = 6;// 没有相关信息
	public static final int NO_NETWORK = 7;// 没有网络

	protected Context mContext;
	private ProgressDialog progressDialog;
	private boolean isShowProgressDialog = false;
	private int progressMessage = R.string.loading;

	protected AsyncUpdate asyncUpdate;
	private int asyncUpdateType;

	private static String appkey;
	private static String appSecret;
	private static String url;
//	public static ObjectClient client;

	public AbsCommonTask(AsyncUpdate asyncUpdate, Context mContext,
			int asyncUpdateType) {
		this.asyncUpdate = asyncUpdate;
		this.asyncUpdateType = asyncUpdateType;
		this.mContext = mContext;

		appkey = "com.bao.song";
		appSecret = "com.bao.song";
		url = "";//this.mContext.getString(R.string.client_url);
//		client = new DefaultClient(url, appkey, appSecret, mContext, true);// 实例化TopClient类
	}

	@Override
	protected void onPostExecute(Integer result) {
		asyncUpdate.updateViews(asyncUpdateType, result);
		if (progressDialog != null && progressDialog.isShowing()
				&& isShowProgressDialog) {
				progressDialog.dismiss();
		}
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {

		if (isShowProgressDialog) {
			progressDialog = new ProgressDialog(mContext);
			progressDialog.setMessage(mContext.getText(progressMessage));
			progressDialog.setCancelable(false);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					AbsCommonTask.this.cancel(true);
				}
			});
			progressDialog.show();
		}
		super.onPreExecute();
	}

	public int getProgressMessage() {
		return progressMessage;
	}

	public void setProgressMessage(int progressMessage) {
		this.progressMessage = progressMessage;
	}

	public boolean isShowProgressDialog() {
		return isShowProgressDialog;
	}

	public void setShowProgressDialog(boolean isShowProgressDialog) {
		this.isShowProgressDialog = isShowProgressDialog;
	}



}
