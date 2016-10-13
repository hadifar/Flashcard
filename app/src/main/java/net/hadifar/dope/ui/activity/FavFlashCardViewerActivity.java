package net.hadifar.dope.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import net.hadifar.dope.R;
import net.hadifar.dope.model.FlashCard;
import net.hadifar.dope.model.FlashCardFavoritedItems;
import net.hadifar.dope.storage.AppDataBaseManager;
import net.hadifar.dope.ui.fragment.FragmentFlashCardContent;
import net.hadifar.dope.ui.widget.Toaster;
import net.hadifar.dope.ui.widget.progressbar.LinearProgress;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Amir on 10/13/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */

public class FavFlashCardViewerActivity extends BaseActivity implements TextToSpeech.OnInitListener {
    private final static String EXTRA_ID = "extra_id";

    private int selectedDockId = 1;

    private TextToSpeech mTextToSpeech;

    @Bind(R.id.lp_progressbar)
    LinearProgress progress;

    @Bind(R.id.pager)
    ViewPager viewPager;

    public static Intent createIntent(Context context, int selectedDeckId) {
        Intent intent = new Intent(context, FavFlashCardViewerActivity.class);
        intent.putExtra(EXTRA_ID, selectedDeckId);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        setContentView(R.layout.activity_flashcard_viewer);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            selectedDockId = getIntent().getIntExtra(EXTRA_ID, 1);
        } else {
            selectedDockId = savedInstanceState.getInt(EXTRA_ID, 1);
        }

        initData();

        setupViewPager();

    }

    private void initData() {
        mTextToSpeech = new TextToSpeech(this, this);
    }

    private void setupViewPager() {

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        final int totalCount = mSectionsPagerAdapter.getCount();

        viewPager.setAdapter(mSectionsPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateProgress(position, totalCount);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        updateProgress(0, totalCount);
    }

    private void updateProgress(int position, int totalCount) {
        int percent = (int) Math.ceil(((position + 1) * 100) / totalCount);
        progress.setProgress(percent);
    }

    public void speakOut(String text) {
        if (Build.VERSION.SDK_INT < 21)
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        else
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @OnClick(R.id.btn_close_viewer)
    public void onCloseViewerClick() {
        super.onBackPressed();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = mTextToSpeech.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toaster.toast(this, R.string.msg_your_language_not_supported_please_install, Toast.LENGTH_SHORT);
            }
        } else {
            Toaster.toast(this, R.string.msg_please_install_google_voice, Toast.LENGTH_LONG);
            Intent installIntent = new Intent();
            installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(installIntent);
        }
    }

    @Override
    public void onDestroy() {
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
        super.onDestroy();
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_back_to_left, R.anim.slide_in_back_from_right);
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
