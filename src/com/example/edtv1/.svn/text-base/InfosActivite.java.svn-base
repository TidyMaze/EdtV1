package com.example.edtv1;

import gestionBD.DAO.ActivitesBDD;
import gestionBD.DAO.ImagesBDD;
import gestionBD.bean.ActiviteBean;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class InfosActivite extends Activity {

	private int id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_infos_activite);
		
		id = this.getIntent().getIntExtra("idActivite", -1);
		this.buildActivity();
	}

	public void buildActivity(){
		ActivitesBDD actibdd = new ActivitesBDD(this);
		actibdd.open();
		ActiviteBean acti = actibdd.getActiviteWithId(id);
		actibdd.close();
		ImageView im = (ImageView) findViewById(R.id.imageActivite);
		
		int idImage = acti.getIdImage();
		System.out.println("id image : " + idImage);
		
		if(idImage != -1){
			ImagesBDD imagesBDD = new ImagesBDD(getBaseContext());
			imagesBDD.open();
			
			im.setImageURI(Utilities.stringToUri(imagesBDD.getImageWithId(idImage).getImagePath()));
			imagesBDD.close();
		}
		
		TextView text = (TextView) findViewById(R.id.nomActivite);
		text.setText(acti.getName());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.infos_activite, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.modify:
	        	Intent intent = new Intent(this, ModifyActiviteActivity.class);
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
		this.buildActivity();
	}
	
}
