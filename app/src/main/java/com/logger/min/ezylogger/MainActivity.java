package com.logger.min.ezylogger;

import android.content.Intent;
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

        //inline initialize
        new Logger.Builder(this).setTag("testing").enableLog(true).create();

        //short initialize
        Logger.initTag(this,"testing");
        Logger.initTag("testing");
        Logger.canShowLog(true);


        List<User> users = new ArrayList<>();
        Bundle bundle = new Bundle();
        Map<String,?> map = new LinkedHashMap<>();
        Intent intent = new Intent();


        Logger.info("message");
        Logger.info(0);
        Logger.info(new String[]{"test","world","hello"});
        Logger.info(new int[]{0,1,2,3,4});
        Logger.info(true);




        Logger.info(users);
        Logger.info(bundle);
        Logger.info(map);
        Logger.info(intent);



        Logger.info("JSON_STRING",Format.JSON);
        Logger.info("XML_STRING",Format.XML);
        Logger.info(users,Format.LIST);
        Logger.info(bundle,Format.BUNDLE);
        Logger.info(map,Format.MAP);
        Logger.info(intent,Format.INTENT);
        Logger.info("message",Format.STRING);
        Logger.info(new String[]{"array","1","2"},Format.ARRAY);





    }
}
