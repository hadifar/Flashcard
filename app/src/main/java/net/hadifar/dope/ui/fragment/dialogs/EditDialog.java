package net.hadifar.dope.ui.fragment.dialogs;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import net.hadifar.dope.ui.listeners.DialogButtonsClickListener;
import net.hadifar.dope.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Amir on 4/12/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public class EditDialog extends BaseDialog {

    @Bind(R.id.btn_dialog_left)
    Button leftBtn;

    @Bind(R.id.btn_dialog_right)
    Button rightBtn;

    @Bind(R.id.btn_dialog_single)
    Button createBtn;

    @Bind(R.id.edittxt_first)
    EditText firstEditTxt;

    @Bind(R.id.edittxt_second)
    EditText secondEditTxt;

    @Bind(R.id.txt_input_layout_first)
    TextInputLayout firstInputLayout;

    @Bind(R.id.txt_input_layout_second)
    TextInputLayout secondInputLayout;

    private Object firstEtHint;
    private Object secondEtHint;
    private Object leftButtonTextResId;
    private Object rightButtonTextResId;
    private Object createButtonTexResId;

    private String firstHint;
    private String secondHint;

    private DialogButtonsClickListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getDialog() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        View view = inflater.inflate(R.layout.dialog_edit, container, false);
        ButterKnife.bind(this, view);

        initViews();

        return view;
    }

    private void initViews() {

        firstHint = this.firstEtHint instanceof String ? (String) this.firstEtHint : getString((Integer) this.firstEtHint);
        if (!TextUtils.isEmpty(firstHint)) {
            firstInputLayout.setHint(firstHint);
        } else {
            firstInputLayout.setVisibility(View.GONE);
        }

        secondHint = secondEtHint instanceof String ? (String) secondEtHint : getString((Integer) secondEtHint);
        if (!TextUtils.isEmpty(secondHint)) {
            secondInputLayout.setHint(secondHint);
        } else {
            secondInputLayout.setVisibility(View.GONE);
        }

        String leftBtnRes = leftButtonTextResId instanceof String ? (String) leftButtonTextResId : getString((Integer) leftButtonTextResId);
        if (!TextUtils.isEmpty(leftBtnRes)) {
            leftBtn.setText(leftBtnRes);
        } else {
            leftBtn.setVisibility(View.GONE);
        }
        String rightBtnRes = rightButtonTextResId instanceof String ? (String) rightButtonTextResId : getString((Integer) rightButtonTextResId);
        if (!TextUtils.isEmpty(rightBtnRes)) {
            rightBtn.setText(rightBtnRes);
        } else {
            rightBtn.setVisibility(View.GONE);
        }

        String createBtnRes = createButtonTexResId instanceof String ? (String) createButtonTexResId : getString((Integer) createButtonTexResId);
        if (!TextUtils.isEmpty(createBtnRes)) {
            createBtn.setText(createBtnRes);
        } else {
            createBtn.setVisibility(View.GONE);
        }
    }

    public void init(Object firstEditTxtHint, Object secondEditTxtHint, Object leftButtonTextResId, Object rightButtonTextResId, Object createButtonTexResId, DialogButtonsClickListener listener) {
        this.firstEtHint = firstEditTxtHint;
        this.secondEtHint = secondEditTxtHint;
        this.leftButtonTextResId = leftButtonTextResId;
        this.rightButtonTextResId = rightButtonTextResId;
        this.createButtonTexResId = createButtonTexResId;
        this.listener = listener;
    }

    private boolean isInputValid() {

        if ((firstEditTxt == null || TextUtils.isEmpty(firstEditTxt.getText())) && firstInputLayout.getVisibility() == View.VISIBLE) {
            firstInputLayout.setError("Set " + firstHint);
            return false;
        } else if ((secondEditTxt == null || TextUtils.isEmpty(secondEditTxt.getText())) && secondInputLayout.getVisibility() == View.VISIBLE) {
            secondInputLayout.setError("Set " + secondHint);
            return false;
        }

        return true;
    }

    @OnClick(R.id.btn_dialog_right)
    public void rightBtnClicked() {
        if (isInputValid()) {
            if (rightBtn.getVisibility() == View.VISIBLE && listener != null)
                listener.onRightButtonClick(firstEditTxt.getText().toString(), secondEditTxt.getText().toString());
        }
    }

    @OnClick(R.id.btn_dialog_left)
    public void leftBtnClicked() {
        if (leftBtn.getVisibility() == View.VISIBLE && listener != null)
            listener.onLeftButtonClick();
    }

    @OnClick(R.id.btn_dialog_single)
    public void createBtnClicked() {
        if (isInputValid()) {
            if (createBtn.getVisibility() == View.VISIBLE && listener != null)
                listener.onRightButtonClick(firstEditTxt.getText().toString(), secondEditTxt.getText().toString());
        }
    }
}
