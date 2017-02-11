package com.logger.min.ezylogger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.logger.min.easylogger.Format;
import com.logger.min.easylogger.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // INIT
        Logger.init(this);
        Logger.canShowLog(BuildConfig.DEBUG);
        Logger.initTag("LOG");
        //Logger.initTag(this,"LOG");

        // OR

        new Logger.Builder(this).setTag("LOG").enableLog(BuildConfig.DEBUG).create();

        // END INIT


        // print tag name
        Logger.printTag();
        // get tag name
        Logger.getTag();

    }
}
