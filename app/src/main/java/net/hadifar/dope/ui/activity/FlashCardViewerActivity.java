package net.hadifar.dope.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import android.widget.Toast;

import net.hadifar.dope.R;
import net.hadifar.dope.ui.adapter.FragmentContentAdapter;
import net.hadifar.dope.ui.widget.Toaster;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Amir on 4/21/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public class FlashCardViewerActivity extends BaseActivity implements TextToSpeech.OnInitListener {

    private final static String EXTRA_ID = "extra_id";


    private int selectedDockId = 1;

    private TextToSpeech mTextToSpeech;

    public static Intent createIntent(Context context, int selectedDeckId) {
        Intent intent = new Intent(context, FlashCardViewerActivity.class);
        intent.putExtra(EXTRA_ID, selectedDeckId);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //todo translucent color issue
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
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        FragmentContentAdapter mSectionsPagerAdapter = new FragmentContentAdapter(getSupportFragmentManager(), selectedDockId);
        viewPager.setAdapter(mSectionsPagerAdapter);
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

    //todo can we intilize this on FragmentAdapter and access to txt2Speech in fragment ?
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = mTextToSpeech.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toaster.toast(this, R.string.your_language_not_supported_please_install, Toast.LENGTH_SHORT);
            }
        } else {
            Toaster.toast(this, R.string.please_install_google_voice, Toast.LENGTH_LONG);
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
