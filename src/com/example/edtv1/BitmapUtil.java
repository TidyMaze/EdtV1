package com.example.edtv1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtil {
	public static Bitmap byteArrayImagetoBitmap(byte[] image){
		return BitmapFactory.decodeByteArray(image, 0, image.length);
	}
}
