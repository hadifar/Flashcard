package net.hadifar.dope.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.hadifar.dope.R;
import net.hadifar.dope.ui.fragment.dialogs.MessageDialog;
import net.hadifar.dope.ui.listeners.DialogButtonsClickListener;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Amir on 4/29/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public class ExceptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final MessageDialog messageDialog = new MessageDialog();
        messageDialog.init(R.string.title_exception, R.string.icon_info_outline, R.string.msg_error_happened_exception, R.string.btn_no, R.string.btn_go_to_app, false, new DialogButtonsClickListener() {
            @Override
            public void onLeftButtonClick() {
                messageDialog.dismiss();
                finish();

            }

            @Override
            public void onRightButtonClick(String... strings) {
                startActivity(new Intent(ExceptionActivity.this, MainActivity.class));
                messageDialog.dismiss();
                finish();
            }
        });

        messageDialog.show(getSupportFragmentManager(), "unHandleExceptionDialog");

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
