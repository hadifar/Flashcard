package net.hadifar.dope.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import net.hadifar.dope.model.BaseEntity;
import net.hadifar.dope.ui.adapter.GridAdapter;
import net.hadifar.dope.ui.listeners.OnPolygonClickListener;

/**
 * Created by Amir on 4/17/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public class FragmentLearningMethod extends BaseListFragment implements OnPolygonClickListener {


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        adapter = new GridAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void onRootClickListener(View v, BaseEntity entity) {
        Snackbar.make(v, "ttttttt", Snackbar.LENGTH_LONG).show();
    }


}
