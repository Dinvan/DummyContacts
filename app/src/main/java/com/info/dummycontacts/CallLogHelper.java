package com.info.dummycontacts;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.util.Log;

public class CallLogHelper {

	public static Cursor getAllCallLogs(ContentResolver cr,String number) {
		// reading all data in descending order according to DATE
		String strOrder = CallLog.Calls.DATE + " DESC";
		Uri callUri = Uri.parse("content://call_log/calls");
		Cursor curCallLogs = cr.query(callUri, null,CallLog.Calls.NUMBER + " = ? ",new String[]{number}, strOrder);

		return curCallLogs;
	}
}
