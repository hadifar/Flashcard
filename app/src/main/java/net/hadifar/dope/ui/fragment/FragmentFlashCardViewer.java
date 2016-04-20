package net.hadifar.dope.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import net.hadifar.dope.R;
import net.hadifar.dope.model.FlashCard;
import net.hadifar.dope.storage.AppDataBaseManager;
import net.hadifar.dope.ui.adapter.FragmentContentAdapter;
import net.hadifar.dope.ui.widget.Toaster;
import net.hadifar.dope.ui.widget.pagerIndicator.NumericPageIndicator;
import net.hadifar.dope.utils.AnimationHelper;
import net.hadifar.dope.utils.FontHelper;

import java.util.Locale;

/**
 * Amir Hadifar on 29/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @AmirHadifar
 */

public class FragmentFlashCardViewer extends BaseFragment  {

    private int selectedDockId = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flashcard_viewer, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        getBundle();

        setupViewPager(view);

    }

    private void getBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            selectedDockId = bundle.getInt(EXTRA_ID);
            setTitle(bundle.getString(EXTRA_TITLE));
        }
    }

    private void setupViewPager(View view) {

        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.pager);
        NumericPageIndicator pageIndicator = (NumericPageIndicator) view.findViewById(R.id.pageIndicator);

        FragmentContentAdapter mSectionsPagerAdapter = new FragmentContentAdapter(getFragmentManager(), selectedDockId);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        pageIndicator.setViewPager(mViewPager);

        pageIndicator.setTypeface(FontHelper.getInstance(getActivity()).getIconTypeface());
    }



}
