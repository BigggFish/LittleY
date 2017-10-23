package com.bigggfish.littley.util;

import android.widget.Toast;

public class ToastUtils {
	
	private static Toast mToast;
	
	/**
	 * 显示Toast
	 */
	public static void showToast(String text) {
		if("".equals(text))
			return;
		if(mToast == null) {
			mToast = Toast.makeText(Global.context.getApplicationContext()  , text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	public static void showLongToast(String text) {
		if("".equals(text))
			return;
		if(mToast == null) {
			mToast = Toast.makeText(Global.context.getApplicationContext(), text, Toast.LENGTH_LONG);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_LONG);
		}
		mToast.show();
	}

}
