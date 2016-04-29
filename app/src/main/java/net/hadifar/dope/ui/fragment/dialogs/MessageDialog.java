package net.hadifar.dope.ui.fragment.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import net.hadifar.dope.R;
import net.hadifar.dope.ui.listeners.DialogButtonsClickListener;

/**
 * Created by Amir on 4/11/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */
public class MessageDialog extends BaseDialog {

    private Object titleRes;
    private Integer iconRes;
    private Object descriptionRes;
    private Object leftButtonTextResId;
    private Object rightButtonTextResId;
    private boolean showLoadingProgress;

    private DialogButtonsClickListener listener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getDialog() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        View rootView = inflater.inflate(R.layout.dialog_message, container, false);

        initViews(rootView);
        return rootView;
    }


    private void initViews(View root) {


        String title = titleRes instanceof String ? (String) titleRes : getString((Integer) titleRes);
        ((TextView) root.findViewById(R.id.text_dialog_title)).setText(title);

        TextView icon = (TextView) root.findViewById(R.id.icon_dialog_icon);
        if (iconRes != null)
            icon.setText(iconRes);
        else {
            icon.setVisibility(View.GONE);
        }

        if (descriptionRes != null) {
            String description = descriptionRes instanceof String ? (String) descriptionRes : getString((Integer) descriptionRes);
            TextView desc = (TextView) root.findViewById(R.id.text_dialog_description);
            desc.setVisibility(View.VISIBLE);
            desc.setText(description);
        } else {
            root.findViewById(R.id.text_dialog_description).setVisibility(View.GONE);
        }
        // Config right button
        final Button rightButton = (Button) root.findViewById(R.id.btn_dialog_right);
        if (rightButtonTextResId != null) {
            String rightButtonText = rightButtonTextResId instanceof String ? (String) rightButtonTextResId : getString((Integer) rightButtonTextResId);
            rightButton.setText(rightButtonText);
            rightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onRightButtonClick();
                    dismiss();
                }
            });
        } else {
            rightButton.setVisibility(View.GONE);
        }

        // Config left button
        final Button leftButton = (Button) root.findViewById(R.id.btn_dialog_left);
        if (leftButtonTextResId != null) {
            String leftButtonText = leftButtonTextResId instanceof String ? (String) leftButtonTextResId : getString((Integer) leftButtonTextResId);
            leftButton.setText(leftButtonText);
            leftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onLeftButtonClick();
                    dismiss();
                }
            });
        } else {
            leftButton.setVisibility(View.GONE);
        }

        if (showLoadingProgress) {
//            ProgressView progressView = (ProgressView) root.findViewById(R.id.pvc_loading);
//            progressView.setVisibility(View.VISIBLE);
//            progressView.start();
        }


    }

    public void init(Object title, Integer iconRes, Object descriptionRes, Object leftButtonTextResId, Object rightButtonTextResId, boolean showLoadingProgress, final DialogButtonsClickListener listener) {
        this.titleRes = title;
        this.iconRes = iconRes;
        this.descriptionRes = descriptionRes;
        this.leftButtonTextResId = leftButtonTextResId;
        this.rightButtonTextResId = rightButtonTextResId;
        this.showLoadingProgress = showLoadingProgress;
        this.listener = listener;
    }
}