package net.hadifar.dope.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import net.hadifar.dope.R;

/**
 * Amir Hadifar on 02/08/2015
 * Cardy
 * Email : Hadifar.amir@gmail.com
 * Twitter : @AmirHadifar
 */

public class IntentHelper {

    public static void sendEmail(Context context) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "hadifar.amir@gmail.com", null));
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    public static void shareOnSocials(Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.share_on_socials));
        context.startActivity(Intent.createChooser(intent, "Share Dope"));
    }
}
