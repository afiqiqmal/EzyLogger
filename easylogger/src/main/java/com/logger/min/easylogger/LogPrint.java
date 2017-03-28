package com.logger.min.easylogger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author : hafiq on 18/01/2017.
 */

@SuppressLint("StaticFieldLeak")
class LogPrint {

    private static String TAG = "EzyLogger";
    private static Context context;
    private static boolean DEBUG = false;


    private static final int JSON_INDENT = 2;
    private static String PREF_NAME = null;

    static void setContext(Context con){
        context = con;
    }

    static void setTag(String tag){
        TAG = (tag!=null)? tag : TAG;
    }

    static void setTag(Context con,String tag){
        TAG = (tag!=null)? tag : TAG;
        context = con;
    }

    static String getTag(){
        return TAG;
    }

    static void setPrefName(String name){
        PREF_NAME = name;
    }

    static void canShowLog(boolean debug){
        DEBUG = debug;
    }

    static void i(String message){
        if (DEBUG) Log.i(TAG,message);
    }

    static void d(String message){
        if (DEBUG) Log.d(TAG,message);
    }

    static void w(String message){
        if (DEBUG) Log.w(TAG,message);
    }

    static void e(String message){
        if (DEBUG) Log.e(TAG,message);
    }

    static void e(Throwable throwable){
        if (DEBUG) Log.e(TAG,"Error",throwable);
    }

    static void e(String message, Throwable throwable){
        if (DEBUG) Log.e(TAG,message,throwable);
    }

    static void v(String message){
        if (DEBUG) Log.v(TAG,message);
    }

    static void wtf(String message){
        if (DEBUG) Log.wtf(TAG,message);
    }

    static void getAllSharedPref() {
        try {
            if (context != null) {
                SharedPreferences prefs;
                if (PREF_NAME != null)
                    prefs = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                else
                    prefs = PreferenceManager.getDefaultSharedPreferences(context);

                if(prefs.getAll() != null) {
                    convertLinkedHashMap(castMap((LinkedHashMap)prefs.getAll()), LogLevel.DEBUG);
                }
            } else {
                throw new Exception("Context Cannot be Null");
            }
        }
        catch (Exception e){
            e(e.getMessage());
        }
    }

    static void getSharedPrefValue(String key){
        try {
            if (context != null) {
                SharedPreferences prefs;
                if (PREF_NAME != null)
                    prefs = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                else
                    prefs = PreferenceManager.getDefaultSharedPreferences(context);

                if(prefs.getAll().get(key) instanceof String) {
                    log(prefs.getString(key,null),LogLevel.DEBUG);
                }
                else if(prefs.getAll().get(key) instanceof Boolean) {
                    log(prefs.getBoolean(key,false)+"",LogLevel.DEBUG);
                }
                else if(prefs.getAll().get(key) instanceof Float) {
                    log(prefs.getFloat(key,-1)+"",LogLevel.DEBUG);
                }
                else if(prefs.getAll().get(key) instanceof Integer) {
                    log(prefs.getInt(key,-1)+"",LogLevel.DEBUG);
                }
                else if(prefs.getAll().get(key) instanceof Long) {
                    log(prefs.getLong(key,-0)+"",LogLevel.DEBUG);
                }
                else{
                    log(prefs.getString(key,null),LogLevel.DEBUG);
                }

            } else {
                throw new Exception("Context Cannot be Null");
            }
        }
        catch (Exception e){
            e(e.getMessage());
        }
    }


    private static void log(String message, LogLevel level){
        switch (level) {
            case INFO:
                i(message);
                break;
            case DEBUG:
                d(message);
                break;
            case VERBOSE:
                v(message);
                break;
            case WARN:
                w(message);
                break;
            case ERROR:
                e(message);
                break;
            case WTF:
                wtf(message);
                break;
        }
    }

    static void print(Object message, Format format,LogLevel level){
        if (DEBUG) {
            switch (format) {
                case JSON:
                    prettyPrintJsonString(message.toString(),level);
                    break;
                case XML:
                    prettyPrintXMLString(message.toString(),level);
                    break;
                case LIST:
                    convertListString(castList(message),level);
                    break;
                case MAP:
                    convertLinkedHashMap(castMap(message),level);
                    break;
                case BUNDLE:
                    convertBundleString(message,level);
                    break;
                case INTENT:
                    convertBundleString(((Intent)message).getExtras(),level);
                    break;
                case STRING:
                    log(message.toString(),level);
                    break;
                case ARRAY:
                    convertArraytoString(message,level);
                    break;
                default:
                    break;
            }
        }
    }

    static void print(Object message,LogLevel level){
        try {
            if (message instanceof String) {
                possibleXmlorJsonorString(message.toString(), level);
            } else if (message instanceof Map) {
                convertLinkedHashMap(castMap(message), level);
            } else if (message instanceof Bundle) {
                convertBundleString(message, level);
            } else if (message instanceof Intent) {
                convertBundleString(((Intent) message).getExtras(), level);
            } else if (message instanceof List<?>) {
                convertListString(castList(message), level);
            } else if (message instanceof Integer || message instanceof Float || message instanceof Double){
                log(message.toString(), level);
            } else if (message instanceof Array[] || message instanceof Object[] || message.getClass().getComponentType().isPrimitive()) {
                convertArraytoString(message, level);
            } else {
                log(message.toString(), level);
            }
        }
        catch (Exception e){
            log("[ERROR] => "+e.getMessage(),LogLevel.ERROR);
        }
    }


    private static <T extends List<?>> T castList(Object obj) {
        try {
            return (T) obj;
        }
        catch (Exception e){
            e(e.getMessage());
            return null;
        }
    }

