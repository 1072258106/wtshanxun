package cn.mandroid.wtshanxun.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MLog {
	private static boolean display = false;
	public static void i(Object log) {
		if (display) {
			Log.i(Const.LOG_TAG, log + "");
		}

	}
	public static void v(String log) {
		if (display) {
			Log.v(Const.LOG_TAG, log);
		}

	}

	public static void d(String log) {
		if (display) {
			Log.d(Const.LOG_TAG, log);
		}

	}

	public static void e(String log) {
		if (display) {
			Log.e(Const.LOG_TAG, log);
		}

	}

	public static void w(String log) {
		if (display) {
			Log.w(Const.LOG_TAG, log);
		}

	}
}
