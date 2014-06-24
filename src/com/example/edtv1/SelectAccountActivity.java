package com.example.edtv1;

import gestionBD.DAO.CompteBDD;
import gestionBD.bean.CompteBean;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SelectAccountActivity extends Activity implements Selectionable {

	private int idObj;
	private ListView list;
	private ListImageTextViewAdapter aa;

	private void buildList() {
		CompteBDD bddCompte = new CompteBDD(this);
		bddCompte.open();
		aa = new ListImageTextViewAdapter(bddCompte.getAllComptes(),this);
		bddCompte.close();
		list.setAdapter(aa);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comptes);

		list = (ListView) findViewById(R.id.listComptes);
		this.buildList();

		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("idCompte", ((CompteBean) parent
						.getItemAtPosition(position)).getId());
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				idObj = ((CompteBean) parent.getItemAtPosition(position))
						.getId();
				DialogModifSuppr dial = new DialogModifSuppr();
				dial.show(getFragmentManager(), "dial");
				return true;
			}
		});

		final EditText textsearch = (EditText) findViewById(R.id.search);
		textsearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String text = textsearch.getText().toString()
						.toLowerCase(Locale.getDefault());
				aa.getFilter().filter(text);
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
		getMenuInflater().inflate(R.menu.menu_selection, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.ajout:
			Intent intent = new Intent(this, ModifyAccountActivity.class);
			startActivityForResult(intent, 1);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onUserSelectValue(int value) {
		if (value == Selectionable.VOIR) {
			System.out.println("dans voir");
			Intent intent = new Intent(this, InfosComptes.class);
			intent.putExtra("idCompte", this.idObj);
			startActivity(intent);
		} else if (value == Selectionable.SUPPRIMER) {
			try {
				System.out.println("dans supprimer");
				CompteBDD comptebdd = new CompteBDD(this);
				comptebdd.open();
				comptebdd.removeCompteWithID(idObj);
				comptebdd.close();
				this.buildList();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println(e.getClass().getName());
				Toast.makeText(getApplicationContext(),
						"Compte utilisé par une frise", Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			System.out.println("aucune selection");
		}
	}

	protected void onResume() {
		super.onResume();
		this.buildList();
	}

}