    private static <T extends LinkedHashMap<String,?>> T castMap(Object obj) {
        try {
            return (T) obj;
        }
        catch (Exception e){
            e(e.getMessage());
            return null;
        }
    }

    private static Object[] convertToObjectArray(Object array) {
        Class ofArray = array.getClass().getComponentType();
        if (ofArray.isPrimitive()) {
            List ar = new ArrayList();
            int length = Array.getLength(array);
            for (int i = 0; i < length; i++) {
                ar.add(Array.get(array, i));
            }
            return ar.toArray();
        }
        else {
            return (Object[]) array;
        }
    }

    private static void convertArraytoString(Object obj,LogLevel level){
        if (obj!=null) {
            log(showline(), level);
            Object[] getObj = convertToObjectArray(obj);
            if (getObj != null && getObj.length > 0) {
                for (Object object : getObj) {
                    log("[ARRAY] : " + object.toString(), level);
                }
                log("[ARRAY] : Total Item : " + getObj.length, level);
            } else {
                log("[ARRAY] : Array Given is Empty or Null", level);
            }

            log(showline(), level);
        }
        else {
            log("[ARRAY] : Array Given is Empty or Null", LogLevel.WARN);
        }
    }

    private static void convertBundleString(Object obj,LogLevel level){
        if (obj != null && obj instanceof Bundle) {
            log(showline(),level);
            Bundle bundle = (Bundle)obj;
            int x=0;
            for (String entry : bundle.keySet()) {
                try {
                    Object value = bundle.get(entry);
                    if (value != null){
                        log("   [BUNDLE] : "+String.format("[%s] => [%s]", entry, bundle.get(entry).toString()),level);
                    }
                }
                catch (Exception e){
                    log("[BUNDLE] : Error Key => '"+entry+"'",LogLevel.ERROR);
                }
                x++;
            }
            log("[BUNDLE] : Size Item => "+x,level);
            log(showline(),level);
        }
        else{
            log("[BUNDLE] : Error => Not Bundle Type or Null",LogLevel.WARN);
        }
    }

    private static void convertListString(List<?> list, LogLevel level){
        if (list!=null) {
            log(showline(),level);
            try {
                log("[LIST] : Object Name ( " + list.getClass().getSimpleName() + " )", level);
                for (Object obj : list) {
                    log("   [LIST] : [" + obj.toString() + "]", level);
                }
                log("[LIST] : Total Item : " + list.size(), level);
            }
            catch (Exception e){
                log("[LIST] : Error => "+e.getMessage(),LogLevel.ERROR);
            }
            log(showline(),level);
        }
        else{
            log("[LIST] : List is null",LogLevel.WARN);
        }
    }

    private static void convertLinkedHashMap(LinkedHashMap<String,?> message,LogLevel level){
        if (message!=null) {
            log(showline(), level);
            for (Map.Entry<String, ?> entry : message.entrySet()) {
                try {
                    log("[MAP] : [" + entry.getKey() + "] => [" + entry.getValue() + "]", level);
                } catch (Exception e) {
                    log("[MAP] : Error Key => '" + entry.getKey()+"'", LogLevel.ERROR);
                }
            }

            log(showline(), level);
        }
        else{
            log("[MAP] : Map is Null",LogLevel.WARN);
        }
    }

    private static void prettyPrintJsonString(String json,LogLevel level){

        if (isJSONValid(json)) {
            log(showline(), level);
            try {
                json = json.trim();
                if (json.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(json);
                    log(jsonObject.toString(JSON_INDENT), level);
                } else if (json.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(json);
                    log(jsonArray.toString(JSON_INDENT), level);
                } else {
                    log("[JSON] : Error => Wrong JSON Format", LogLevel.ERROR);
                }

            } catch (Exception e) {
                log("[JSON] : Error => [ " + e.getMessage() + " ]", LogLevel.ERROR);
            }
            log(showline(), level);
        }
        else{
            log("[JSON] : Error => Wrong JSON Format", LogLevel.WARN);
        }
    }

    private static void prettyPrintXMLString(String xml,LogLevel level) {
        if (isValidXML(xml)) {
            log(showline(), level);
            try {
                Source xmlInput = new StreamSource(new StringReader(xml));
                StreamResult xmlOutput = new StreamResult(new StringWriter());
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                transformer.transform(xmlInput, xmlOutput);
                log(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"), level);
            } catch (TransformerException e) {
                log("[XML] : Error => Wrong XML Format [ " + e.getMessage() + " ]", LogLevel.ERROR);
            }

            log(showline(), level);
        }
        else{
            log("[XML] : Error => Wrong XML Format ", LogLevel.WARN);
        }
    }

    private static void possibleXmlorJsonorString(String input,LogLevel level){
        if (isJSONValid(input)){
            prettyPrintJsonString(input,level);
            return;
        }

        if (isValidXML(input)){
            prettyPrintXMLString(input,level);
            return;
        }

        log(input,level);
    }

    private static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidXML(String test){
        return test.matches("(?s).*(<(\\w+)[^>]*>.*</\\2>|<(\\w+)[^>]*/>).*");

    }

    private static String showline(){
        return "====================================================================";
    }

    static void showToast(Activity activity, String message, int type){
        try {
            if (context != null) {
                if (type == 1) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                } else if (type == 2) {
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }
            } else {
                if (activity != null){
                    if (type == 1) {
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    } else if (type == 2) {
                        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                    }
                }
                else
                    throw new Exception("Context Cannot be Null");
            }
        }
        catch (Exception e){
            e(e.getMessage());
        }
    }
}
