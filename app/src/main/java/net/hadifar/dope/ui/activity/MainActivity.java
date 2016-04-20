package net.hadifar.dope.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import net.hadifar.dope.R;
import net.hadifar.dope.ui.fragment.FragmentFlashCardViewer;

import net.hadifar.dope.ui.fragment.BaseFragment;
import net.hadifar.dope.ui.fragment.FragmentCategory;
import net.hadifar.dope.ui.fragment.FragmentAbout;
import net.hadifar.dope.ui.fragment.FragmentDecks;
import net.hadifar.dope.ui.fragment.FragmentFavoriteFlashCardList;
import net.hadifar.dope.ui.fragment.FragmentFavoriteFlashCardViewer;
import net.hadifar.dope.ui.fragment.FragmentLearningMethod;
import net.hadifar.dope.ui.widget.Toaster;

import java.util.Locale;


public class MainActivity extends BaseDrawerActivity implements TextToSpeech.OnInitListener {

    private static final String TAG_ACTIVE_FRAGMENT = "fragment_active";

    private BaseFragment activeFragment = null;
    private TextToSpeech mTextToSpeech = null;

    @Override
    public void displayView(int position, Bundle fragmentBundle) {

        FragmentManager fragmentManager = getSupportFragmentManager(); // Get the fragmentManager for this activity
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (position) {
            case CATEGORIES_FRAG:
                activeFragment = new FragmentCategory();
                clearBackStack();
                break;
            case DECKS_FRAG:
                activeFragment = new FragmentDecks();
                fragmentTransaction.addToBackStack(null);
                break;
            case LEARNING_FRAG:
                activeFragment = new FragmentLearningMethod();
                fragmentTransaction.addToBackStack(null);
                break;
            case FAVORITE_FRAG:
                activeFragment = new FragmentFavoriteFlashCardList();
                fragmentTransaction.addToBackStack(null);
                break;
            case FAVORITE_FRAG_VIEWER:
                activeFragment = new FragmentFavoriteFlashCardViewer();
                fragmentTransaction.addToBackStack(null);
                break;
            case SETTINGS_FRAG:
                break;
//            case FLASHCARDS_FRAG:
//                activeFragment = new FragmentFlashCardsList();
//                fragmentTransaction.addToBackStack(null);
//                break;
            case FLASHCARDS_VIEWER:
                activeFragment = new FragmentFlashCardViewer();
                fragmentTransaction.addToBackStack(null);
                break;
            case ABOUT_FRAG:
                activeFragment = new FragmentAbout();
                fragmentTransaction.addToBackStack(null);
            default:
                break;
        }

        if (activeFragment != null) {

            if (fragmentBundle != null) {
                activeFragment.setArguments(fragmentBundle);
            }

            fragmentTransaction
                    .setCustomAnimations(R.anim.alpha_in, R.anim.alpha_out, R.anim.alpha_in, R.anim.alpha_out) // Animations for the fragment out...
                    .add(R.id.frame_container, activeFragment, TAG_ACTIVE_FRAGMENT) // We then replace whatever is inside FrameLayout to our activeFragment
                    .commit();

        } else {
            Log.e("MainActivity", "Error creating fragment"); // if the fragment does not create we Log an error.
        }
    }

    @Override
    public void init() {
        mTextToSpeech = new TextToSpeech(this, this);
    }

    public void speakOut(String text) {
        if (Build.VERSION.SDK_INT < 21)
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        else
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
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


}
