package com.march1905.dope.ui.fragment.dialogs;

import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.march1905.dope.R;
import com.march1905.dope.core.BundleDataBaseManager;
import com.march1905.dope.ui.fragment.FragmentDecks;
import com.march1905.dope.model.FlashCard;
import com.march1905.dope.utils.Utils;

/**
 * Amir Hadifar on 01/08/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @HadifarAmir
 */

public class FragmentNewFlashCard extends DialogFragment implements DialogInterface.OnDismissListener {

    private Bundle mBundle;
    private BundleDataBaseManager dataBaseManager = new BundleDataBaseManager();

    private OnDBChangedListener mCallback;

    public interface OnDBChangedListener {
        public void onDBChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getDialog() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        final View rootView = inflater.inflate(R.layout.fragment_new_flashcard, container, false);

        Button btnAddFlashcard = (Button) rootView.findViewById(R.id.fab_add_new_flashcard);
        btnAddFlashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //our flash card attrs
                EditText flashcardName = (EditText) rootView.findViewById(R.id.flashcardWord);
                String strName = flashcardName.getText().toString();

                EditText synonym = (EditText) rootView.findViewById(R.id.flashcardSynonym);
                String strSynonym = synonym.getText().toString();

                EditText persian = (EditText) rootView.findViewById(R.id.flashcardPersian);
                String strPersian = persian.getText().toString();

                EditText pronunciation = (EditText) rootView.findViewById(R.id.flashcardPronunciation);
                String strPronunciation = pronunciation.getText().toString();

                EditText ex1 = (EditText) rootView.findViewById(R.id.flashcardEx1);
                String strEx1 = ex1.getText().toString();

                EditText ex2 = (EditText) rootView.findViewById(R.id.flashcardEx2);
                String strEx2 = ex2.getText().toString();

                EditText ex3 = (EditText) rootView.findViewById(R.id.flashcardEx3);
                String strEx3 = ex3.getText().toString();

                if (!strName.isEmpty()) {

                    int mFlashCardID = dataBaseManager.getLastFlashCardId() + 1;

                    dataBaseManager.addToFlashCard(new FlashCard(mFlashCardID, strName, strPersian,
                            strSynonym, strPronunciation, strEx1, strEx2, strEx3, mBundle.getInt(FragmentDecks.EXTRA_DECK_ID)));

                    Utils.hideKeyboard(getActivity());
                    mCallback.onDBChanged();
                    dismiss();

                } else {
                    //TODO:flashcard must have title with animation
                    Toast.makeText(getActivity(), "FlashCard MUST Have a Word.", Toast.LENGTH_LONG).show();
                }
            }
        });


        return rootView;
    }


    public void setBundle(Bundle bundle) {
        mBundle = bundle;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStart() {
        super.onStart();

        // change dialog width
        if (getDialog() != null) {

            int fullWidth;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                fullWidth = size.x;
            } else {
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                fullWidth = display.getWidth();
            }

            final int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                    .getDisplayMetrics());

            int w = fullWidth - padding;
            int h = getDialog().getWindow().getAttributes().height;
            getDialog().getWindow().setLayout(w, h);
        }
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    public void setListener(OnDBChangedListener listener) {
        this.mCallback = listener;
    }
}