package com.example.edtv1;

public interface Selectionable {
	public static final int VOIR = 1;
	public static final int SUPPRIMER = 2;
	public static final int ANNULER = 3;
	public static final int QUITTER = 4;
	
    public void onUserSelectValue(int value);
}
