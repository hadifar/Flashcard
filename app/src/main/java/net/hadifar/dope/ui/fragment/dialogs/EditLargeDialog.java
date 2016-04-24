package net.hadifar.dope.ui.fragment.dialogs;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import net.hadifar.dope.model.FlashCard;
import net.hadifar.dope.ui.listeners.LargeDialogClickListener;
import net.hadifar.dope.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Amir Hadifar on 01/08/2015
 * Flashcard
 * Email : Hadifar.amir@gmail.com
 * Twitter : @AmirHadifar
 */

public class EditLargeDialog extends BaseDialog {

    @Bind(R.id.btn_dialog_left)
    Button leftBtn;
    @Bind(R.id.btn_dialog_right)
    Button rightBtn;
    @Bind(R.id.btn_dialog_create)
    Button createBtn;

    @Bind(R.id.et_word)
    EditText wordEditTxt;
    @Bind(R.id.et_synonym)
    EditText synonymEditTxt;
    @Bind(R.id.et_translate)
    EditText translateEditTxt;
    @Bind(R.id.et_pronunciation)
    EditText pronunciationEditTxt;


    private FlashCard flashCard;

    private Object leftButtonTextResId;
    private Object rightButtonTextResId;
    private Object createButtonTexResId;

    private LargeDialogClickListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getDialog() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        View view = inflater.inflate(R.layout.dialog_edit_large, container, false);

        ButterKnife.bind(this, view);

        initViews(view);

        return view;
    }

    private void initViews(View rootView) {


        String leftBtnTxt = this.leftButtonTextResId instanceof String ? (String) this.leftButtonTextResId : getString((Integer) this.leftButtonTextResId);
        if (!TextUtils.isEmpty(leftBtnTxt)) {
            leftBtn.setText(leftBtnTxt);
        } else {
            rootView.findViewById(R.id.btn_container).setVisibility(View.GONE);
        }

        String rightBtnTxt = this.rightButtonTextResId instanceof String ? (String) this.rightButtonTextResId : getString((Integer) this.rightButtonTextResId);
        if (!TextUtils.isEmpty(rightBtnTxt)) {
            rightBtn.setText(rightBtnTxt);
        } else {
            rootView.findViewById(R.id.btn_container).setVisibility(View.GONE);
        }

        String createBtnTxt = this.createButtonTexResId instanceof String ? (String) this.createButtonTexResId : getString((Integer) this.createButtonTexResId);
        if (!TextUtils.isEmpty(createBtnTxt)) {
            createBtn.setText(createBtnTxt);
        } else {
            createBtn.setVisibility(View.GONE);
        }

    }
/*

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getDialog() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        final View rootView = inflater.inflate(R.layout.dialog_edit_large, container, false);

        Button btnAddFlashcard = (Button) rootView.findViewById(R.id.fab_add_new_flashcard);
        btnAddFlashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //our flash card attrs


                EditText ex1 = (EditText) rootView.findViewById(R.id.flashcardEx1);
                String strEx1 = ex1.getText().toString();

                EditText ex2 = (EditText) rootView.findViewById(R.id.flashcardEx2);
                String strEx2 = ex2.getText().toString();

                EditText ex3 = (EditText) rootView.findViewById(R.id.flashcardEx3);
                String strEx3 = ex3.getText().toString();

            }
        });


        return rootView;
    }
*/

    public void init(FlashCard flashcard, Object leftButtonTextResId, Object rightButtonTextResId, Object createButtonTexResId, LargeDialogClickListener listener) {
        this.leftButtonTextResId = leftButtonTextResId;
        this.rightButtonTextResId = rightButtonTextResId;
        this.createButtonTexResId = createButtonTexResId;
        this.listener = listener;
        this.flashCard = flashcard;
    }

    @OnClick(R.id.btn_dialog_create)
    public void onCreateFlashcard() {
        fillFlashcard();
        if (listener != null) {
            listener.onRightButtonClick(flashCard);
        }
    }

    private void fillFlashcard() {
        if (flashCard == null)
            flashCard = new FlashCard();

        String txt = wordEditTxt.getText().toString();
        if (!TextUtils.isEmpty(txt)) {
            flashCard.setTitle(txt);
        } else {
            //// TODO: 4/24/2016 AD text input layout error
        }

        txt = translateEditTxt.getText().toString();
        if (!TextUtils.isEmpty(txt)) {
            flashCard.setTranslate(txt);
        } else {
            //// TODO: 4/24/2016 AD text input layout error
        }

        txt = pronunciationEditTxt.getText().toString();
        if (!TextUtils.isEmpty(txt)) {
            flashCard.setPronunciation(txt);
        }

        txt = synonymEditTxt.getText().toString();
        if (!TextUtils.isEmpty(txt)) {
            flashCard.setSynonym(txt);
        }
        //TODO list of example should be added here

    }

    @OnClick(R.id.btn_dialog_left)
    public void leftBtnClick() {
        if (listener != null)
            listener.onLeftButtonClick();
    }

    @OnClick(R.id.btn_dialog_right)
    public void rightBtnClick() {
        if (listener != null) {
            fillFlashcard();
            listener.onRightButtonClick(flashCard);
        }
    }

}