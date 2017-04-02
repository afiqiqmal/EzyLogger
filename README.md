# EzyLogger-Android
Simple Logger for Android


[![](https://jitpack.io/v/afiqiqmal/EzyLogger.svg)](https://jitpack.io/#afiqiqmal/EzyLogger)


### Gradle

```grovvy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
    }
}

dependencies {
    compile 'com.github.afiqiqmal:EzyLogger:1.1.0'
}
```

### Maven
```grovvy
<dependency>
	<groupId>com.github.afiqiqmal</groupId>
	<artifactId>EzyLogger</artifactId>
	<version>1.0.1</version>
</dependency>
```


### How to use:

Init Logger

Use in Activity
```java

public class BaseActivtiy extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inline initialize
        new Logger.Builder(this).setTag("testing").enableLog(true).create();

        //short initialize
        Logger.init(this);               // Optional if you dont use Logger Toast
        Logger.initTag(this,"testing");  // default is 'EzyLogger'
        Logger.initTag("testing");       // default is 'EzyLogger'
        Logger.canShowLog(true);         // default is True
    }
}    

```


Logger use
```java

//info log
Logger.info(TAG,"message"); // can set TAG here if not using initTag
Logger.info("message");
Logger.info(Object);
Logger.info(Object,Format); // JSON, XML,LIST, MAP, BUNDLE, STRING, INTENT, ARRAY

Logger.debug(...);
Logger.warn(...);
Logger.error(...);
Logger.verbose(...);
Logger.wtf(...);

//EXAMPLE OF USE

List<?> users = new ArrayList<>();
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

Logger.info(JSON_STRING,Format.JSON);
Logger.info(XML_STRING,Format.XML);
Logger.info(users,Format.LIST);
Logger.info(bundle,Format.BUNDLE);
Logger.info(map,Format.MAP);
Logger.info(intent,Format.INTENT);
Logger.info("message",Format.STRING);
Logger.info(new String[]{"array","1","2"},Format.ARRAY);
```


Use Logger for showing Toast

``` java
// Logger.init(this) is needed 
Logger.shortToast(MESSAGE);
Logger.longToast(MESSAGE);
```


Extra Logger
if you need to list all the values in SharedPreferences
```java

//init
Logger.init(this) //needed if not init yet

//will log all the prefs in the default Prefs
new Logger.Preference().printAllSharedPref();

//log all the prefs in the selected Prefs
new Logger.Preference().whichPrefDBName(PREF_NAME).printAllSharedPref();

//log the value by the key
new Logger.Preference().printSharedPrefByKey(KEY);



//OR can do like this
Logger.Preference pref = new Logger.Preference();
pref.whichPrefDBName(PREF_NAME);
pref.printAllSharedPref();
pref.printSharedPrefByKey(KEY);

```




###License
<pre>
Copyright 2017 HAFIQ IQMAL

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</pre>
