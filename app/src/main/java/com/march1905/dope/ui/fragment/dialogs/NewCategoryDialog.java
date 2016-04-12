//package com.march1905.dope.ui.fragment.dialogs;
//
//import android.os.Bundle;
//import android.support.v4.app.DialogFragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.march1905.dope.R;
//import com.march1905.dope.core.BundleDataBaseManager;
//import com.march1905.dope.model.Category;
//import com.march1905.dope.ui.activity.MainActivity;
//import com.march1905.dope.ui.listeners.DialogButtonsClickListener;
//import com.march1905.dope.utils.Utils;
//
//import butterknife.Bind;
//import butterknife.OnClick;
//
///**
// * Amir Hadifar on 31/07/2015
// * Cardy
// * Email : Hadifar.amir@gmail.com
// * Twitter : @AmirHadifar
// */
//
//
//public class NewCategoryDialog extends BaseDialog {
//
////    private BundleDataBaseManager dataBaseManager = BundleDataBaseManager.getInstance();
//
//    DialogButtonsClickListener listener;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        if (getDialog() != null) {
//            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        }
//        View rootView = inflater.inflate(R.layout.dialog_new_category, container, false);
//
//        final Button btnAddCategory = (Button) rootView.findViewById(R.id.fab_add_new_category);
//        final EditText categoryName = (EditText) rootView.findViewById(R.id.categoryTitle);
//
//
//        btnAddCategory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        return rootView;
//    }
//
//
//    @OnClick(R.id.fab_add_new_category)
//    public void fabClicked(){
//
//    }
//}
