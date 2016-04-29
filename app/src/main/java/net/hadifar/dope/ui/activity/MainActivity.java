package net.hadifar.dope.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import net.hadifar.dope.R;
import net.hadifar.dope.ui.fragment.BaseFragment;
import net.hadifar.dope.ui.fragment.FragmentAbout;
import net.hadifar.dope.ui.fragment.FragmentCategory;
import net.hadifar.dope.ui.fragment.FragmentDecks;
import net.hadifar.dope.ui.fragment.FragmentFlashCardsList;
import net.hadifar.dope.ui.fragment.FragmentLearningMethod;
import net.hadifar.dope.ui.fragment.FragmentSettings;


public class MainActivity extends BaseDrawerActivity {

    private static final String TAG_ACTIVE_FRAGMENT = "fragment_active";

    private BaseFragment activeFragment = null;

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
            case FLASHCARDS_FRAG:
                activeFragment = new FragmentFlashCardsList();
                fragmentTransaction.addToBackStack(null);
                break;
            case LEARNING_FRAG:
                activeFragment = new FragmentLearningMethod();
                fragmentTransaction.addToBackStack(null);
                setToolbarTitle(R.string.menu_favorite);
                break;
//            case FAVORITE_FRAG:
//                activeFragment = new FragmentFavoriteFlashCardList();
//                fragmentTransaction.addToBackStack(null);
//                break;
//            case FAVORITE_FRAG_VIEWER:
//                activeFragment = new FragmentFavoriteFlashCardViewer();
//                fragmentTransaction.addToBackStack(null);
//                break;
            case SETTINGS_FRAG:
                activeFragment = new FragmentSettings();
                fragmentTransaction.addToBackStack(null);
                setToolbarTitle(R.string.nav_settings);
                break;
            case ABOUT_FRAG:
                activeFragment = new FragmentAbout();
                fragmentTransaction.addToBackStack(null);
                setToolbarTitle(R.string.menu_about);
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


}
