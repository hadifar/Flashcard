package net.hadifar.dope.ui.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.hadifar.dope.R;

/**
 * Created by Amir on 4/18/2016 AD
 * Project : Flashcard
 * GitHub  : @AmirHadifar
 * Twitter : @AmirHadifar
 */

public class Toaster {

    public static void toast(Context context, String message, int duration) {
        View layout = LayoutInflater.from(context).inflate(R.layout.view_toaster, null);
        TextView text = (TextView) layout.findViewById(R.id.text_message);
        text.setText(message);

        Toast toast = new Toast(context);
        toast.setView(layout);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(duration);
        toast.show();
    }

    public static void toast(Context context, int messageResId, int duration) {
        toast(context, context.getString(messageResId), duration);
    }

    public static void toast(Context context, String message) {
        toast(context, message, Toast.LENGTH_SHORT);
    }

    public static void toast(Context context, int messageResId) {
        toast(context, context.getString(messageResId), Toast.LENGTH_SHORT);
    }
}

