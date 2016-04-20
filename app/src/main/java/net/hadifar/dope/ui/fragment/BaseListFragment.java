package net.hadifar.dope.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import net.hadifar.dope.R;
import net.hadifar.dope.ui.adapter.BaseAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Amir on 4/15/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public abstract class BaseListFragment extends BaseFragment {

    protected BaseAdapter adapter;

    @Bind(R.id.rv_list_base)
    RecyclerView recyclerView;
    @Bind(R.id.fab_add_new_card)
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_list, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    protected void showFloatingButton() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!fab.isShown()) {
                    fab.show();
                    setupFabListener();
                }
            }
        }, 1000);
    }

    private void setupFabListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fab.isShown())
                    fab.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

}
