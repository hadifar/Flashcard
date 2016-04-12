package com.march1905.dope.ui.fragment.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.march1905.dope.R;
import com.march1905.dope.core.BundleDataBaseManager;
import com.march1905.dope.utils.Utils;

/**
 * Amir Hadifar on 01/08/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @AmirHadifar
 */

public class NewDeckDialog extends BaseDialog implements DialogInterface.OnDismissListener {

    private Bundle mBundle;
    private BundleDataBaseManager dataBaseManager = BundleDataBaseManager.getInstance();

    private OnDBChangedListener mCallback;

    public interface OnDBChangedListener {
        public void onDBChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getDialog() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        View rootView = inflater.inflate(R.layout.dialog_new_deck, container, false);

        final Button btnAddDeck = (Button) rootView.findViewById(R.id.fab_add_new_deck);
        final EditText deckName = (EditText) rootView.findViewById(R.id.deckTitle);
        btnAddDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!deckName.getText().toString().isEmpty()) {
                    int mDeckCount = dataBaseManager.getLastDeckId() + 1;
                    //TODO uncomment
//                    dataBaseManager.addToDecks(new Deck(mDeckCount, deckName.getText().toString(), mBundle.getInt(CategoryAdapter.EXTRA_CATEGORY_ID)));
                    Utils.hideKeyboard(getActivity());
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


    public void setListener(OnDBChangedListener listener) {
        this.mCallback = listener;
    }
}
