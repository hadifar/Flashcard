package net.hadifar.dope.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import net.hadifar.dope.model.FlashCard;
import net.hadifar.dope.storage.BundleDataBaseManager;
import net.hadifar.dope.ui.fragment.FragmentFlashCardContent;

import java.util.List;

/**
 * Created by Amir on 4/20/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public class ContentAdapter extends FragmentStatePagerAdapter {

    private List<FlashCard> items;

    public ContentAdapter(FragmentManager fm, int selectedDock) {
        super(fm);
        items = BundleDataBaseManager.getInstance().getFlashCardsForDeckId(selectedDock);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new FragmentFlashCardContent();
        ((FragmentFlashCardContent) fragment).setCard(items.get(position));
        return fragment;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}


