package com.march1905.dope.ui.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.march1905.dope.R;
import com.march1905.dope.core.AppDataBaseManager;
import com.march1905.dope.ui.widget.pagerIndicator.NumericPageIndicator;
import com.march1905.dope.model.FlashCard;
import com.march1905.dope.model.FlashCardFavoritedItems;

import java.util.List;

/**
 * Amir Hadifar on 31/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @HadifarAmir
 */

public class FragmentFavoriteFlashCardViewer extends DefaultFragment {

    Bundle mBundle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flashcard_viewer, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mBundle = getArguments();
        if (mBundle == null)
            mBundle = savedInstanceState;

        getActivity().setTitle(R.string.drawer_item_favorites);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        NumericPageIndicator pageIndicator = (NumericPageIndicator) view.findViewById(R.id.pageIndicator);
        pageIndicator.setViewPager(mViewPager);

        mViewPager.setCurrentItem(mBundle.getInt(FragmentFavoriteFlashCardList.EXTRA_FAV_FLASHCARD_ID));

        Typeface mFont = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "font/fontawesome-webfont.ttf");
        pageIndicator.setTypeface(mFont);

    }


    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private Fragment mFragment;
        private List<FlashCardFavoritedItems> mItems;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            mItems = AppDataBaseManager.getInstance().getFavoritedFlashCardItems();
        }

        @Override
        public Fragment getItem(int position) {

            mFragment = new FragmentFlashCardContent();
            ((FragmentFlashCardContent) mFragment).setCard(new FlashCard(mItems.get(position)));
            return mFragment;
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
    }

}
