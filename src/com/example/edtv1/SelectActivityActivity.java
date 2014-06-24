package com.example.edtv1;

import gestionBD.DAO.ActivitesBDD;
import gestionBD.bean.ActiviteBean;

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

public class SelectActivityActivity extends Activity implements Selectionable {

	private int idObj;
	private ListView list;
	private ListImageTextViewAdapter aa;

	private void buildList() {
		ActivitesBDD bddActi = new ActivitesBDD(this);
		bddActi.open();
		aa = new ListImageTextViewAdapter(bddActi.getAllActivites(), this );
		bddActi.close();
		list.setAdapter(aa);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activites);

		list = (ListView) findViewById(R.id.listView);
		this.buildList();

		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("idActivite", ((ActiviteBean) parent
						.getItemAtPosition(position)).getId());
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				idObj = ((ActiviteBean) parent.getItemAtPosition(position))
						.getId();
				DialogModifSuppr dial = new DialogModifSuppr();
				dial.show(getFragmentManager(), "dial");
				return true;
			}
		});

		final EditText textsearch = (EditText) findViewById(R.id.NameEdit);
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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_selection, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.ajout:
			Intent intent = new Intent(this, ModifyActiviteActivity.class);
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
			Intent intent = new Intent(this, InfosActivite.class);
			intent.putExtra("idActivite", this.idObj);
			startActivity(intent);
		} else if (value == Selectionable.SUPPRIMER) {
			System.out.println("dans supprimer");
			try {
				ActivitesBDD actibdd = new ActivitesBDD(this);
				actibdd.open();
				actibdd.removeActiviteWithID(idObj);
				actibdd.close();
				this.buildList();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println(e.getClass().getName());
				Toast.makeText(getApplicationContext(),
						"Activité utilisée par une frise", Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			System.out.println("aucune selection");
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.buildList();
	}

}