package net.hadifar.dope.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import net.hadifar.dope.ui.adapter.SettingsAdapter;

/**
 * Created by Amir on 4/27/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public class FragmentSettings extends BaseListFragment  {


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        adapter = new SettingsAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
