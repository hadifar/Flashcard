package com.march1905.dope.ui.fragment.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.march1905.dope.R;
import com.march1905.dope.core.BundleDataBaseManager;
import com.march1905.dope.model.Category;
import com.march1905.dope.ui.activity.MainActivity;
import com.march1905.dope.utils.Utils;

/**
 * Amir Hadifar on 31/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @AmirHadifar
 */


public class NewCategoryDialog extends DialogFragment {

    private BundleDataBaseManager dataBaseManager = BundleDataBaseManager.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getDialog() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        View rootView = inflater.inflate(R.layout.dialog_new_category, container, false);

        final Button btnAddCategory = (Button) rootView.findViewById(R.id.fab_add_new_category);
        final EditText categoryName = (EditText) rootView.findViewById(R.id.categoryTitle);


        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!categoryName.getText().toString().isEmpty()) {

                    int mCategoryCount = dataBaseManager.getLastCategoryId() + 1;

                    dataBaseManager.addToCategory(new Category(mCategoryCount, categoryName.getText().toString(), "This Category made by User"));
                    Utils.hideKeyboard(getActivity());
                    dismiss();
                    ((MainActivity) getActivity()).displayView(MainActivity.CATEGORIES_FRAG, null);
                } else {
                    Toast.makeText(getActivity(), "Category MUST have a title.", Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootView;
    }


}
