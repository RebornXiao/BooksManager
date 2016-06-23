package com.book.booksmanager.utils;

import android.app.Activity;
import android.content.Context;

import com.book.booksmanager.view.MyToast;


public class ToastUtils {
    public static void show(final Context context, final String msg,
                            final int duaration) {
        if ("main".equalsIgnoreCase(Thread.currentThread().getName())) {
//            Toast.makeText(context, msg, duaration).show();
            MyToast.makeText(context, msg, MyToast.LENGTH_LONG).show();
        } else {
            ((Activity) context).runOnUiThread(new Runnable() {

                @Override
                public void run() {
//                    Toast.makeText(context, msg, duaration).show();
                    MyToast.makeText(context, msg, MyToast.LENGTH_LONG).show();
                }
            });
        }
    }

    public static void show(final Context context, final String msg) {
        if ("main".equalsIgnoreCase(Thread.currentThread().getName())) {
            MyToast.makeText(context, msg, MyToast.LENGTH_SHORT).show();
//            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } else {
            ((Activity) context).runOnUiThread(new Runnable() {

                @Override
                public void run() {
//                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    MyToast.makeText(context, msg, MyToast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
