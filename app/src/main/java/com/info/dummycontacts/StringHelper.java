package com.info.dummycontacts;

import android.view.View;
import android.widget.TextView;

/**
 * Created by advanz101 on 26/10/17.
 */

public class StringHelper {
    public static boolean isNotEmpty(String string) {
        return string != null && string.length() > 0;
    }

    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0||string.trim().length()==0||string.trim().equals("null");
    }

    public static String isEmpty(String string, String defaultValue){
        if(isEmpty(string)){
            return defaultValue;
        }else {
            return string;
        }
    }

    public static void setText(TextView textView, String textString) {
        if (StringHelper.isEmpty(textString)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setText(textString);
        }
    }


    public static String joinStrings(String seperator, String oldString, String newString) {
        String strings[]={oldString,newString};
        int length = strings.length;
        if (length == 0) {
            return "";
        }
        // Allocate space for length * firstStringLength;
        // If strings[0] is null, then its length is defined as 4, since that's the
        // length of "null".
        final int firstStringLength = strings[0] != null ? strings[0].length() : 4;
        StringBuilder buf = new StringBuilder(length * firstStringLength)
                .append(strings[0]);
        for (int i = 1; i < length; i++) {
            buf.append(seperator).append(strings[i]);
        }
        return buf.toString();
    }
}
