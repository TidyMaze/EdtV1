package gestionBD.DAO;


import gestionBD.bean.ImageBean;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ImagesBDD {

	private static final String TABLE_IMAGES = "table_images";
	private static final String COL_ID = "ID";
	private static final int NUM_COL_ID = 0;	
	private static final String COL_IMAGEPATH = "IMAGEPATH";
	private static final int NUM_COL_IMAGEPATH = 1;

	private SQLiteDatabase bdd;

	private MaBaseSQLite maBaseSQLite;

	public ImagesBDD(Context context) {
		maBaseSQLite = new MaBaseSQLite(context, null);
	}

	public void open() {
		bdd = maBaseSQLite.getWritableDatabase();
	}

	public void close() {
		bdd.close();
	}

	public SQLiteDatabase getBDD() {
		return bdd;
	}

	public long insertImage(ImageBean image) {
		ContentValues values = new ContentValues();
		values.put(COL_IMAGEPATH, image.getImagePath());
		return bdd.insert(TABLE_IMAGES, null, values);
	}

	public long updateImage(int id, ImageBean image) {
		ContentValues values = new ContentValues();
		values.put(COL_IMAGEPATH, image.getImagePath());
		return bdd.update(TABLE_IMAGES, values, COL_ID + " = " + id, null);
	}

	public int removeImageWithID(int id) {
		return bdd.delete(TABLE_IMAGES, COL_ID + " = " + id, null);
	}

	public ArrayList<ImageBean> getAllImages() {
		Cursor c = bdd.query(TABLE_IMAGES, new String[] { COL_ID, COL_IMAGEPATH }, null, null,
				null, null, COL_ID);
		ArrayList<ImageBean> list = new ArrayList<ImageBean>();

		while(c.moveToNext()){
			ImageBean a = new ImageBean();
			a.setId(c.getInt(NUM_COL_ID));
			a.setImagePath(c.getString(NUM_COL_IMAGEPATH));
			list.add(a);
		}
		
		c.close();

		return list;
	}
	
	public ImageBean getImageWithId(int id) {
		Cursor c = bdd.query(TABLE_IMAGES, new String[] { COL_ID, COL_IMAGEPATH },
				COL_ID + " = " + id, null, null, null, null);
		return cursorToImage(c);
	}

	private ImageBean cursorToImage(Cursor c) {
		if (c.getCount() == 0)
			return null;

		c.moveToFirst();

		ImageBean image = new ImageBean();
		image.setId(c.getInt(NUM_COL_ID));
		image.setImagePath(c.getString(NUM_COL_IMAGEPATH));

		c.close();

		return image;
	}
	
}
