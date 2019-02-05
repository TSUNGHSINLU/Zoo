package com.ivan.animal.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Ivan on 2019/2/5.
 */

public class UrlUtils {

    public static void openBrowserURL(Context context, String URL) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(URL));
        try {
            context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
