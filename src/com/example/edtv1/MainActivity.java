package com.example.edtv1;

import gestionBD.DAO.CompteBDD;
import gestionBD.DAO.ImagesBDD;
import gestionBD.bean.CompteBean;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	protected static final int REQUEST_IDFRISE = 0;
	private static final int COMPTEWIDTH = 400;
	private static final int COMPTEHEIGHT = 400;
	private LinearLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		layout = (LinearLayout) findViewById(R.id.layoutMain);
		layout.setGravity(Gravity.CENTER);
		this.buildList();

	}
	
	public void buildList(){
		this.layout.removeAllViews();
		
		CompteBDD bddCmpt = new CompteBDD(this);
		bddCmpt.open();
		
		ArrayList<CompteBean> listeComptes = bddCmpt.getAllComptes();
		bddCmpt.close();
		
		for(final CompteBean c : listeComptes){
			
			LinearLayout zone = new LinearLayout(this);
			LayoutParams paramZone = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			paramZone.setMargins(10, 10, 10, 10);
			zone.setLayoutParams(paramZone);
			zone.setOrientation(LinearLayout.VERTICAL);
			
			ImagesBDD bddImages = new ImagesBDD(this);
			bddImages.open();
		
			ImageButton boutonTmp = new ImageButton(this);
			boutonTmp.setId(c.getId());
			LayoutParams params = new LayoutParams(COMPTEWIDTH, COMPTEHEIGHT);
			boutonTmp.setLayoutParams(params);
			boutonTmp.setScaleType(ScaleType.CENTER_CROP);
			if(c.getIdImage() != -1){
				boutonTmp.setImageURI(Utilities.stringToUri(bddImages.getImageWithId(c.getIdImage()).getImagePath()));
			}
			bddImages.close();
			boutonTmp.setBackgroundResource(R.drawable.button);

			TextView nom = new TextView(this);
			String t = c.getPrenom() +" "+ c.getNom();
			nom.setText(t.toCharArray(), 0, t.length());
			
			nom.setGravity(Gravity.CENTER);
			
			zone.addView(boutonTmp);
			zone.addView(nom);
			this.layout.addView(zone);
			
			boutonTmp.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getBaseContext(),SelectFriseActivity.class);
					intent.putExtra("idCompte", c.getId());
					
					startActivityForResult(intent, REQUEST_IDFRISE);
					System.out.println("clic sur bouton ID : " + v.getId());
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	   			startActivity(new Intent(this, AdminActivity.class));
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.buildList();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == REQUEST_IDFRISE){
			if(resultCode == RESULT_OK){
				int idFrise = data.getIntExtra("idFrise", -1);
				
				if(idFrise != -1){
					Intent i = new Intent (this,FriseAffichageActivity.class);
					i.putExtra("idFrise", idFrise);
					startActivity(i);
				}
			}
		}
	}
}
