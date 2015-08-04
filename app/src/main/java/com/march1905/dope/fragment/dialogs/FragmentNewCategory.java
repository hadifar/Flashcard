package com.march1905.dope.fragment.dialogs;

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
import com.march1905.dope.activity.MainActivity;
import com.march1905.dope.core.BundleDataBaseManager;
import com.march1905.dope.model.Category;
import com.march1905.dope.utils.GeneralHelper;

/**
 * Amir Hadifar on 31/07/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @HadifarAmir
 */


public class FragmentNewCategory extends DialogFragment {

    private BundleDataBaseManager dataBaseManager = new BundleDataBaseManager();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getDialog() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        View rootView = inflater.inflate(R.layout.fragment_new_category, container, false);

        final Button btnAddCategory = (Button) rootView.findViewById(R.id.addNewCategory);
        final EditText categoryName = (EditText) rootView.findViewById(R.id.categoryTitle);


        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!categoryName.getText().toString().isEmpty()) {

                    int mCategoryCount = dataBaseManager.getLastCategoryId() + 1;

                    dataBaseManager.addToCategory(new Category(mCategoryCount, categoryName.getText().toString(), "This Category made by User"));
                    GeneralHelper.hideKeyboard(getActivity());
                    dismiss();
                    ((MainActivity) getActivity()).displayView(MainActivity.CATEGORIES_FRAG, null);
                } else {
                    Toast.makeText(getActivity(), "Category MUST have a title.", Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootView;
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


}
