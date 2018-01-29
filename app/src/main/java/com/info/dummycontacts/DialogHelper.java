package com.info.dummycontacts;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;

/**
 * Created by dbagv_000 on 1/18/2018.
 */
public class DialogHelper {
    public static void showAlert(Context context, String msg) {
        if (context != null) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.ErrorDialog);
            builder.setTitle(context.getResources().getString(R.string.app_name))
                    .setMessage(msg)
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create();

            if (!((Activity) context).isFinishing())
                builder.show();
        }
    }
}
