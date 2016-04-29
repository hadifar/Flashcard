package net.hadifar.dope.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.hadifar.dope.AnalyticsTrackers;

/**
 * Created by Amir on 4/14/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sending uncaughtExceptions to analytics server
        //todo uncomment this
//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread thread, Throwable throwable) {
//                try {
//                    AnalyticsTrackers.analyticLogCrash(BaseActivity.this, throwable);
//                    startActivity(new Intent(BaseActivity.this, ExceptionActivity.class));
//                    System.exit(0);
//                } catch (Exception e) {}
//            }
//        });
    }


}
