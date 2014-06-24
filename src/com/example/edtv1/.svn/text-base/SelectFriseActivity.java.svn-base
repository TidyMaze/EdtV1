package com.example.edtv1;

import gestionBD.DAO.FrisesBDD;
import gestionBD.bean.FriseBean;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class SelectFriseActivity extends Activity implements Selectionable {
	
	private int idObj;
	private ListView list;
	private ArrayAdapter<FriseBean> ff;
	
	private void buildList(){
		FrisesBDD bddFrise = new FrisesBDD(this);
		bddFrise.open();
		
		int idCompte = this.getIntent().getIntExtra("idCompte", -1);
		
		ArrayList<FriseBean> frises;
		
		if(idCompte == -1){
			frises = bddFrise.getAllFrises();
		}else {
			frises = bddFrise.getFrisesWithIdCompte(idCompte);
		}
		
		ff = new ArrayAdapter<FriseBean>(this,
				android.R.layout.simple_list_item_1, frises);
		bddFrise.close();

		
		list.setAdapter(ff);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_frise);
		
		list = (ListView) findViewById(R.id.listView);
		this.buildList();
		
		list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {				
				Intent intent = new Intent();
				intent.putExtra("idFrise", ((FriseBean)parent.getItemAtPosition(position)).getId());
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				idObj = ((FriseBean)parent.getItemAtPosition(position)).getId();
				DialogModifSuppr dial = new DialogModifSuppr();
				dial.show(getFragmentManager(), "dial");
				return true;
			}
		});
		
		final EditText textsearch = (EditText) findViewById(R.id.NameEdit);
		textsearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String text = textsearch.getText().toString()
	                    .toLowerCase(Locale.getDefault());
	            ff.getFilter().filter(text);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_selection, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.ajout:
	        	Intent intent = new Intent(this, ModifyFriseActivity.class);
	        	startActivity(intent);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
    public void onUserSelectValue(int value){
    	if(value == Selectionable.VOIR){
    		System.out.println("dans voir");
    		Intent intent = new Intent(this, ModifyFriseActivity.class);
    		intent.putExtra("idFrise", this.idObj);
    		startActivity(intent);
    	} else if (value == Selectionable.SUPPRIMER){
    		System.out.println("dans supprimer");
    		FrisesBDD frisebdd = new FrisesBDD(this);
    		frisebdd.open();
    		frisebdd.removeFriseWithID(idObj);
    		frisebdd.close();
    		
    		this.buildList();
    	} else {
    		System.out.println("aucune selection");
    	}
    }
	
	protected void onResume() {
		super.onResume();
		this.buildList();
	}
}
