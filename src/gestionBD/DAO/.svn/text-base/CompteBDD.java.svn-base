package gestionBD.DAO;

import gestionBD.bean.CompteBean;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CompteBDD {
	
	private static final String TABLE_COMPTES = "table_comptes";
	private static final String COL_ID = "ID";
	private static final int NUM_COL_ID = 0;
	private static final String COL_NOM = "NOM";
	private static final int NUM_COL_NOM = 1;
	private static final String COL_PRENOM = "PRENOM";
	private static final int NUM_COL_PRENOM = 2;
	private static final String COL_NAISSANCE = "NAISSANCE";
	private static final int NUM_COL_NAISSANCE = 3;
	private static final String COL_BIP = "BIP";
	private static final int NUM_COL_BIP = 4;
	private static final String COL_AFFICHERTEXTE = "AFFICHERTEXTE";
	private static final int NUM_COL_AFFICHERTEXTE = 5;
	private static final String COL_IDIMAGE = "IDIMAGE";
	private static final int NUM_COL_IDIMAGE = 6;

	
	private SQLiteDatabase bdd;
	
	private MaBaseSQLite maBaseSQLite;
	
	public CompteBDD(Context context){
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

	public long insertCompte(CompteBean compte) {
		ContentValues values = new ContentValues();
		values.put(COL_NOM, compte.getNom());
		values.put(COL_PRENOM, compte.getPrenom());
		values.put(COL_NAISSANCE, compte.getNaissance());
		values.put(COL_BIP, Boolean.toString(compte.isBip()));
		values.put(COL_AFFICHERTEXTE, Boolean.toString(compte.isAfficherTexte()));
		values.put(COL_IDIMAGE, compte.getIdImage());
		return bdd.insert(TABLE_COMPTES, null, values);
	}
	
	public long updateCompte(int id, CompteBean	compte) {
		ContentValues values = new ContentValues();
		values.put(COL_NOM, compte.getNom());
		values.put(COL_PRENOM, compte.getPrenom());
		values.put(COL_NAISSANCE, compte.getNaissance());
		values.put(COL_BIP, Boolean.toString(compte.isBip()));
		values.put(COL_AFFICHERTEXTE, Boolean.toString(compte.isAfficherTexte()));
		values.put(COL_IDIMAGE, compte.getIdImage());
		return bdd.update(TABLE_COMPTES, values, COL_ID + " = " + id, null);
	}
	
	public int removeCompteWithID(int id) {
		return bdd.delete(TABLE_COMPTES, COL_ID + " = " + id, null);
	}
	
	public ArrayList<CompteBean> getAllComptes(){
		Cursor c = bdd.query(TABLE_COMPTES, new String[] { COL_ID, COL_NOM, COL_PRENOM, COL_NAISSANCE, COL_BIP, COL_AFFICHERTEXTE, COL_IDIMAGE }, null, null, null, null, COL_NOM);
		ArrayList<CompteBean> list = new ArrayList<CompteBean>();
		
		while(c.moveToNext()){
			CompteBean tmp = new CompteBean();
			tmp.setId(c.getInt(NUM_COL_ID));
			tmp.setNom(c.getString(NUM_COL_NOM));
			tmp.setPrenom(c.getString(NUM_COL_PRENOM));
			tmp.setNaissance(c.getString(NUM_COL_NAISSANCE));
			tmp.setBip(Boolean.parseBoolean(c.getString(NUM_COL_BIP)));
			tmp.setAfficherTexte(Boolean.parseBoolean(c.getString(NUM_COL_AFFICHERTEXTE)));
			tmp.setIdImage(c.getInt(NUM_COL_IDIMAGE));
			list.add(tmp);
		}
		
		c.close();
		
		return list;
	}
	
	public CompteBean getCompteWithName(String prenom, String nom) {
		Cursor c = bdd.query(TABLE_COMPTES, new String[] { COL_ID, COL_NOM, COL_PRENOM, COL_NAISSANCE, COL_BIP, COL_AFFICHERTEXTE, COL_IDIMAGE, COL_NOM },
				COL_NOM + " = \"" + nom + "\" AND " + COL_PRENOM + " = \"" + prenom + "\"", null, null, null, null);
		return cursorToCompte(c);
	}
	
	public CompteBean getCompteWithId(int id){
		Cursor c = bdd.query(TABLE_COMPTES, new String[] { COL_ID, COL_NOM, COL_PRENOM, COL_NAISSANCE, COL_BIP, COL_AFFICHERTEXTE, COL_IDIMAGE, COL_NOM },
				COL_ID + " = " + id, null, null, null, null);
		return cursorToCompte(c);
	}

	private CompteBean cursorToCompte(Cursor c) {
		if (c.getCount() == 0)
			return null;

		c.moveToFirst();

		CompteBean compte = new CompteBean();
		compte.setId(c.getInt(NUM_COL_ID));
		compte.setNom(c.getString(NUM_COL_NOM));
		compte.setPrenom(c.getString(NUM_COL_PRENOM));
		compte.setNaissance(c.getString(NUM_COL_NAISSANCE));
		compte.setBip(Boolean.parseBoolean(c.getString(NUM_COL_BIP)));
		compte.setAfficherTexte(Boolean.parseBoolean(c.getString(NUM_COL_AFFICHERTEXTE)));
		compte.setIdImage(c.getInt(NUM_COL_IDIMAGE));

		c.close();
		
		return compte;
	}

	
}