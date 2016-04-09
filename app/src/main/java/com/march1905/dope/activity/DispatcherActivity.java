package com.march1905.dope.activity;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.march1905.dope.R;


/**
 * Amir Hadifar on 27/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @HadifarAmir
 */

public abstract class DispatcherActivity extends AppCompatActivity {


    private ActionBar actionBar;

    private int mCurrentPosition = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatcher);

        init();

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(this, R.style.ToolbarStyle);
        setSupportActionBar(toolbar);


        if (savedInstanceState != null)
            restoreFragment(savedInstanceState);
        else
            displayView(0, null);


    }

    @Override
    public void setTitle(CharSequence title) {
        actionBar.setTitle(title);
    }


    /**
     * Method to define what clicking on the drawer will do.
     *
     * @param position       The position of the clicked item
     * @param fragmentBundle A bundle in case something needs to be passed to a specific fragment
     */
    public abstract void displayView(int position, Bundle fragmentBundle);


    public abstract void restoreFragment(Bundle savedInstanceState);

    /**
     * Any specific initializations should go here.
     */
    public abstract void init();

    /**
     * Handy method to clear the back stack. We want to do this to avoid back stack bugs.
     */
    public void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void onBackPressed() {

        // if drawer is open first close drawer
//        if (result.isDrawerOpen()) {
//            result.closeDrawer();
//        } else {

            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                // handle by OS
                super.onBackPressed();
            } else {
                // Otherwise we remove it from the back stack and the framework will handle the
                // fragment change for us :)
                getSupportFragmentManager().popBackStack();
            }
//        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (mCurrentPosition != ACTION_SEARCH) {
//            switch (item.getItemId()) {
//                case R.id.action_search:
//                    // displayView(SETTINGS_FRAG, null);
//                    // return true;
//                    break;
//                default:
//                    return super.onOptionsItemSelected(item);
//
//            }
//        }
//
//        return true;
//    }
}

