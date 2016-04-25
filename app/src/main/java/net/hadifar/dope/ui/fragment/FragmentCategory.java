package net.hadifar.dope.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import net.hadifar.dope.R;
import net.hadifar.dope.model.BaseEntity;
import net.hadifar.dope.model.Category;
import net.hadifar.dope.storage.BundleDataBaseManager;
import net.hadifar.dope.ui.activity.MainActivity;
import net.hadifar.dope.ui.adapter.BaseListAdapter;
import net.hadifar.dope.ui.fragment.dialogs.EditSmallDialog;
import net.hadifar.dope.ui.fragment.dialogs.MessageDialog;
import net.hadifar.dope.ui.listeners.DialogButtonsClickListener;
import net.hadifar.dope.ui.listeners.OnCardClickListener;

import java.util.List;

import butterknife.OnClick;

/**
 * Amir Hadifar on 27/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @AmirHadifar
 */

public class FragmentCategory extends BaseListFragment implements OnCardClickListener {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<? extends BaseEntity> entityList = BundleDataBaseManager.getInstance().getAllCategories();
        adapter = new BaseListAdapter(getActivity(), (List<BaseEntity>) entityList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        showFloatingButton();
    }

    @OnClick(R.id.fab_add_new_card)
    public void fabClicked() {
        final EditSmallDialog newCategoryDialog = new EditSmallDialog();
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
                adapter.notifyDataSetChanged();
                newCategoryDialog.dismiss();
            }
        });
        newCategoryDialog.show(getFragmentManager(), getClass().getCanonicalName());
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

        final EditSmallDialog editMsg = new EditSmallDialog();
        editMsg.init(R.string.hint_category_name, R.string.hint_category_subtitle, R.string.btn_cancel, R.string.btn_done, "", new DialogButtonsClickListener() {
            @Override
            public void onLeftButtonClick() {
                editMsg.dismiss();
            }

            @Override
            public void onRightButtonClick(String... strings) {
                category.setTitle(strings[0]);
                category.setSubtitle(strings[1]);
                BundleDataBaseManager.getInstance().editFromCategory(category);
                adapter.notifyDataSetChanged();
                editMsg.dismiss();
            }
        });
        editMsg.show(getFragmentManager(), "activeFragment");
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
        deleteMsg.show(getFragmentManager(), "activeFragment");
    }


}
