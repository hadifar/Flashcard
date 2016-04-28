package net.hadifar.dope;

import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;

import net.hadifar.dope.storage.BundleDataBaseManager;
import net.hadifar.dope.storage.SettingsManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Amir Hadifar on 25/07/2015
 * Flash2Flash
 * Email : Hadifar.amir@gmail.com
 * Twitter : @AmirHadifar
 */

public class AppConfig extends Application {

    private static AppConfig instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        if (SettingsManager.getAppLatestVersionCode(instance) < getAppVersionCode()) {
            //this calls when app has old data
            //creating database files
            BundleDataBaseManager bundledDataBaseManager = BundleDataBaseManager.getInstance();
            bundledDataBaseManager.init();
//            bundledDataBaseManager.clearAllTables();
            //copy data from asset to database
            importDatabaseFromAssets();
            SettingsManager.setAppLatestVersionCode(this, getAppVersionCode());
        }

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/YekanMob-Regular-v4.ttf")
                .setFontAttrId(net.hadifar.dope.R.attr.fontPath)
                .build());


        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);

    }


    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }

    public void trackException(String exception) {
        if (!TextUtils.isEmpty(exception)) {
            Tracker t = getGoogleAnalyticsTracker();
            t.send(new HitBuilders.ExceptionBuilder()
                    .setDescription(exception)
                    .build()
            );
        }
    }

    public static int getAppVersionCode() {
        try {
            return instance.getPackageManager().getPackageInfo(instance.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 1;
        }
    }

    public static AppConfig getInstance() {
        return instance;
    }

//    public static String getAppVersion() {
//        try {
//            return instance.getPackageManager().getPackageInfo(instance.getPackageName(), 0).versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//            return "";
//        }
//    }
//
//    public static int getAppVersionCode() {
//        try {
//            return instance.getPackageManager().getPackageInfo(instance.getPackageName(), 0).versionCode;
//        } catch (PackageManager.NameNotFoundException e) {
//            return 1;
//        }
//    }

    //    private boolean isDataBaseExist() {
//        String DB_PATH = "/data/data/" + getPackageName() + "/databases/";
//        File dbFile = new File(DB_PATH + BundledDataBaseManager.DATABASE_NAME);
//        return dbFile.exists();
//    }
//


//    public static void exportDatabaseToSD() {
//        try {
//            String DB_PATH = "/data/data/" + getInstance().getPackageName() + "/databases/";
//            InputStream myInput = new FileInputStream(DB_PATH + BundledDataBaseManager.DATABASE_NAME);
//            OutputStream myOutput = new FileOutputStream("/sdcard/" + BundledDataBaseManager.DATABASE_NAME);
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = myInput.read(buffer)) > 0) {
//                myOutput.write(buffer, 0, length);
//            }
//            // Close the streams
//            myOutput.flush();
//            myOutput.close();
//            myInput.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    private void importDatabaseFromAssets() {

        try {
            InputStream myInput = getAssets().open(BundleDataBaseManager.DATABASE_NAME);
            String DB_PATH;

            if (Build.VERSION.SDK_INT >= 17)
                DB_PATH = getApplicationInfo().dataDir + "/databases/";
            else
                DB_PATH = "/data/data/" + getPackageName() + "/databases/";

            String outFileName = DB_PATH + BundleDataBaseManager.DATABASE_NAME;
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
