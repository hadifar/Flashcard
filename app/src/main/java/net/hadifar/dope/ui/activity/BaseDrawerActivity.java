package net.hadifar.dope.ui.activity;


import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.hadifar.dope.R;
import net.hadifar.dope.ui.fragment.FragmentSettings;
import net.hadifar.dope.utils.Utils;

import java.util.Stack;

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

    public static final int FLASHCARDS_VIEWER = 3;
    public static final int FLASHCARDS_FRAG = 2;
    public static final int DECKS_FRAG = 1;
    public static final int CATEGORIES_FRAG = 0;
    public static final int LEARNING_FRAG = 10;

    public static final int SETTINGS_FRAG = 4;
    public static final int ABOUT_FRAG = 5;
    public static final int FAVORITE_FRAG = 6;
    public static final int FAVORITE_FRAG_VIEWER = 7;

    private int lastSelectedDrawerItem = 0;

    private Stack<String> titleStack = new Stack<>();

    MenuItem previousMenuItem;
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


    public abstract void displayView(int position, Bundle fragmentBundle);

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (toolbar != null)
            setupToolbar();

        if (navigationView != null)
            setupDrawerContent();


        displayView(0, null);
    }


    private void setupToolbar() {
        setSupportActionBar(toolbar);
        startToolbarAnimation();
    }

    private void setupDrawerContent() {
        //set category item as default
        previousMenuItem = navigationView.getMenu().findItem(R.id.nav_category);
        previousMenuItem.setChecked(true);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        drawerLayout.closeDrawer(Gravity.LEFT);

                        if (previousMenuItem.getItemId() == menuItem.getItemId())
                            return true;

                        menuItem.setCheckable(true);
                        menuItem.setChecked(true);
                        previousMenuItem.setChecked(false);
                        previousMenuItem = menuItem;

                        switch (menuItem.getItemId()) {
                            case R.id.nav_category:
                                displayView(CATEGORIES_FRAG, null);
                                break;
                            case R.id.nav_favorites:
                                displayView(FAVORITE_FRAG, null);
                                break;
                            case R.id.nav_about:
                                displayView(ABOUT_FRAG, null);
                                break;
                            case R.id.nav_feedback:
                                displayView(LEARNING_FRAG, null);
                                break;
                            case R.id.nav_settings:
                                getFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.animator.alpha_in, R.animator.alpha_out, R.animator.alpha_in, R.animator.alpha_out)
                                        .addToBackStack(null)
                                        .add(R.id.frame_container, new FragmentSettings())
                                        .commit();
                                break;

                        }


                        return true;
                    }
                }

        );
    }


    public void clearBackStack() {
        FragmentManager manager = getFragmentManager();
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

    public FrameLayout getFrameIcons() {
        return toolbarFrameIcons;
    }

    public TextView getSearchIcon() {
        return toolbarSearchIcon;
    }

    public TextView getAppLogo() {
        return toolbarTitle;
    }

    public void setToolbarTitle(Object object) {
        String newTitle = object instanceof String ? (String) object : getString((Integer) object);
        if (!TextUtils.isEmpty(newTitle)) {
            String currentTitle = toolbarTitle.getText().toString();
            titleStack.push(currentTitle);
            toolbarTitle.setText(newTitle);
        }
    }

    public void drawerEnable() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        toolbarIconDrawer.setVisibility(View.VISIBLE);
        toolbarSearchIcon.setVisibility(View.VISIBLE);
        toolbarBackIcon.setVisibility(View.GONE);
    }

    public void drawerDisable() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toolbarIconDrawer.setVisibility(View.GONE);
        toolbarSearchIcon.setVisibility(View.INVISIBLE);
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
    public void onBackClick() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {

        // if drawer is open first close drawer
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            if (getFragmentManager().getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                getFragmentManager().popBackStack();
                if (!titleStack.isEmpty()) {
                    toolbarTitle.setText(titleStack.pop());
                }
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}

