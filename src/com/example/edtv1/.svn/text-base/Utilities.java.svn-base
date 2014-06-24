package com.example.edtv1;

import java.io.File;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.MediaColumns;
import android.widget.EditText;

public class Utilities {
	public static String getAbsolutePath(Activity acti, Uri uri) {
		String[] projection = { MediaColumns.DATA };
		@SuppressWarnings("deprecation")
		Cursor cursor = acti.managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} else
			return null;
	}

	public static Uri stringToUri(String path) {
		return Uri.fromFile(new File(path));
	}

	static boolean isEmpty(EditText etText) {
		return etText.getText().toString().trim().length() == 0;
	}
}
