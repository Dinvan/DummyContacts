package com.info.dummycontacts;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;

/**
 * Created by dbagv_000 on 1/18/2018.
 */
public class DialogHelper {
    public static void showMessage(String Msg, Context con) {
        new AlertDialog.Builder(new ContextThemeWrapper(con, R.style.AppTheme_Dark_Dialog)).setTitle("Alert!").setMessage(Msg)
                .setPositiveButton("OK", null).show();
    }

    public static AlertDialog confirmationDialog(String buttonText, String title, String message, final OnServerResponse<Boolean> onResponse, Context context) {

        AlertDialog alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AppTheme_Dark_Dialog))
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        onResponse.serverResponseReceived(true, true);

                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        onResponse.serverResponseReceived(false, false);

                        dialog.dismiss();
                    }
                })
                .show();
        return alertDialog;
    }
}
