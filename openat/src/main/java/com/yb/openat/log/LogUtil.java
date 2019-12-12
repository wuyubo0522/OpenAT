package com.yb.openat.log;

import android.text.TextUtils;
import android.util.Log;

import java.util.List;

/**
 * 类说明：Logcat工具类，帮助开发者更快捷在控制台输出日志
 *
 * @author 裕博
 * Time: 2019/12/12 10:24
 */
public class LogUtil {

    /** 日志输出级别V */
    public static final int LEVEL_VERBOSE = 1;
    /** 日志输出级别D */
    public static final int LEVEL_DEBUG = 2;
    /** 日志输出级别I */
    public static final int LEVEL_INFO = 3;
    /** 日志输出级别W */
    public static final int LEVEL_WARN = 4;
    /** 日志输出级别E */
    public static final int LEVEL_ERROR = 5;

    /** 日志输出时的TAG */
    public static String mTag = "Log日志";
    /** 是否允许输出log */
    private static int mDebuggable = LEVEL_ERROR;

    /** 用于记时的变量 */
    private static long mTimestamp = 0;
    /** 写文件的锁对象 */
    private static final Object mLogLock = new Object();

    public static void init(int level){
        mDebuggable = level;
    }

    /** 以级别为 v 的形式输出LOG */
    public static void v(String msg) {
        if (mDebuggable >= LEVEL_VERBOSE) {
            Log.v(mTag, msg);
        }
    }

    /** 以级别为 d 的形式输出LOG */
    public static void d(String msg) {
        if (mDebuggable >= LEVEL_DEBUG) {
            Log.d(mTag, msg);
        }
    }

    /** 以级别为 i 的形式输出LOG */
    public static void i(String msg) {
        if (mDebuggable >= LEVEL_INFO) {
            Log.i(mTag, msg);
        }
    }

    /** 以级别为 w 的形式输出LOG */
    public static void w(String msg) {
        if (mDebuggable >= LEVEL_WARN) {
            Log.w(mTag, msg);
        }
    }

    /** 以级别为 w 的形式输出Throwable */
    public static void w(Throwable tr) {
        if (mDebuggable >= LEVEL_WARN) {
            Log.w(mTag, "", tr);
        }
    }

    /**
     * 格式化时间戳
     * @param millis
     */
    public  static  void time(long millis){
        int h = (int) (millis / (1000 * 60 * 60));
        int m = (int) (millis/ (1000 * 60) % 60);
        int s = (int) (millis % (1000 * 60) / 1000);
        String hour = h < 10 ? "0" + h : h + "";
        String minute = m < 10 ? "0" + m : m + "";
        String second = s < 10 ? "0" + s : s + "";
        d(String.format(" millis: %s  格式化时间:%s时 %s分 %s秒",millis+"",hour,minute,second));
    }

    /** 以级别为 w 的形式输出LOG信息和Throwable */
    public static void w(String msg, Throwable tr) {
        if (mDebuggable >= LEVEL_WARN && null != msg) {
            Log.w(mTag, msg, tr);
        }
    }

    /** 以级别为 e 的形式输出LOG */
    public static void e(String msg) {
        if (mDebuggable >= LEVEL_ERROR) {
            Log.e(mTag, msg);
        }
    }

    /** 以级别为 e 的形式输出Throwable */
    public static void e(Throwable tr) {
        if (mDebuggable >= LEVEL_ERROR) {
            Log.e(mTag, "", tr);
        }
    }

    /** 以级别为 e 的形式输出LOG信息和Throwable */
    public static void e(String msg, Throwable tr) {
        if (mDebuggable >= LEVEL_ERROR && null != msg) {
            Log.e(mTag, msg, tr);
        }
    }

    /**
     * 以级别为 e 的形式输出msg信息,附带时间戳，用于输出一个时间段起始点
     * @param msg 需要输出的msg
     */
    public static void msgStartTime(String msg) {
        mTimestamp = System.currentTimeMillis();
        if (!TextUtils.isEmpty(msg)) {
            e("[Started：" + mTimestamp + "]" + msg);
        }
    }

    /** 以级别为 e 的形式输出msg信息,附带时间戳，用于输出一个时间段结束点* @param msg 需要输出的msg */
    public static void elapsed(String msg) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - mTimestamp;
        mTimestamp = currentTime;
        e("[Elapsed：" + elapsedTime + "]" + msg);
    }

    public static <T> void printList(List<T> list) {
        if (list == null || list.size() < 1) {
            return;
        }
        int size = list.size();
        i("---begin---");
        for (int i = 0; i < size; i++) {
            i(i + ":" + list.get(i).toString());
        }
        i("---end---");
    }

    public static <T> void printArray(T[] array) {
        if (array == null || array.length < 1) {
            return;
        }
        int length = array.length;
        i("---begin---");
        for (int i = 0; i < length; i++) {
            i(i + ":" + array[i].toString());
        }
        i("---end---");
    }

    public static void errorMsg(String msg){
        mTag = "错误信息";
        e(msg);
    }

    public static void printStackTrace(Exception e) {
        if(mDebuggable > 1 && e != null) e.printStackTrace();
    }
}
