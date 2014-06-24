package gestionBD.DAO;

import gestionBD.bean.ActiviteBean;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ActivitesBDD {
	
	private static final String TABLE_ACTIVITES = "table_activites";
	private static final String COL_ID = "ID";
	private static final int NUM_COL_ID = 0;
	private static final String COL_NAME = "NAME";
	private static final int NUM_COL_NAME = 1;
	private static final String COL_IDIMAGE = "IDIMAGE";
	private static final int NUM_COL_IDIMAGE = 2;
	private static final String COL_NBUTIL = "NBUTIL";
	private static final int NUM_COL_NBUTIL = 3;

	private SQLiteDatabase bdd;

	private MaBaseSQLite maBaseSQLite;

	public ActivitesBDD(Context context) {
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

	public long insertActivite(ActiviteBean activite) {
		ContentValues values = new ContentValues();
		values.put(COL_NAME, activite.getName());
		values.put(COL_IDIMAGE, activite.getIdImage());
		values.put(COL_NBUTIL, activite.getNbUtil());
		return bdd.insert(TABLE_ACTIVITES, null, values);
	}

	public long updateActivite(int id, ActiviteBean activite) {
		ContentValues values = new ContentValues();
		values.put(COL_NAME, activite.getName());
		values.put(COL_IDIMAGE, activite.getIdImage());
		values.put(COL_NBUTIL, activite.getNbUtil());
		return bdd.update(TABLE_ACTIVITES, values, COL_ID + " = " + id, null);
	}

	public int removeActiviteWithID(int id) {
		return bdd.delete(TABLE_ACTIVITES, COL_ID + " = " + id, null);
	}

	public ArrayList<ActiviteBean> getAllActivites() {
		Cursor c = bdd.query(TABLE_ACTIVITES, new String[] { COL_ID, COL_NAME, COL_IDIMAGE, COL_NBUTIL }, null,
				null, null, null, COL_NBUTIL + ", " + COL_NAME);
		ArrayList<ActiviteBean> list = new ArrayList<ActiviteBean>();

		while (c.moveToNext()) {
			ActiviteBean tmp = new ActiviteBean();
			tmp.setId(c.getInt(NUM_COL_ID));
			tmp.setName(c.getString(NUM_COL_NAME));
			tmp.setIdImage(c.getInt(NUM_COL_IDIMAGE));
			tmp.setNbUtil(c.getInt(NUM_COL_NBUTIL));
			list.add(tmp);
		}

		c.close();

		return list;
	}	

	public ActiviteBean getActiviteWithName(String name) {
		Cursor c = bdd.query(TABLE_ACTIVITES, new String[] { COL_ID, COL_NAME, COL_IDIMAGE, COL_NBUTIL },
				COL_NAME + " LIKE \"" + name + "\"", null, null, null, null);
		return cursorToActivite(c);
	}

	private ActiviteBean cursorToActivite(Cursor c) {
		if (c.getCount() == 0)
			return null;

		c.moveToFirst();

		ActiviteBean activite = new ActiviteBean();
		activite.setId(c.getInt(NUM_COL_ID));
		activite.setName(c.getString(NUM_COL_NAME));
		activite.setIdImage(c.getInt(NUM_COL_IDIMAGE));
		activite.setNbUtil(c.getInt(NUM_COL_NBUTIL));
		c.close();

		return activite;
	}

	public ActiviteBean getActiviteWithId(int id) {
		Cursor c = bdd.query(TABLE_ACTIVITES, new String[] { COL_ID, COL_NAME, COL_IDIMAGE, COL_NBUTIL },
				COL_ID + " = " + id, null, null, null, null);
		return cursorToActivite(c);
	}
}
