package com.bigggfish.littley.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;

/**
 * Log日志输出控制类 。debug控制日志输出，可打印到sd卡。封版后关闭调试日志。
 * log文件每天产生一个，存在sd卡中软件目录/log/ 中
 * @author zhulx
 */
public class Log
{
	private final static String TAG = Log.class.getSimpleName();
	private final static String LOG_PATH = "";


	public static boolean DEBUG = true;
	private static boolean LOG2FILE = false;
	private static File file;
	private static String processName;
	static
	{
		if (LOG2FILE)
		{
			processName = getCurProcessName(Global.context);
			String sdStatus = Environment.getExternalStorageState();
			if (!sdStatus.equals(Environment.MEDIA_MOUNTED))
			{
				android.util.Log.e(TAG, "SD card is not avaiable/writeable right now.");
				LOG2FILE = false;
			}
			File path = new File(Constant.LOG_PATH);
			if (!path.exists())
			{
				android.util.Log.i(TAG, "Create log path:" + Constant.LOG_PATH);
				path.mkdirs();
			}
			String fileName = "log" + TimeUtil.getStringByFormat(System.currentTimeMillis(), "yyyyMMdd") + processName + ".txt";
			file = new File(Constant.LOG_PATH + File.separator + fileName);
			if (!file.exists())
			{
				android.util.Log.i(TAG, "Create log file:" + fileName);
				try
				{
					file.createNewFile();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public static void init(boolean debug) {
		DEBUG = debug;

	}

	public static void i(String s, String s1)
	{
		i(s, s1, true);
	}

	/**
	 * 指明info日志是否要保存到sd卡，默认不保存
	 *
	 * @param s
	 *            日志标签
	 * @param s1
	 *            日志内容
	 * @param saveToSdcard
	 *            是否要保存到sd卡标志
	 */
	public static void i(String s, String s1, boolean saveToSdcard)
	{
		if (DEBUG)
		{
			if (s1 == null)
			{
				android.util.Log.i(s, "null");
			}
			else
			{
				android.util.Log.i(s, s1);
			}
		}
		if (LOG2FILE && saveToSdcard)
		{
			writeFileToSD(s, s1);
		}
	}

	public static void w(String s, String s1)
	{
		w(s, s1, true);
	}

	/**
	 * 指明warn日志是否要保存到sd卡，默认不保存
	 *
	 * @param s
	 *            日志标签
	 * @param s1
	 *            日志内容
	 * @param saveToSdcard
	 *            是否要保存到sd卡标志
	 */
	public static void w(String s, String s1, boolean saveToSdcard)
	{
		if (DEBUG)
		{
			if (s1 == null)
			{
				android.util.Log.w(s, "null");
			}
			else
			{
				android.util.Log.w(s, s1);
			}
		}
		if (LOG2FILE && saveToSdcard)
		{
			writeFileToSD(s, s1);
		}
	}

	public static void v(String s, String s1)
	{
		v(s, s1, true);
	}

	/**
	 * 指明verbose日志是否要保存到sd卡，默认不保存
	 *
	 * @param s
	 *            日志标签
	 * @param s1
	 *            日志内容
	 * @param saveToSdcard
	 *            是否要保存到sd卡标志
	 */
	public static void v(String s, String s1, boolean saveToSdcard)
	{
		if (DEBUG)
		{
			if (s1 == null)
			{
				android.util.Log.v(s, "null");
			}
			else
			{
				android.util.Log.v(s, s1);
			}
		}
		if (LOG2FILE && saveToSdcard)
		{
			writeFileToSD(s, s1);
		}
	}

	public static void e(String s, String s1)
	{
		e(s, s1, true);
	}

	/**
	 * 指明error日志是否要保存到sd卡，默认不保存
	 *
	 * @param s
	 *            日志标签
	 * @param s1
	 *            日志内容
	 * @param saveToSdcard
	 *            是否要保存到sd卡标志
	 */
	public static void e(String s, String s1, boolean saveToSdcard)
	{
		if (DEBUG)
		{
			if (s1 == null)
			{
				android.util.Log.e(s, "null");
			}
			else
			{
				android.util.Log.e(s, s1);
			}
		}
		if (LOG2FILE && saveToSdcard)
		{
			writeFileToSD(s, s1);
		}
	}

	public static void e(String s, String s1, Throwable r) {
		if(DEBUG) {
			android.util.Log.e(s, s1, r);
		}
	}

	public static void d(String s, String s1)
	{
		d(s, s1, true);
	}

	/**
	 * 指明debug日志是否要保存到sd卡，默认不保存
	 *
	 * @param s
	 *            日志标签
	 * @param s1
	 *            日志内容
	 * @param saveToSdcard
	 *            是否要保存到sd卡标志
	 */
	public static void d(String s, String s1, boolean saveToSdcard)
	{
		if (DEBUG)
		{
			if (s1 == null)
			{
				android.util.Log.d(s, "null");
			}
			else
			{
				android.util.Log.d(s, s1);
			}
		}
		if (LOG2FILE && saveToSdcard)
		{
			writeFileToSD(s, s1);
		}
	}

	/**
	 * 写文件到sd卡上
	 */
	public synchronized static void writeFileToSD(String s, String s1)
	{
		StringBuilder content = new StringBuilder();
		Calendar c = Calendar.getInstance();

		StringBuilder nameStr = new StringBuilder();
		StringBuilder timeStr = new StringBuilder();
		timeStr.append(c.get(Calendar.YEAR)).append("-")
				.append(c.get(Calendar.MONTH)+1).append("-")
				.append(c.get(Calendar.DAY_OF_MONTH)).append(" ")
				.append(c.get(Calendar.HOUR_OF_DAY)).append(":")
				.append(c.get(Calendar.MINUTE)<10?"0"+c.get(Calendar.MINUTE):c.get(Calendar.MINUTE)).append(":")
				.append(c.get(Calendar.SECOND)<10?"0"+c.get(Calendar.SECOND):c.get(Calendar.SECOND)).append(".")
                .append(c.get(Calendar.MILLISECOND));

		if (s1 == null)
		{
			content.append(timeStr).append("\t").append(s).append("\r\n");
		}
		else
		{
			content.append(timeStr).append("\t").append(s).append("\t").append(s1).append("\r\n");
		}

		try
		{
			if(!file.exists()) {
				File path = new File(Constant.LOG_PATH);
				if (!path.exists())
				{
					android.util.Log.d(TAG, "Create log path:" + Constant.LOG_PATH);
					path.mkdirs();
				}
				file.createNewFile();
			} else { //每天生成一个日志文件
				int month = c.get(Calendar.MONTH)+1;
				int day = c.get(Calendar.DAY_OF_MONTH);
				nameStr.append(c.get(Calendar.YEAR))
						.append(month < 10 ? ("0" + month) : month)
						.append(day < 10 ? ("0" + day) : day);
				if (processName == null) {
					processName = getCurProcessName(Global.context);
				}
				String fileName = "log" + nameStr.toString() + processName + ".txt";
				if(!file.getName().contains(nameStr.toString())) {
					android.util.Log.d(TAG, "Create log:" + fileName);
					file = new File(Constant.LOG_PATH + File.separator + fileName);
					file.createNewFile();
				}
			}
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			raf.seek(file.length());
			raf.write(content.toString().getBytes());
			raf.close();
		}
		catch (Exception e)
		{
            android.util.Log.i(TAG, file.toString());
			android.util.Log.e(TAG, "Error on writeLogToSD. Error msg:" + e.getMessage());
//			e.printStackTrace();
		}
	}

	private static String getCurProcessName(Context context) {
		if (context == null) {
			android.util.Log.e(TAG, "no context when init log");
			return "";
		}
		int pid = android.os.Process.myPid();
		ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
			if (appProcess.pid == pid) {
				String processName = appProcess.processName;
				String names[] = processName.split(":");
				return names[names.length - 1];
			}
		}
		return "";
	}
}
