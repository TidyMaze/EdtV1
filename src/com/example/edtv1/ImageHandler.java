package com.example.edtv1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class ImageHandler {

	public static Bitmap decodeSampledBitmapFromPath(String path, int reqWidth, int reqHeight) {

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(path, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
			int reqHeight) {

		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}

	public static Bitmap decodeFile(String path, int size) {
		// Decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		System.out.println("chemin :" + path);
		BitmapFactory.decodeFile(path, o);
		

		// Find the correct scale value. It should be the power of 2.
		int scale = 1;
		while (o.outWidth / scale / 2 >= size && o.outHeight / scale / 2 >= size)
			scale *= 2;

		// Decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		return BitmapFactory.decodeFile(path, o2);
	}

	public static File ajouterImage(Bitmap raw, String name) throws IOException {

		String file_path = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES).getAbsolutePath()
				+ "/edt";
		File dir = new File(file_path);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File nomedia = new File(dir, ".nomedia");
		if (!nomedia.exists()) {
			System.out.println("nomedia n'existe pas !");
			nomedia.createNewFile();
		}

		File file = new File(dir, name + ".png");
		FileOutputStream fOut = new FileOutputStream(file);
		
		raw.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		fOut.flush();
		fOut.close();

		raw.recycle();

		return file;
	}

}
