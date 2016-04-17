package net.hadifar.dope.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import net.hadifar.dope.model.BaseEntity;
import net.hadifar.dope.model.FlashCard;
import net.hadifar.dope.ui.activity.MainActivity;
import net.hadifar.dope.ui.adapter.BaseListAdapter;
import net.hadifar.dope.ui.fragment.dialogs.EditLargeDialog;
import net.hadifar.dope.ui.fragment.dialogs.EditSmallDialog;
import net.hadifar.dope.ui.fragment.dialogs.MessageDialog;
import net.hadifar.dope.ui.listeners.DialogButtonsClickListener;
import net.hadifar.dope.R;
import net.hadifar.dope.storage.BundleDataBaseManager;

import java.util.List;

import butterknife.OnClick;

/**
 * Amir Hadifar on 29/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @AmirHadifar
 */

public class FragmentFlashCardsList extends BaseListFragment {

    public final static String EXTRA_DECK_ID = "extra_deck_id";

    private int selectedDeckId;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getBundles();

        List<? extends BaseEntity> entityList = BundleDataBaseManager.getInstance().getFlashCardsForDeckId(selectedDeckId);
        adapter = new BaseListAdapter(getActivity(), (List<BaseEntity>) entityList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        showFloatingButton();

    }

    private void getBundles() {
        Bundle mBundle = getArguments();
        setTitle(mBundle.getString(EXTRA_TITLE));
        selectedDeckId = mBundle.getInt(EXTRA_ID);
    }


    @OnClick(R.id.fab_add_new_card)
    public void fabClicked() {
        final EditLargeDialog editLargeDialog = new EditLargeDialog();
        editLargeDialog.init("", "", R.string.create_new_flashcard, new DialogButtonsClickListener() {
            @Override
            public void onLeftButtonClick() {
                editLargeDialog.dismiss();
            }

            @Override
            public void onRightButtonClick(String... strings) {

                int newFlashcardId = BundleDataBaseManager.getInstance().getLastFlashCardId() + 1;
                FlashCard flashCard = new FlashCard(newFlashcardId, strings[0], strings[1], strings[2], strings[3], strings[4], strings[5], strings[6], selectedDeckId);
                BundleDataBaseManager.getInstance().addToFlashCard(flashCard);
                adapter.addItem(flashCard);
                adapter.notifyDataSetChanged();
                editLargeDialog.dismiss();
            }
        });
        editLargeDialog.show(getFragmentManager(), getClass().getCanonicalName());
    }

    @Override
    public void onRootClick(BaseEntity entity) {

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TITLE, entity.getTitle());
        bundle.putInt(EXTRA_ID, entity.getId());
        bundle.putInt(EXTRA_DECK_ID, selectedDeckId);

        ((MainActivity) getActivity()).displayView(MainActivity.FLASHCARDS_VIEWER, bundle);
    }

    @Override
    public void onMoreClick(View v, final BaseEntity baseEntity) {

        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_overflow, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_edit:
                        DialogEdit(baseEntity);
                        return true;
                    case R.id.action_delete:
//                        DialogDelete(flashcard);
                        return true;
                }
                return false;
            }
        });

    }


    public void DialogEdit(final BaseEntity entity) {

        final FlashCard flashCard = BundleDataBaseManager.getInstance().getFlashcard4Id(entity.id);

        final EditSmallDialog editMsg = new EditSmallDialog();
        editMsg.init(R.string.hint_deck_name, R.string.hint_deck_subtitle, R.string.btn_cancel, R.string.btn_done, "", new DialogButtonsClickListener() {
            @Override
            public void onLeftButtonClick() {
                editMsg.dismiss();
            }

            @Override
            public void onRightButtonClick(String... strings) {
                //TODO update content of flashcard
                flashCard.setTitle(strings[0]);
                flashCard.setSubtitle(strings[1]);
                BundleDataBaseManager.getInstance().editFromFlashCard(flashCard);
                adapter.notifyDataSetChanged();
                editMsg.dismiss();
            }
        });
        editMsg.show(getFragmentManager(), "activeFragment");
    }

    public void DialogDelete(BaseEntity entity) {

        final FlashCard flashCard = BundleDataBaseManager.getInstance().getFlashcard4Id(entity.id);

        final MessageDialog deleteMsg = new MessageDialog();
        deleteMsg.init(R.string.title_delete, R.string.icon_delete, R.string.title_are_you_sure_to_delete_category, R.string.btn_no, R.string.btn_yes, false, new DialogButtonsClickListener() {
            @Override
            public void onLeftButtonClick() {
                deleteMsg.dismiss();
            }

            @Override
            public void onRightButtonClick(String... strings) {
                BundleDataBaseManager.getInstance().removeFromFlashCard(flashCard);
                adapter.removeItem(flashCard);
                adapter.notifyDataSetChanged();
                deleteMsg.dismiss();
            }
        });
        deleteMsg.show(getFragmentManager(), "activeFragment");
    }

}
