package com.logger.min.easylogger;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @author : hafiq on 18/01/2017.
 */

public class Logger {

    Logger(){}

    public static void init(Context context){
        LogPrint.setContext(context);
    }

    public static void initTag(Context context, String tag){
        LogPrint.setTag(context,tag);
    }

    public static void initTag(String tag){
        LogPrint.setTag(tag);
    }

    public static String getTag(){
        return LogPrint.getTag();
    }

    public static void printTag(){
        LogPrint.d(LogPrint.getTag());
    }

    public static void canShowLog(boolean show){
        LogPrint.canShowLog(show);
    }


    public static void info(String tag,String message){
        LogPrint.setTag(tag);
        info(message);
    }

    public static void info(String message){
        LogPrint.i(message);
    }

    public static void info(Object message){
        LogPrint.print(message,LogLevel.INFO);
    }

    public static void info(Object message, Format format){
        LogPrint.print(message,format,LogLevel.INFO);
    }



    public static void debug(String tag,String message){
        LogPrint.setTag(tag);
        debug(message);
    }

    public static void debug(String message){
        LogPrint.d(message);
    }

    public static void debug(Object message){
        LogPrint.print(message,LogLevel.DEBUG);
    }

    public static void debug(Object message, Format format){
        LogPrint.print(message,format,LogLevel.DEBUG);
    }



    public static void warn(String tag,String message){
        LogPrint.setTag(tag);
        warn(message);
    }

    public static void warn(String message){
        LogPrint.w(message);
    }

    public static void warn(Object message){
        LogPrint.print(message,LogLevel.WARN);
    }

    public static void warn(Object message, Format format){
        LogPrint.print(message,format,LogLevel.WARN);
    }



    public static void error(String tag,String message){
        LogPrint.setTag(tag);
        error(message);
    }

    public static void error(String message){
        LogPrint.e(message);
    }

    public static void error(Object message){
        LogPrint.print(message,LogLevel.ERROR);
    }

    public static void error(Throwable throwable){
        LogPrint.e("Error",throwable);
    }

    public static void error(String message, Throwable throwable){
        LogPrint.e(message,throwable);
    }

    public static void error(Object message, Format format){
        LogPrint.print(message,format,LogLevel.ERROR);
    }


    public static void verbose(String tag,String message){
        LogPrint.setTag(tag);
        verbose(message);
    }

    public static void verbose(String message){
        LogPrint.v(message);
    }

    public static void verbose(Object message){
        LogPrint.print(message,LogLevel.VERBOSE);
    }

    public static void verbose(Object message, Format format){
        LogPrint.print(message,format,LogLevel.VERBOSE);
    }



    public static void wtf(String tag,String message){
        LogPrint.setTag(tag);
        wtf(message);
    }

    public static void wtf(String message){
        LogPrint.wtf(message);
    }

    public static void wtf(Object message){
        LogPrint.print(message,LogLevel.WTF);
    }

    public static void wtf(Object message, Format format){
        LogPrint.print(message,format,LogLevel.WTF);
    }




    public static void shortToast(String message){
        LogPrint.showToast(null,message,1);
    }

    public static void longToast(String message){
        LogPrint.showToast(null,message,2);
    }


    public static void shortToast(Activity activity, String message){
        LogPrint.showToast(activity,message,1);
    }

    public static void longToast(Activity activity,String message){
        LogPrint.showToast(activity,message,2);
    }



    public static class Builder {
        Context context = null;
        String tag = null;
        boolean canshow = true;
        public Builder(Context context){
            this.context = context;
        }

        public Builder setTag(String tag){
            this.tag = tag;
            return this;
        }

        public Builder enableLog(boolean enable){
            this.canshow = enable;
            return this;
        }

        public void create(){
            if (tag == null)
                LogPrint.setContext(context);
            else
                LogPrint.setTag(context,tag);

            LogPrint.canShowLog(canshow);
        }
    }

    public static class Preference{
        String name = null;
        public Preference(){
        }

        public Preference whichPrefDBName(String name){
            return this;
        }

        public void printAllSharedPref(){
            if (name != null)LogPrint.setPrefName(name);
            LogPrint.getAllSharedPref();
        }

        public void printSharedPrefByKey(String key){
            if (name !=null)LogPrint.setPrefName(name);
            LogPrint.getSharedPrefValue(key);
        }

    }


}
