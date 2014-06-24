package gestionBD.DAO;

import gestionBD.bean.SeanceBean;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SeancesBDD {
	private static final String TABLE_SEANCES = "table_seances";
	private static final String COL_IDFRISE = "IDFRISE";
	private static final int NUM_COL_IDFRISE = 0;
	private static final String COL_IDACTIVITE = "IDACTIVITE";
	private static final int NUM_COL_IDACTIVITE = 1;
	private static final String COL_ORDRE = "ORDRE";
	private static final int NUM_COL_ORDRE = 2;
	
	private SQLiteDatabase bdd;
	
	private MaBaseSQLite maBaseSQLite;
	
	public SeancesBDD(Context context){
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

	public long insertSeance(SeanceBean seance) {
		ContentValues values = new ContentValues();
		values.put(COL_IDFRISE, seance.getIdFrise());
		values.put(COL_IDACTIVITE, seance.getIdActivite());
		values.put(COL_ORDRE, seance.getOrdre());
		return bdd.insert(TABLE_SEANCES, null, values);
	}
	
	public long updateSeance(int idFrise, int idActivite, int ordre, SeanceBean	seance) {
		ContentValues values = new ContentValues();
		values.put(COL_IDFRISE, seance.getIdFrise());
		values.put(COL_IDACTIVITE, seance.getIdActivite());
		values.put(COL_ORDRE, seance.getOrdre());
		return bdd.update(TABLE_SEANCES, values, COL_IDFRISE + " = " + idFrise + " AND " + COL_IDACTIVITE + " = " + idActivite + " AND " + COL_ORDRE + " = " + ordre, null);
	}
	
	public int removeSeanceWithIdFrise(int idFrise){
		return bdd.delete(TABLE_SEANCES, COL_IDFRISE + " = " + idFrise, null);
	}
	
	public int removeSeanceWithID(int idFrise, int idActivite, int ordre) {
		return bdd.delete(TABLE_SEANCES, COL_IDFRISE + " = " + idFrise + " AND " + COL_IDACTIVITE + " = " + idActivite + " AND " + COL_ORDRE + " = " + ordre, null);
	}
	
	public ArrayList<SeanceBean> getAllSeances(){
		Cursor c = bdd.query(TABLE_SEANCES, new String[] { COL_IDFRISE, COL_IDACTIVITE, COL_ORDRE}, null, null, null, null, COL_ORDRE);
		ArrayList<SeanceBean> list = new ArrayList<SeanceBean>();
		
		while(c.moveToNext()){
			SeanceBean tmp = new SeanceBean();
			tmp.setIdFrise(c.getInt(NUM_COL_IDFRISE));
			tmp.setIdActivite(c.getInt(NUM_COL_IDACTIVITE));
			tmp.setOrdre(c.getInt(NUM_COL_ORDRE));
			list.add(tmp);
		}
		
		c.close();
		
		return list;
	}
	
	public SeanceBean getSeanceWithId(int idFrise, int idActivite, int ordre){
		Cursor c = bdd.query(TABLE_SEANCES, new String[] { COL_IDFRISE, COL_IDACTIVITE, COL_ORDRE},COL_IDFRISE + " = " + idFrise + " AND " + COL_IDACTIVITE + " = " + idActivite + " AND " + COL_ORDRE + " = " + ordre, null, null, null, null);
		return cursorToSeance(c);
	}
	
	public ArrayList<SeanceBean> getSeancesWithIdFrise(int idFrise){
		Cursor c = bdd.query(TABLE_SEANCES, new String[] { COL_IDFRISE, COL_IDACTIVITE, COL_ORDRE}, COL_IDFRISE + " = " + idFrise, null, null, null, COL_ORDRE);
		ArrayList<SeanceBean> list = new ArrayList<SeanceBean>();
		
		while(c.moveToNext()){
			SeanceBean tmp = new SeanceBean();
			tmp.setIdFrise(c.getInt(NUM_COL_IDFRISE));
			tmp.setIdActivite(c.getInt(NUM_COL_IDACTIVITE));
			tmp.setOrdre(c.getInt(NUM_COL_ORDRE));
			list.add(tmp);
		}
		
		c.close();
		
		return list;
	}

	private SeanceBean cursorToSeance(Cursor c) {
		if (c.getCount() == 0)
			return null;

		c.moveToFirst();

		SeanceBean seance = new SeanceBean();
		seance.setIdFrise(c.getInt(NUM_COL_IDFRISE));
		seance.setIdActivite(c.getInt(NUM_COL_IDACTIVITE));
		seance.setOrdre(c.getInt(NUM_COL_ORDRE));
		c.close();

		return seance;
	}
}
