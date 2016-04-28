package net.hadifar.dope.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.hadifar.dope.R;
import net.hadifar.dope.ui.adapter.BaseAdapter;
import net.hadifar.dope.ui.adapter.SectionAdapter;
import net.hadifar.dope.ui.adapter.SettingsAdapter;
import net.hadifar.dope.ui.widget.DividerItemDecorator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Amir on 4/27/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public class FragmentSettings extends BaseFragment {

    @Bind(R.id.rv_settings_list)
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        BaseAdapter adapter = new SettingsAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecorator(getActivity()));

        List<SectionAdapter.Section> sections = new ArrayList<SectionAdapter.Section>();

        //Sections
        sections.add(new SectionAdapter.Section(0, "Section 1"));
        sections.add(new SectionAdapter.Section(5, "Section 2"));
        sections.add(new SectionAdapter.Section(12, "Section 3"));
        sections.add(new SectionAdapter.Section(14, "Section 4"));
        sections.add(new SectionAdapter.Section(20, "Section 5"));

        SectionAdapter.Section[] dummy = new SectionAdapter.Section[sections.size()];
        SectionAdapter mSectionedAdapter = new SectionAdapter(getActivity(), R.layout.row_setting_section_items, adapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        recyclerView.setAdapter(mSectionedAdapter);

    }
}
