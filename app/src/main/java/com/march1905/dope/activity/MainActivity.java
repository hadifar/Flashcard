package com.march1905.dope.activity;


import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.march1905.dope.R;
import com.march1905.dope.fragment.DefaultFragment;
import com.march1905.dope.fragment.FragmentCategories;
import com.march1905.dope.fragment.FragmentContact;
import com.march1905.dope.fragment.FragmentDecks;
import com.march1905.dope.fragment.FragmentFavoriteFlashCardList;
import com.march1905.dope.fragment.FragmentFavoriteFlashCardViewer;
import com.march1905.dope.fragment.FragmentFlashCardViewer;
import com.march1905.dope.fragment.FragmentFlashCardsList;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends BaseDrawerActivity implements TextToSpeech.OnInitListener {

    private static final String TAG_ACTIVE_FRAGMENT = "fragment_active";

    public static final String EXTRA_TITLE = "title";

    // We use this to know which of the items has been selected.
    // We name the items so we know which one is which.
    // For the fragments that will be OUTSIDE of the drawer layout we use negative numbers so we avoid a conflict.
    public static final int FLASHCARDS_VIEWER = -6;
    public static final int FLASHCARDS_FRAG = -5;
    public static final int DECKS_FRAG = -4;

    public static final int NEW_FLASHCARD_FRAG = -3;
    public static final int NEW_DECK_FRAG = -2;
    public static final int NEW_CATEGORY_FRAG = -1;


    public static final int CATEGORIES_FRAG = 0;
    public static final int SETTINGS_FRAG = 3;
    public static final int CONTACT_FRAG = 6;
    public static final int FAVORITE_FRAG = 1;
    public static final int FAVORITE_FRAG_VIEWER = 2;


    private SparseArray<String> fragmentTitles;
    private DefaultFragment activeFragment = null;


    ///private Bundle currentBundle;
    private static TextToSpeech mTextToSpeech;

    @Override
    public void restoreFragment(Bundle savedInstanceState) {
        //Restore the fragment's instance
        activeFragment = (DefaultFragment) getSupportFragmentManager().getFragment(savedInstanceState, "activeFragment");
    }

    @Override
    public void init() {

        mTextToSpeech = new TextToSpeech(this, this);
//        // Retrieve the typedArray from the XML. Notice the weird Syntax "obtain"
//        TypedArray navIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
//        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_titles); // Retrieve the titles
//        navDrawerItems = new ArrayList<NavDrawerItem>(); // Initialize the ArrayList
//
//        // Now let's add add items to the ArrayList of NavDrawer items.
//        for (int i = 0; i < navMenuTitles.length; i++) {
//            navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], navIcons.getDrawable(i)));
//        }
//        // An typed array can be recycled to avoid waste of System Resources. In our case it wouldn't matter because we only have 2 items.. but is still a good practice.
//        navIcons.recycle();
//
//        mNavDrawerAdapter = new NavDrawerAdapter(this, navDrawerItems);
//
        // We need a HashMap to map the Title of the fragments that are not on our Nav Drawer
        fragmentTitles = new SparseArray<>();
        fragmentTitles.put(NEW_CATEGORY_FRAG, getString(R.string.new_cat_title));
        fragmentTitles.put(NEW_DECK_FRAG, getString(R.string.new_deck_title));
        fragmentTitles.put(NEW_FLASHCARD_FRAG, getString(R.string.new_flashcard_title));
    }


    @Override
    public void displayView(int position, Bundle fragmentBundle) {

        FragmentManager fragmentManager = getSupportFragmentManager(); // Get the fragmentManager for this activity
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (position) {
            case CATEGORIES_FRAG:
                activeFragment = new FragmentCategories(); // Set the ActiveFragment to our selected item on the list
                clearBackStack(); // Clear the back stack to avoid back presses bugs
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
                fragmentTransaction.addToBackStack(null); // null = name of the fragment on the stack.
                break;
            case FLASHCARDS_FRAG:
                activeFragment = new FragmentFlashCardsList();
                fragmentTransaction.addToBackStack(null);
                break;
            case FLASHCARDS_VIEWER:
                activeFragment = new FragmentFlashCardViewer();
                fragmentTransaction.addToBackStack(null);
                break;
            case CONTACT_FRAG:
                activeFragment = new FragmentContact();
                fragmentTransaction.addToBackStack(null);
            default:
                break;
        }

        if (activeFragment != null) {
            if (fragmentBundle != null) {
                //currentBundle = fragmentBundle;
                activeFragment.setArguments(fragmentBundle);
            }

            fragmentTransaction.setCustomAnimations(R.anim.alpha_in, R.anim.alpha_out, // Animations for the fragment in...
                    R.anim.alpha_in, R.anim.alpha_out) // Animations for the fragment out...
                    .replace(R.id.frame_container, activeFragment, TAG_ACTIVE_FRAGMENT) // We then replace whatever is inside FrameLayout to our activeFragment
                    .commit(); // Commit the change
            if (position >= 0) {
                //setTitle("Dope");
//                getSupportActionBar().setIcon(R.drawable.ic_launcher);
//                setTitle();
               // getDrawer().setSelection(position);
            } else {
                if (fragmentBundle == null) {
                    setTitle(fragmentTitles.get(position)); // We not change the title of the Action Bar to match our fragment.
                } else {
                    setTitle(fragmentBundle.getString(MainActivity.EXTRA_TITLE));
                }
            }
        } else {
            Log.i("MainActivity", "Error creating fragment"); // if the fragment does not create we Log an error.
        }
    }





    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = mTextToSpeech.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, R.string.your_language_not_supported_please_install, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.please_install_google_voice, Toast.LENGTH_LONG).show();
            Intent installIntent = new Intent();
            installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(installIntent);
        }

    }


    public void onDestroy() {
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
        super.onDestroy();
    }

    public static void speakOut(String text) {
        if (Build.VERSION.SDK_INT < 21)
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        else
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

}
