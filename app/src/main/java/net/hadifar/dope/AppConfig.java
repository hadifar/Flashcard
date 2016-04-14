package net.hadifar.dope;

import android.app.Application;
import android.content.pm.PackageManager;

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

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/YekanMob-Regular-v4.ttf")
                .setFontAttrId(net.hadifar.dope.R.attr.fontPath)
                .build());


    }

    public static int getAppVersionCode() {
        try {
            return instance.getPackageManager().getPackageInfo(instance.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 1;
        }
    }

//    public static String getFilesDirectory() {
//        File filesDir = getInstance().getApplicationContext().getFilesDir(); // /data/data/com...
//        String root = filesDir.getPath()
//                .concat(File.separator);
//
//        return root;
//    }
//
//    public static String getUnzippedDataDirectory() {
//        return getFilesDirectory()
//                .concat(Constants.UNZIPPED_DATA_DIRECTORY)
//                .concat(File.separator);
//    }
//
//    public static String getRootSdDirectory() {
//        String root = Environment.getExternalStorageDirectory().getPath() // /storage/sdcard0/
//                .concat(File.separator);
//        return root;
//    }
//
//    private void createAppDirectories(){
//        File file;
//        String[] directories = {
//                getUnzippedDataDirectory()
//        };
//        for (String directory : directories) {
//            file = new File(directory);
//            if(!file.exists()) {
//                try {
//                    file.mkdirs();
//                }catch (SecurityException e) {
//                    Toast.makeText(instance.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
//                }
//            }
//        }
//    }

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

}