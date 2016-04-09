package com.march1905.dope.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.march1905.dope.R;
import com.march1905.dope.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Amir Hadifar on 27/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @AmirHadifar
 */

public abstract class BaseDrawerActivity extends AppCompatActivity {


    //toolbar stuff
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.frame_left_icons)
    FrameLayout toolbarFrameIcons;

    @Bind(R.id.icon_toolbar_drawer)
    TextView toolbarIconDrawer;

    @Bind(R.id.icon_toolbar_search)
    TextView toolbarSearchIcon;

    @Bind(R.id.icon_toolbar_back)
    TextView toolbarBackIcon;

    @Bind(R.id.text_toolbar_title)
    TextView toolbarTitle;

    //drawer stuff
    @Bind(R.id.navigation_view)
    NavigationView navigationView;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (toolbar != null)
            setupToolbar();

        if (navigationView != null)
            setupDrawerContent();


        if (savedInstanceState != null)
            restoreFragment(savedInstanceState);
        else
            displayView(0, null);

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        startToolbarAnimation();
    }

    private void setupDrawerContent() {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }


    public abstract void displayView(int position, Bundle fragmentBundle);


    public abstract void restoreFragment(Bundle savedInstanceState);


    public void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    private void startToolbarAnimation() {

        int actionbarSize = Utils.dpToPx(56);
        getFrameIcons().setTranslationY(-actionbarSize);
        getAppLogo().setTranslationY(-actionbarSize);
        getSearchIcon().setTranslationY(-actionbarSize);

        getFrameIcons().animate()
                .translationY(0)
                .setDuration(300)
                .setStartDelay(300);
        getAppLogo().animate()
                .translationY(0)
                .setDuration(300)
                .setStartDelay(400);
        getSearchIcon().animate()
                .translationY(0)
                .setDuration(300)
                .setStartDelay(500)
                .setListener(new android.animation.Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(android.animation.Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(android.animation.Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(android.animation.Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(android.animation.Animator animation) {

                    }
                })
                .start();
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public FrameLayout getFrameIcons() {
        return toolbarFrameIcons;
    }

    public TextView getSearchIcon() {
        return toolbarSearchIcon;
    }

    public TextView getBackIcon() {
        return toolbarBackIcon;
    }

    public TextView getAppLogo() {
        return toolbarTitle;
    }

    public void drawerEnable(){
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        toolbarIconDrawer.setVisibility(View.VISIBLE);
        toolbarBackIcon.setVisibility(View.GONE);
    }

    public void drawerDisable(){
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toolbarIconDrawer.setVisibility(View.GONE);
        toolbarBackIcon.setVisibility(View.VISIBLE);
    }


    @OnClick(R.id.icon_toolbar_drawer)
    public void onDrawerClick() {
        if (toolbarIconDrawer.getVisibility() == View.GONE)
            return;
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    @OnClick(R.id.icon_toolbar_back)
    public void onBackClick(){
        if(toolbarBackIcon.getVisibility()==View.GONE)
            return;
        onBackPressed();
    }


    @Override
    public void onBackPressed() {

        // if drawer is open first close drawer
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}

