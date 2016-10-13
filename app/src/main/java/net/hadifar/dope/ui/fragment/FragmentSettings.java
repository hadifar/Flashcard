package net.hadifar.dope.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.hadifar.dope.R;
import net.hadifar.dope.model.SettingEntity;
import net.hadifar.dope.storage.SettingsManager;
import net.hadifar.dope.ui.fragment.dialogs.LiecenceDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Amir on 4/27/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public class FragmentSettings extends BaseFragment {

    @Bind(R.id.switch_animation)
    SwitchCompat switchCompat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        switchCompat.setChecked(SettingsManager.isAnimationEnabled(getActivity()));
    }

    @OnClick(R.id.txt_language)
    public void onLanguageClick() {

    }


    @OnClick(R.id.ll_animation)
    public void onAnimationClick() {
        if (switchCompat.isChecked()) {
            switchCompat.setChecked(false);
            SettingsManager.setAnimationEnable(getActivity(), false);
        } else {
            switchCompat.setChecked(true);
            SettingsManager.setAnimationEnable(getActivity(), true);
        }

        Log.d("set animation", "onAnimationClick: ");
    }


    @OnClick(R.id.ll_open_source)
    public void onLiecenceClick() {
        LiecenceDialog dialog = new LiecenceDialog();
        dialog.show(getFragmentManager(), "OpenSourceDialog");
    }


}
