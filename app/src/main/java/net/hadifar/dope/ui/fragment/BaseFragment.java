package net.hadifar.dope.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.hadifar.dope.ui.activity.MainActivity;

/**
 * Amir Hadifar on 27/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @AmirHadifar
 */

public abstract class BaseFragment extends Fragment implements FragmentManager.OnBackStackChangedListener {

    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_TITLE = "extra_title";


    private MainActivity mainActivity;

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


    public boolean shouldDisplayHomeUp() {
        //Enable Up button only  if there are entries in the back stack
        boolean canBack = false;
        try {
            canBack = getFragmentManager().getBackStackEntryCount() > 0;
        } catch (Exception ex) {
//            Log.e(getClass().getCanonicalName(), ex.getMessage());getMessage
        }

        if (canBack) {
            mainActivity.drawerDisable();
        } else {
            mainActivity.drawerEnable();
        }
        return canBack;
    }

    public void setTitle(String title) {
        mainActivity.setToolbarTitle(title);
    }
}
