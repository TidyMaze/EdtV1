package com.example.edtv1;

import gestionBD.DAO.ActivitesBDD;
import gestionBD.DAO.ImagesBDD;
import gestionBD.bean.ActiviteBean;
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

public class ModifyActiviteActivity extends Activity {
	private int id;
	private ImageView picture;
	private boolean editer = false;
	private int idImage;
	private EditText editname;
	private Button validation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_activite);

		picture = (ImageView) findViewById(R.id.idIvImage);
		editname = (EditText) findViewById(R.id.edit_name);
		
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
		
		editname.addTextChangedListener(tw);

		validation = (Button) findViewById(R.id.bouton_validation);
		validation.setVisibility(View.INVISIBLE);

		id = this.getIntent().getIntExtra("id", -1);

		if (id != -1) {
			editer = true;
		}

		if (editer) {
			validation.setVisibility(View.VISIBLE);
			ActivitesBDD actibdd = new ActivitesBDD(this);
			actibdd.open();
			ActiviteBean acti = actibdd.getActiviteWithId(id);
			actibdd.close();
			editname.setText(acti.getName());
			idImage = acti.getId();
			checkValues();
		}

		Button click = (Button) findViewById(R.id.bouton_image_activite);
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

	public void ajout(View view) {
		if (editer) {
			ActivitesBDD bddActi = new ActivitesBDD(this);
			bddActi.open();
			ActiviteBean acti = new ActiviteBean();

			String name = editname.getText().toString();
			acti.setName(name);
			acti.setIdImage(idImage);
			bddActi.updateActivite(id, acti);
			bddActi.close();
			finish();
		} else {
			ActiviteBean acti = new ActiviteBean();
			editname = (EditText) findViewById(R.id.edit_name);
			String name = editname.getText().toString();
			acti.setName(name);
			acti.setIdImage(idImage);
			ActivitesBDD bddActi = new ActivitesBDD(this);
			bddActi.open();
			bddActi.insertActivite(acti);
			bddActi.close();
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

	protected void checkValues() {
		if (Utilities.isEmpty(editname) || idImage == -1) {
			if (validation.getVisibility() == View.VISIBLE) {
				validation.setVisibility(View.INVISIBLE);
			}
		} else {
			if (validation.getVisibility() == View.INVISIBLE) {
				validation.setVisibility(View.VISIBLE);
			}
		}

	}

}
