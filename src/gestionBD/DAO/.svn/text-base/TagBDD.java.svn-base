package gestionBD.DAO;

import gestionBD.bean.TagBean;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TagBDD {

	private static final String TABLE_TAGS = "table_tags";
	private static final String COL_TAGS_ID = "ID";
	private static final int NUM_COL_TAGS_ID = 0;
	private static final String COL_TAGS_NAME = "NAME";
	private static final int NUM_COL_TAGS_NAME = 1;

	private SQLiteDatabase bdd;

	private MaBaseSQLite maBaseSQLite;

	public TagBDD(Context context) {
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

	public long insertTag(TagBean tag) {
		ContentValues values = new ContentValues();
		values.put(COL_TAGS_NAME, tag.getName());
		return bdd.insert(TABLE_TAGS, null, values);
	}

	public long updateTag(int id, TagBean tag) {
		ContentValues values = new ContentValues();
		values.put(COL_TAGS_NAME, tag.getName());
		return bdd.update(TABLE_TAGS, values, COL_TAGS_ID + " = " + id, null);
	}

	public int removeTagWithID(int id) {
		return bdd.delete(TABLE_TAGS, COL_TAGS_ID + " = " + id, null);
	}

	public ArrayList<TagBean> getAllTags() {
		Cursor c = bdd.query(TABLE_TAGS, new String[] { COL_TAGS_ID, COL_TAGS_NAME }, null, null,
				null, null, COL_TAGS_NAME);
		ArrayList<TagBean> list = new ArrayList<TagBean>();

		while(c.moveToNext()){
			TagBean a = new TagBean();
			a.setId(c.getInt(NUM_COL_TAGS_ID));
			a.setName(c.getString(NUM_COL_TAGS_NAME));
			list.add(a);
		}
		
		c.close();

		return list;
	}

	public TagBean getTagWithName(String name) {
		Cursor c = bdd.query(TABLE_TAGS, new String[] { COL_TAGS_ID, COL_TAGS_NAME },
				COL_TAGS_NAME + " LIKE \"" + name + "\"", null, null, null, null);
		return cursorToTag(c);
	}

	private TagBean cursorToTag(Cursor c) {
		if (c.getCount() == 0)
			return null;

		c.moveToFirst();

		TagBean tag = new TagBean();
		tag.setId(c.getInt(NUM_COL_TAGS_ID));
		tag.setName(c.getString(NUM_COL_TAGS_NAME));

		c.close();

		return tag;
	}

}
