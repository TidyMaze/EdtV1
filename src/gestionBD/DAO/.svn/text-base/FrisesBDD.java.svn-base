package gestionBD.DAO;

import gestionBD.bean.FriseBean;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FrisesBDD {
	
	private static final String TABLE_FRISES = "table_frises";
	private static final String COL_ID = "ID";
	private static final int NUM_COL_ID = 0;
	private static final String COL_NAME = "NAME";
	private static final int NUM_COL_NAME = 1;
	private static final String COL_IDCOMPTE = "IDCOMPTE";
	private static final int NUM_COL_IDCOMPTE = 2;
	
	private SQLiteDatabase bdd;
	
	private MaBaseSQLite maBaseSQLite;
	
	public FrisesBDD(Context context){
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

	public long insertFrise(FriseBean frise) {
		ContentValues values = new ContentValues();
		values.put(COL_NAME, frise.getName());
		values.put(COL_IDCOMPTE, frise.getIdCompte());
		return bdd.insert(TABLE_FRISES, null, values);
	}
	
	public long updateFrise(int id, FriseBean frise) {
		ContentValues values = new ContentValues();
		values.put(COL_NAME, frise.getName());
		values.put(COL_IDCOMPTE, frise.getIdCompte());
		
		return bdd.update(TABLE_FRISES, values, COL_ID + " = " + id, null);
	}
	
	public int removeFriseWithID(int id) {
		return bdd.delete(TABLE_FRISES, COL_ID + " = " + id, null);
	}
	
	public ArrayList<FriseBean> getAllFrises(){
		Cursor c = bdd.query(TABLE_FRISES, new String[] { COL_ID, COL_NAME, COL_IDCOMPTE }, null, null, null, null, COL_NAME);
		ArrayList<FriseBean> list = new ArrayList<FriseBean>();
		
		while(c.moveToNext()){
			FriseBean tmp = new FriseBean();
			tmp.setId(c.getInt(NUM_COL_ID));
			tmp.setName(c.getString(NUM_COL_NAME));
			tmp.setIdCompte(c.getInt(NUM_COL_IDCOMPTE));
			list.add(tmp);
		}
		
		c.close();
		
		return list;
	}
	
	public FriseBean getFriseWithId(int id){
		Cursor c = bdd.query(TABLE_FRISES, new String[] { COL_ID, COL_NAME, COL_IDCOMPTE },
				COL_ID + " = " + id, null, null, null, null);
		return cursorToFrise(c);
	}
	
	public ArrayList<FriseBean> getFrisesWithIdCompte(int idCompte){
		Cursor c = bdd.query(TABLE_FRISES, new String[] { COL_ID, COL_NAME, COL_IDCOMPTE }, COL_IDCOMPTE + " = " + idCompte, null, null, null, COL_NAME);
		ArrayList<FriseBean> list = new ArrayList<FriseBean>();
		
		while(c.moveToNext()){
			FriseBean tmp = new FriseBean();
			tmp.setId(c.getInt(NUM_COL_ID));
			tmp.setName(c.getString(NUM_COL_NAME));
			tmp.setIdCompte(c.getInt(NUM_COL_IDCOMPTE));
			list.add(tmp);
		}
		
		c.close();
		
		return list;
	}

	private FriseBean cursorToFrise(Cursor c) {
		if (c.getCount() == 0)
			return null;

		c.moveToFirst();

		FriseBean frise = new FriseBean();
		frise.setId(c.getInt(NUM_COL_ID));
		frise.setName(c.getString(NUM_COL_NAME));
		frise.setIdCompte(c.getInt(NUM_COL_IDCOMPTE));

		c.close();

		return frise;
	}
}
