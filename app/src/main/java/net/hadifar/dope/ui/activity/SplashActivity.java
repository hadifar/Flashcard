package net.hadifar.dope.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

import net.hadifar.dope.AppConfig;
import net.hadifar.dope.R;
import net.hadifar.dope.storage.BundleDataBaseManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startAnimation();

        initialize();

    }

    private void initialize() {

//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
//
//                if (SettingsManager.getAppLatestVersionCode(SplashActivity.this) < AppConfig.getAppVersionCode()) {
//                    //this calls when app has old data
//                    //creating database files
//                    BundleDataBaseManager bundledDataBaseManager = BundleDataBaseManager.getInstance();
//                    bundledDataBaseManager.init();
//                    //copy data from asset to database
//                    importDatabaseFromAssets();
//                    SettingsManager.setAppLatestVersionCode(SplashActivity.this, AppConfig.getAppVersionCode());
//                }
//
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//                finishSplash();
//            }
//        };

    }

    private void finishSplash() {

    }


    private void startAnimation() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animateLogo();
            }
        }, 300);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animateSubtitle();
            }
        }, 900);
    }

    private void animateLogo() {
        View logo = findViewById(R.id.img_splash_logo);
        AnimationSet logoAnimationSet = new AnimationSet(true);
        logoAnimationSet.setDuration(600);
        logoAnimationSet.setStartOffset(300);
        logoAnimationSet.addAnimation(new AlphaAnimation(0, 1));
        logoAnimationSet.addAnimation(new ScaleAnimation(3, 1, 3, 1, Animation.RELATIVE_TO_SELF, .75f, Animation.RELATIVE_TO_SELF, .75f));
        logoAnimationSet.setInterpolator(new DecelerateInterpolator());
        logo.startAnimation(logoAnimationSet);
        logo.setVisibility(View.VISIBLE);
    }

    private void animateSubtitle() {

        final View subtitle = findViewById(R.id.txt_subtitle_splash);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(subtitle, "translationY", -subtitle.getHeight(), 0),
                ObjectAnimator.ofFloat(subtitle, "alpha", 0, 1)
        );
        animatorSet.setDuration(450);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                subtitle.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }
        });
        animatorSet.start();
    }

    private void importDatabaseFromAssets() {

        try {
            InputStream myInput = getAssets().open(BundleDataBaseManager.DATABASE_NAME);
            String DB_PATH;

            if (Build.VERSION.SDK_INT >= 17)
                DB_PATH = AppConfig.getInstance().getApplicationInfo().dataDir + "/databases/";
            else
                DB_PATH = "/data/data/" + AppConfig.getInstance().getPackageName() + "/databases/";

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
