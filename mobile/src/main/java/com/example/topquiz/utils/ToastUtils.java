package com.example.topquiz.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtils {

    public static void showColoredToast(Context context, String message, int backgroundColor) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        View view = toast.getView();

        if (view != null) {
            view.setBackgroundColor(backgroundColor);
            TextView text = view.findViewById(android.R.id.message);
            if (text != null) {
                text.setTextColor(Color.WHITE);
            }
        }

        toast.show();
    }
}
