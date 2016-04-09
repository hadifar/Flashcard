package com.march1905.dope.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.march1905.dope.activity.MainActivity;

/**
 * Amir Hadifar on 27/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @AmirHadifar
 */

public abstract class DefaultFragment extends Fragment implements FragmentManager.OnBackStackChangedListener {


    private MainActivity mainActivity;

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {

        FragmentManager fm = getFragmentManager();
        if (fm != null) {
            fm.beginTransaction().remove(this).commit();
        }
        super.onInflate(activity, attrs, savedInstanceState);
    }

    @Override
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public abstract void onViewCreated(View view, Bundle savedInstanceState);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        getFragmentManager().addOnBackStackChangedListener(this);
        shouldDisplayHomeUp();
    }


    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    /**
     * Handles if we should display the Drawer hamburger icon or if we should display the back navigation button.
     */
    public boolean shouldDisplayHomeUp() {
        //Enable Up button only  if there are entries in the back stack
        boolean canBack = false;
        try {
            canBack = getFragmentManager().getBackStackEntryCount() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (canBack) {
            mainActivity.drawerDisable();
        } else {
//            actionBar.setDisplayHomeAsUpEnabled(false);
//            actionBar.setHomeButtonEnabled(false);
            mainActivity.drawerEnable();
//            mainActivity.getDrawer().getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        }
        return canBack;
    }
}
