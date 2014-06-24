package com.example.edtv1;

import gestionBD.DAO.CompteBDD;
import gestionBD.DAO.ImagesBDD;
import gestionBD.bean.CompteBean;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

public class ModifyAccountActivity extends Activity {

	private ImageView picture;
	private int idImage = -1;
	private int id;
	private boolean editer = false;
	private EditText editnom;
	private EditText editprenom;
	private EditText editdate;
	private Switch editBip;
	private Switch editAfficherTexte;
	private Button validation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_account);

		picture = (ImageView) findViewById(R.id.edit_image_compte);
		editnom = (EditText) findViewById(R.id.edit_nom);
		editprenom = (EditText) findViewById(R.id.edit_prenom);
		editdate = (EditText) findViewById(R.id.edit_date);
		editBip = (Switch) findViewById(R.id.switchBip);
		editAfficherTexte = (Switch) findViewById(R.id.switchAfficherTexte);
		
		TextWatcher tw = new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				checkValues();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		};

		editprenom.addTextChangedListener(tw);
		editnom.addTextChangedListener(tw);
		editdate.addTextChangedListener(tw);
		
		validation = (Button) findViewById(R.id.bouton_validation);
		validation.setVisibility(View.INVISIBLE);

		id = this.getIntent().getIntExtra("id", -1);
		if (id != -1) {
			editer = true;
		}

		if (editer) {
			validation.setVisibility(View.VISIBLE);
			CompteBDD cmptbdd = new CompteBDD(this);
			cmptbdd.open();
			CompteBean cmpt = cmptbdd.getCompteWithId(id);
			editnom.setText(cmpt.getNom());
			editprenom.setText(cmpt.getPrenom());
			editdate.setText(cmpt.getNaissance());
			editBip.setChecked(cmpt.isBip());
			editAfficherTexte.setChecked(cmpt.isAfficherTexte());
			idImage = cmpt.getIdImage();
			cmptbdd.close();
			checkValues();
		}

		Button click = (Button) findViewById(R.id.bouton_image_compte);
		click.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), SelectImageActivity.class);
				startActivityForResult(intent, 1);
			}
		});

		validation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ajout(v);
			}
		});
	}

	protected void checkValues() {
		if(Utilities.isEmpty(editprenom) || Utilities.isEmpty(editnom) || Utilities.isEmpty(editdate) || idImage == -1){
			if(validation.getVisibility() == View.VISIBLE){
				validation.setVisibility(View.INVISIBLE);
			}
		} else {
			if(validation.getVisibility() == View.INVISIBLE){
				validation.setVisibility(View.VISIBLE);
			}
		}
		
		
	}

	public void ajout(View view) {
		if (editer) {
			CompteBDD bddCompte = new CompteBDD(this);
			bddCompte.open();
			CompteBean compte = bddCompte.getCompteWithId(id);
			String nom = editnom.getText().toString();
			String prenom = editprenom.getText().toString();
			String date = editdate.getText().toString();
			boolean bip = editBip.isChecked();
			boolean afficherTexte = editAfficherTexte.isChecked();
			compte.setNom(nom);
			compte.setPrenom(prenom);
			compte.setNaissance(date);
			compte.setIdImage(idImage);
			compte.setBip(bip);
			compte.setAfficherTexte(afficherTexte);

			bddCompte.updateCompte(id, compte);
			bddCompte.close();
			finish();
		} else {
			CompteBean compte = new CompteBean();
			String nom = editnom.getText().toString();
			String prenom = editprenom.getText().toString();
			String date = editdate.getText().toString();
			boolean bip = editBip.isChecked();
			boolean afficherTexte = editAfficherTexte.isChecked();
			compte.setNom(nom);
			compte.setPrenom(prenom);
			compte.setNaissance(date);
			compte.setIdImage(idImage);
			compte.setBip(bip);
			compte.setAfficherTexte(afficherTexte);
			CompteBDD bddCompte = new CompteBDD(this);
			bddCompte.open();
			bddCompte.insertCompte(compte);
			bddCompte.close();
			finish();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				this.idImage = data.getIntExtra("idImage", -1);
				ImagesBDD imagesBDD = new ImagesBDD(getBaseContext());
				imagesBDD.open();
				this.picture.setImageURI(Utilities.stringToUri(imagesBDD.getImageWithId(idImage)
						.getImagePath()));
				imagesBDD.close();
				
				checkValues();
			}
		}
	}
}
