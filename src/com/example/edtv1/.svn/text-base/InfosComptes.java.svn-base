package com.example.edtv1;

import gestionBD.DAO.CompteBDD;
import gestionBD.DAO.ImagesBDD;
import gestionBD.bean.CompteBean;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class InfosComptes extends Activity {
	
	private int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_infos_comptes);
		
		id = this.getIntent().getIntExtra("idCompte", -1);
		this.buildAccount();
	}

    private void buildAccount() {
    	CompteBDD cmptbdd = new CompteBDD(this);
		cmptbdd.open();
		CompteBean cmpt = cmptbdd.getCompteWithId(id);
		cmptbdd.close();
		ImageView im = (ImageView) findViewById(R.id.imageCompte);
		
		int idImage = cmpt.getIdImage();
		System.out.println("id image : " + idImage);
		
		if(idImage != -1){
			ImagesBDD imagesBDD = new ImagesBDD(getBaseContext());
			imagesBDD.open();
			
			im.setImageURI(Utilities.stringToUri(imagesBDD.getImageWithId(idImage).getImagePath()));
			imagesBDD.close();
		}
		
		TextView nom = (TextView) findViewById(R.id.nomCompte);
		nom.setText(cmpt.getNom());
		
		TextView prenom = (TextView) findViewById(R.id.prenomCompte);
		prenom.setText(cmpt.getPrenom());
		
		TextView naissance = (TextView) findViewById(R.id.naissanceCompte);
		naissance.setText(cmpt.getNaissance());
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.infos_activite, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.modify:
	        	Intent intent = new Intent(this, ModifyAccountActivity.class);
	        	intent.putExtra("id", id);
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		this.buildAccount();
	}
}
