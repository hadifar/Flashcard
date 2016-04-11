package com.march1905.dope.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.march1905.dope.R;
import com.march1905.dope.ui.adapter.CategoryAdapter;
import com.march1905.dope.ui.fragment.dialogs.FragmentNewCategory;
import com.march1905.dope.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Amir Hadifar on 27/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @AmirHadifar
 */

public class FragmentCategories extends DefaultFragment {

    @Bind(R.id.rv_list_category)
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new CategoryAdapter(getActivity()));

//        startIntroAnimation();

    }


    private void startIntroAnimation() {
        recyclerView.setTranslationY(Utils.getScreenHeight(getActivity()));
        recyclerView.setAlpha(0f);
        recyclerView.animate()
                .translationY(0)
                .setDuration(400)
                .alpha(1f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    @OnClick(R.id.fab_add_new_category)
    public void fabBtnClicked() {
        FragmentNewCategory newCategoryDialog = new FragmentNewCategory();
        newCategoryDialog.show(getFragmentManager(), "NewCategoryDialog");
    }
}
