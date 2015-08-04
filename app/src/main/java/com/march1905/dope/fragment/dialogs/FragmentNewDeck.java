package com.march1905.dope.fragment.dialogs;

import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
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
import com.march1905.dope.fragment.FragmentCategories;
import com.march1905.dope.model.Deck;
import com.march1905.dope.utils.GeneralHelper;

/**
 * Amir Hadifar on 01/08/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @HadifarAmir
 */

public class FragmentNewDeck extends DialogFragment implements DialogInterface.OnDismissListener {

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
        View rootView = inflater.inflate(R.layout.fragment_new_deck, container, false);

        final Button btnAddDeck = (Button) rootView.findViewById(R.id.addNewDeck);
        final EditText deckName = (EditText) rootView.findViewById(R.id.deckTitle);
        btnAddDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!deckName.getText().toString().isEmpty()) {
                    int mDeckCount = dataBaseManager.getLastDeckId() + 1;
                    dataBaseManager.addToDecks(new Deck(mDeckCount, deckName.getText().toString(), mBundle.getInt(FragmentCategories.EXTRA_CATEGORY_ID)));
                    GeneralHelper.hideKeyboard(getActivity());
                    mCallback.onDBChanged();
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Deck MUST have a title.", Toast.LENGTH_LONG).show();
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
