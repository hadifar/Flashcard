package net.hadifar.dope.utils;

import android.content.ActivityNotFoundException;
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

    public static void voteForAppInBazaar(Context context) {
        try {
            Intent vote = new Intent(Intent.ACTION_EDIT, Uri.parse("bazaar://details?id=" + context.getApplicationInfo().packageName));
            context.startActivity(vote);
        } catch (ActivityNotFoundException e) {
            viewInBrowser(context, "http://cafebazaar.ir/app/" + context.getApplicationInfo().packageName);
        }
    }

    public static void viewInBrowser(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (null != intent.resolveActivity(context.getPackageManager())) {
            context.startActivity(intent);
        }
    }


}
