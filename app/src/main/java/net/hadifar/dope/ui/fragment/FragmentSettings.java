package net.hadifar.dope.ui.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.View;

import net.hadifar.dope.R;

/**
 * Created by Amir on 4/27/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public class FragmentSettings extends PreferenceFragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(R.color.default_background));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_settings);
    }
}
