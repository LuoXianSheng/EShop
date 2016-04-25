package com.newer.eshop.myview;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.newer.eshop.R;

/**
 * Created by Mr_LUO on 2016/3/16.
 */
public class MyProDialog extends AlertDialog.Builder {

    public MyProDialog(Context context, String title) {
        super(context);
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_item, null);
        setView(v);
        setTitle(title);
        setCancelable(false);
    }

    public MyProDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
}
