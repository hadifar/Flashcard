package com.march1905.dope.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.march1905.dope.R;
import com.march1905.dope.core.BundleDataBaseManager;
import com.march1905.dope.model.BaseEntity;
import com.march1905.dope.model.Category;
import com.march1905.dope.ui.activity.MainActivity;
import com.march1905.dope.ui.adapter.BaseAdapter;
import com.march1905.dope.ui.fragment.dialogs.EditDialog;
import com.march1905.dope.ui.fragment.dialogs.MessageDialog;
import com.march1905.dope.ui.listeners.DialogButtonsClickListener;
import com.march1905.dope.ui.listeners.OnItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Amir Hadifar on 27/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @AmirHadifar
 */

public class FragmentCategory extends DefaultFragment implements OnItemClickListener {

    private BaseAdapter adapter;

    @Bind(R.id.rv_list_category)
    RecyclerView recyclerView;
    @Bind(R.id.fab_add_new_category)
    FloatingActionButton fab;


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

        List<? extends BaseEntity> entityList = BundleDataBaseManager.getInstance().getAllCategories();

        adapter = new BaseAdapter(getActivity(), entityList, this);

        recyclerView.setAdapter(adapter);

        showFloatingButton();
    }

    private void showFloatingButton() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!fab.isShown()) {
                    fab.show();
                }
            }
        }, 1000);
    }

    @OnClick(R.id.fab_add_new_category)
    public void fabClicked() {
        final EditDialog newCategoryDialog = new EditDialog();
        newCategoryDialog.init(R.string.hint_category_name, R.string.hint_category_subtitle, "", "", R.string.create_new_category, new DialogButtonsClickListener() {
            @Override
            public void onLeftButtonClick() {
                newCategoryDialog.dismiss();
            }

            @Override
            public void onRightButtonClick(String... strings) {
                int mCategoryCount = BundleDataBaseManager.getInstance().getLastCategoryId() + 1;
                Category category = new Category(mCategoryCount, strings[0], strings[1]);
                BundleDataBaseManager.getInstance().addToCategory(category);
                adapter.addItem(category);
                newCategoryDialog.dismiss();
                adapter.notifyDataSetChanged();
            }
        });
        newCategoryDialog.show(getChildFragmentManager(), getClass().getCanonicalName());
    }

    @Override
    public void onRootClick(BaseEntity category) {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_ID, category.getId());
        bundle.putString(EXTRA_TITLE, category.getTitle());
        ((MainActivity) getActivity()).displayView(MainActivity.DECKS_FRAG, bundle);
    }

    @Override
    public void onMoreClick(View v, BaseEntity baseEntity) {

        final Category category = (Category) baseEntity;

        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_overflow, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_edit:
                        DialogEdit(category);
                        return true;
                    case R.id.action_delete:
                        DialogDelete(category);
                        return true;
                }
                return false;
            }
        });
    }


    public void DialogEdit(final Category category) {

        final EditDialog editMsg = new EditDialog();
        editMsg.init(R.string.hint_category_name, R.string.hint_category_subtitle, R.string.btn_cancel, R.string.btn_done, "", new DialogButtonsClickListener() {
            @Override
            public void onLeftButtonClick() {
                editMsg.dismiss();
            }

            @Override
            public void onRightButtonClick(String... strings) {
                category.setTitle(strings[0]);
                category.setSubTitle(strings[1]);
                BundleDataBaseManager.getInstance().editFromCategory(category);
                adapter.notifyDataSetChanged();
                editMsg.dismiss();
            }
        });
        editMsg.show(getFragmentManager(), getClass().getCanonicalName());
    }

    public void DialogDelete(final Category category) {

        final MessageDialog deleteMsg = new MessageDialog();
        deleteMsg.init(R.string.title_delete, R.string.icon_delete, R.string.title_are_you_sure_to_delete_category, R.string.btn_no, R.string.btn_yes, false, new DialogButtonsClickListener() {
            @Override
            public void onLeftButtonClick() {
                deleteMsg.dismiss();
            }

            @Override
            public void onRightButtonClick(String... strings) {
                BundleDataBaseManager.getInstance().removeFromCategory(category);
                adapter.removeItem(category);
                adapter.notifyDataSetChanged();
                deleteMsg.dismiss();
            }
        });
        deleteMsg.show(getFragmentManager(), getClass().getCanonicalName());
    }


}
