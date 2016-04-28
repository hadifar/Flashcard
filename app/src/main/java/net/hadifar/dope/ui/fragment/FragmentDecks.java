package net.hadifar.dope.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import net.hadifar.dope.R;
import net.hadifar.dope.model.BaseEntity;
import net.hadifar.dope.model.Deck;
import net.hadifar.dope.storage.BundleDataBaseManager;
import net.hadifar.dope.ui.activity.MainActivity;
import net.hadifar.dope.ui.adapter.ListAdapter;
import net.hadifar.dope.ui.fragment.dialogs.EditSmallDialog;
import net.hadifar.dope.ui.fragment.dialogs.MessageDialog;
import net.hadifar.dope.ui.listeners.DialogButtonsClickListener;
import net.hadifar.dope.ui.listeners.OnCardClickListener;

import java.util.List;

import butterknife.OnClick;

/**
 * Amir Hadifar on 29/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @AmirHadifar
 */

public class FragmentDecks extends BaseListFragment implements OnCardClickListener {

    public static final String EXTRA_CATEGORY_ID = "extra_category_id";

    private int selectedCategoryId;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getBundles();

        List<? extends BaseEntity> entityList = BundleDataBaseManager.getInstance().getDecksForCategoryId(selectedCategoryId);
        adapter = new ListAdapter(getActivity(), (List<BaseEntity>) entityList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        showFloatingButton();
    }

    private void getBundles() {
        Bundle mBundle = getArguments();
        setTitle(mBundle.getString(EXTRA_TITLE));
        selectedCategoryId = mBundle.getInt(EXTRA_ID);
    }

    @Override
    public void onRootClick(BaseEntity entity) {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_CATEGORY_ID, selectedCategoryId);
        bundle.putInt(EXTRA_ID, entity.getId());
        bundle.putString(EXTRA_TITLE, entity.getTitle());
        ((MainActivity) getActivity()).displayView(MainActivity.FLASHCARDS_FRAG, bundle);
//        startActivity(FlashCardViewerActivity.createIntent(getActivity(), entity.getId()));

    }

    @Override
    public void onMoreClick(View v, BaseEntity baseEntity) {

        final Deck deck = (Deck) baseEntity;

        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_overflow, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_edit:
                        DialogEdit(deck);
                        return true;
                    case R.id.action_delete:
                        DialogDelete(deck);
                        return true;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.fab_add_new_card)
    public void fabClicked() {
        final EditSmallDialog newDeckDialog = new EditSmallDialog();
        newDeckDialog.init(R.string.hint_deck_name, R.string.hint_deck_subtitle, "", "", R.string.create_new_deck, new DialogButtonsClickListener() {
            @Override
            public void onLeftButtonClick() {
                newDeckDialog.dismiss();
            }

            @Override
            public void onRightButtonClick(String... strings) {

                int newDeckId = BundleDataBaseManager.getInstance().getLastDeckId() + 1;
                Deck deck = new Deck(newDeckId, strings[0], strings[1], selectedCategoryId);
                BundleDataBaseManager.getInstance().addToDecks(deck);
                adapter.addItem(deck);
                newDeckDialog.dismiss();
            }
        });
        newDeckDialog.show(getFragmentManager(), getClass().getCanonicalName());
    }

    public void DialogEdit(final Deck deck) {

        final EditSmallDialog editMsg = new EditSmallDialog();
        editMsg.init(R.string.hint_deck_name, R.string.hint_deck_subtitle, R.string.btn_cancel, R.string.btn_done, "", new DialogButtonsClickListener() {
            @Override
            public void onLeftButtonClick() {
                editMsg.dismiss();
            }

            @Override
            public void onRightButtonClick(String... strings) {
                deck.setTitle(strings[0]);
                deck.setSubtitle(strings[1]);
                BundleDataBaseManager.getInstance().editFromDeck(deck);
                adapter.notifyDataSetChanged();
                editMsg.dismiss();
            }
        });
        editMsg.show(getFragmentManager(), "activeFragment");
    }

    public void DialogDelete(final Deck deck) {

        final MessageDialog deleteMsg = new MessageDialog();
        deleteMsg.init(R.string.title_delete, R.string.icon_delete, R.string.title_are_you_sure_to_delete_deck, R.string.btn_no, R.string.btn_yes, false, new DialogButtonsClickListener() {
            @Override
            public void onLeftButtonClick() {
                deleteMsg.dismiss();
            }

            @Override
            public void onRightButtonClick(String... strings) {
                BundleDataBaseManager.getInstance().removeFromDeck(deck);
                adapter.removeItem(deck);
                adapter.notifyDataSetChanged();
                deleteMsg.dismiss();
            }
        });
        deleteMsg.show(getFragmentManager(), "activeFragment");
    }

}
