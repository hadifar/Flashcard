package com.march1905.dope.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.march1905.dope.R;
import com.march1905.dope.ui.fragment.DefaultFragment;
import com.march1905.dope.ui.fragment.FragmentCategory;
import com.march1905.dope.ui.fragment.FragmentAbout;
import com.march1905.dope.ui.fragment.FragmentDecks;
import com.march1905.dope.ui.fragment.FragmentFavoriteFlashCardList;
import com.march1905.dope.ui.fragment.FragmentFavoriteFlashCardViewer;
import com.march1905.dope.ui.fragment.FragmentFlashCardViewer;
import com.march1905.dope.ui.fragment.FragmentFlashCardsList;


public class MainActivity extends BaseDrawerActivity {

    private static final String TAG_ACTIVE_FRAGMENT = "fragment_active";

    public static final int FLASHCARDS_VIEWER = 3;
    public static final int FLASHCARDS_FRAG = 2;
    public static final int DECKS_FRAG = 1;
    public static final int CATEGORIES_FRAG = 0;

    public static final int SETTINGS_FRAG = 4;
    public static final int ABOUT_FRAG = 5;
    public static final int FAVORITE_FRAG = 6;
    public static final int FAVORITE_FRAG_VIEWER = 7;

    private DefaultFragment activeFragment = null;

    @Override
    public void restoreFragment(Bundle savedInstanceState) {
        activeFragment = (DefaultFragment) getSupportFragmentManager().getFragment(savedInstanceState, "activeFragment");
    }

    @Override
    public void displayView(int position, Bundle fragmentBundle) {

        FragmentManager fragmentManager = getSupportFragmentManager(); // Get the fragmentManager for this activity
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (position) {
            case CATEGORIES_FRAG:
                activeFragment = new FragmentCategory();
                clearBackStack();
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
            case DECKS_FRAG:
                activeFragment = new FragmentDecks();
                fragmentTransaction.addToBackStack(null);
                break;
            case FLASHCARDS_FRAG:
                activeFragment = new FragmentFlashCardsList();
                fragmentTransaction.addToBackStack(null);
                break;
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

}
