package com.iqinbao.android.songstv.task;

public interface AsyncUpdate {

	public static final int SUCCESS = 1;
	public static final int FAIL = 0;
	public static final int CANCEL = 2;
	public static final int PWD_USERNAME = 3;
	public static final int NO_USERNAME = 4;
	public static final int ERROR = 5;// 错误
	public static final int NO_MESSAGE = 6;// 没有相关信息
	public static final int NO_NETWORK = 7;// 没有网络

	public void updateViews(final int asyncUpdateType, final int result);

}
