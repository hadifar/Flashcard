package com.march1905.dope.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.march1905.dope.R;
import com.march1905.dope.adapter.CategoryAdapter;
import com.march1905.dope.fragment.dialogs.FragmentNewCategory;
import com.melnykov.fab.FloatingActionButton;

/**
 * Amir Hadifar on 27/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @HadifarAmir
 */

public class FragmentCategories extends DefaultFragment {

    private CharSequence mTitle = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //this.setHasOptionsMenu(true); // We use this so we can have specific ActionBar actions/icons for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.categoriesList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        CategoryAdapter adapter = new CategoryAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.addNewCategory);
        fab.attachToRecyclerView(mRecyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentNewCategory newCategoryDialog = new FragmentNewCategory();
                newCategoryDialog.show(getFragmentManager(), "NewCategoryDialog");
            }
        });
    }

}
