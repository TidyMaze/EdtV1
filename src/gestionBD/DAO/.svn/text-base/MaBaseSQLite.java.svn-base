package gestionBD.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MaBaseSQLite extends SQLiteOpenHelper {
	private static final int VERSION_BDD = 22;
	private static final String NOM_BDD = "base.bd";
	
	private static final String TABLE_ACTIVITES = "table_activites";
	private static final String COL_ACTIVITES_ID = "ID";
	private static final String COL_ACTIVITES_NAME = "NAME";
	private static final String COL_ACTIVITES_IDIMAGE = "IDIMAGE";
	private static final String COL_ACTIVITES_NBUTIL = "NBUTIL";


	private static final String TABLE_TAGS = "table_tags";
	private static final String COL_TAGS_ID = "ID";
	private static final String COL_TAGS_NAME = "NAME";	

	private static final String TABLE_FRISES = "table_frises";
	private static final String COL_FRISES_ID = "ID";
	private static final String COL_FRISES_NAME = "NAME";
	private static final String COL_FRISES_IDCOMPTE = "IDCOMPTE";

	private static final String TABLE_COMPTES = "table_comptes";
	private static final String COL_COMPTES_ID = "ID";
	private static final String COL_COMPTES_NOM = "NOM";
	private static final String COL_COMPTES_PRENOM = "PRENOM";
	private static final String COL_COMPTES_NAISSANCE = "NAISSANCE";
	private static final String COL_COMPTES_BIP = "BIP";
	private static final String COL_COMPTES_AFFICHERTEXTE = "AFFICHERTEXTE";
	private static final String COL_COMPTES_IDIMAGE = "IDIMAGE";
	
	private static final String TABLE_ASSOCIATIONS = "table_associations";
	private static final String COL_ASSOCIATIONS_IDACTIVITES = "IDACTIVITES";
	private static final String COL_ASSOCIATIONS_IDTAG = "IDTAG";

	private static final String TABLE_SEANCES = "table_seances";
	private static final String COL_SEANCES_IDFRISE = "IDFRISE";
	private static final String COL_SEANCES_IDACTIVITE = "IDACTIVITE";
	private static final String COL_SEANCES_ORDRE = "ORDRE";
	
	private static final String TABLE_IMAGES = "table_images";
	private static final String COL_IMAGES_ID = "ID";
	private static final String COL_IMAGES_IMAGEPATH = "IMAGEPATH";

	private static final String CREATE_TABLE_ACTIVITES =
			"CREATE TABLE " + TABLE_ACTIVITES + " ("
			+ COL_ACTIVITES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ COL_ACTIVITES_NAME + " VARCHAR(16) NOT NULL, "
			+ COL_ACTIVITES_IDIMAGE + " INTEGER NOT NULL, "
			+ COL_ACTIVITES_NBUTIL + " INTEGER NOT NULL DEFAULT 0, "
			+ "FOREIGN KEY (" + COL_ACTIVITES_IDIMAGE + ") REFERENCES " + TABLE_IMAGES + "(" + COL_IMAGES_ID + ") ON DELETE RESTRICT);";

	private static final String CREATE_TABLE_TAGS = 
			"CREATE TABLE " + TABLE_TAGS + " ("
			+ COL_TAGS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ COL_TAGS_NAME	+ " VARCHAR(16) NOT NULL);";

	private static final String CREATE_TABLE_FRISES = "CREATE TABLE " + TABLE_FRISES + " ("
			+ COL_FRISES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ COL_FRISES_NAME + " VARCHAR(16) NOT NULL, "
			+ COL_FRISES_IDCOMPTE + " INTEGER,"
			+ "FOREIGN KEY (" + COL_FRISES_IDCOMPTE + ") REFERENCES " + TABLE_COMPTES + "(" + COL_COMPTES_ID + ") ON DELETE CASCADE);";

	private static final String CREATE_TABLE_COMPTES = 
			"CREATE TABLE " + TABLE_COMPTES + " ("
			+ COL_COMPTES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_COMPTES_NOM + " VARCHAR(16) NOT NULL, "
			+ COL_COMPTES_PRENOM + " VARCHAR(16) NOT NULL, "
			+ COL_COMPTES_NAISSANCE + " VARCHAR(10), "
			+ COL_COMPTES_BIP + " STRING NOT NULL DEFAULT \"true\", "
			+ COL_COMPTES_AFFICHERTEXTE	+ " STRING NOT NULL DEFAULT \"true\", "
			+ COL_COMPTES_IDIMAGE + " INTEGER, "
			+ "FOREIGN KEY (" + COL_COMPTES_IDIMAGE + ") REFERENCES " + TABLE_IMAGES + "(" + COL_IMAGES_ID + ") ON DELETE RESTRICT);";

	private static final String CREATE_TABLE_ASSOCIATIONS =
			"CREATE TABLE " + TABLE_ASSOCIATIONS + " ("
			+ COL_ASSOCIATIONS_IDACTIVITES + " INTEGER NOT NULL, "
			+ COL_ASSOCIATIONS_IDTAG + " INTEGER NOT NULL, "
			+ "FOREIGN KEY (" + COL_ASSOCIATIONS_IDACTIVITES + ") REFERENCES " + TABLE_ACTIVITES + "(" + COL_ACTIVITES_ID + ") ON DELETE CASCADE,"
			+ "FOREIGN KEY (" + COL_ASSOCIATIONS_IDTAG + ") REFERENCES " + TABLE_TAGS + "(" + COL_TAGS_ID + ") ON DELETE CASCADE,"
			+ "PRIMARY KEY (" + COL_ASSOCIATIONS_IDACTIVITES + "," + COL_ASSOCIATIONS_IDTAG + "));";

	private static final String CREATE_TABLE_SEANCES =
			"CREATE TABLE " + TABLE_SEANCES + " ("
			+ COL_SEANCES_IDFRISE + " INTEGER NOT NULL, "
			+ COL_SEANCES_IDACTIVITE + " INTEGER NOT NULL, "
			+ COL_SEANCES_ORDRE + " INTEGER NOT NULL, "
			+ "FOREIGN KEY (" + COL_SEANCES_IDFRISE + ") REFERENCES " + TABLE_FRISES + "(" + COL_FRISES_ID + ") ON DELETE CASCADE,"
			+ "FOREIGN KEY (" + COL_SEANCES_IDACTIVITE + ") REFERENCES " + TABLE_ACTIVITES + "(" + COL_ACTIVITES_ID + ") ON DELETE RESTRICT,"
			+ "PRIMARY KEY (" + COL_SEANCES_IDFRISE + "," + COL_SEANCES_IDACTIVITE + "," + COL_SEANCES_ORDRE + "));";

	private static final String CREATE_TABLE_IMAGES = 
			"CREATE TABLE " + TABLE_IMAGES + " ("
			+ COL_IMAGES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ COL_IMAGES_IMAGEPATH	+ " VARCHAR(2083) NOT NULL);";
	
	public MaBaseSQLite(Context context, CursorFactory factory) {
		super(context, NOM_BDD, factory, VERSION_BDD);
	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
	    super.onOpen(db);
	    db.execSQL("PRAGMA foreign_keys=ON;");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_ACTIVITES);
		db.execSQL(CREATE_TABLE_TAGS);
		db.execSQL(CREATE_TABLE_FRISES);
		db.execSQL(CREATE_TABLE_COMPTES);
		db.execSQL(CREATE_TABLE_SEANCES);
		db.execSQL(CREATE_TABLE_ASSOCIATIONS);
		db.execSQL(CREATE_TABLE_IMAGES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSOCIATIONS + ";");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEANCES + ";");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRISES + ";");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES + ";");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAGS + ";");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPTES + ";");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITES + ";");
		
		onCreate(db);
	}
}
