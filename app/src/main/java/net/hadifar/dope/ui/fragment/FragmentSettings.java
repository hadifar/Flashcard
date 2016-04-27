package net.hadifar.dope.ui.fragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.View;

import net.hadifar.dope.R;
import net.hadifar.dope.ui.activity.MainActivity;

/**
 * Created by Amir on 4/27/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public class FragmentSettings extends PreferenceFragment implements FragmentManager.OnBackStackChangedListener {

    MainActivity mainActivity;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(R.color.default_background));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_settings);

        mainActivity = (MainActivity) getActivity();

        getFragmentManager().addOnBackStackChangedListener(this);

        shouldDisplayHomeUp();

        setTitle(getString(R.string.nav_settings));
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
