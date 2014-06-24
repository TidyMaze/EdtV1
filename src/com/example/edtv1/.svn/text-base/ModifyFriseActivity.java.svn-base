package com.example.edtv1;

import gestionBD.DAO.ActivitesBDD;
import gestionBD.DAO.CompteBDD;
import gestionBD.DAO.FrisesBDD;
import gestionBD.DAO.SeancesBDD;
import gestionBD.bean.ActiviteBean;
import gestionBD.bean.CompteBean;
import gestionBD.bean.FriseBean;
import gestionBD.bean.SeanceBean;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsware.cwac.tlv.TouchListView;

public class ModifyFriseActivity extends Activity implements Selectionable {

	private int idFrise;
	private FriseBean frise;
	private ArrayList<ActiviteBean> listeActivites;
	private ArrayList<String> arrayListlisteNomsActivites;
	private int idCompte;
	private boolean editer = false;
	protected int idObj;
	protected int posiObj;
	EditText nameFrise;
	protected TouchListView list;
	protected IconicAdapter adapter;


	private void buildList() {
		adapter = new IconicAdapter();
		list.setAdapter(adapter);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_frise);

		nameFrise = ((EditText) findViewById(R.id.NameEdit));

		arrayListlisteNomsActivites = new ArrayList<String>();
		listeActivites = new ArrayList<ActiviteBean>();

		list = (TouchListView) findViewById(R.id.listeSeances);

		list.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				posiObj = position;
				DialogSuppr dial = new DialogSuppr();
				dial.show(getFragmentManager(), "dial");
				return true;
			}

		});

		list.setDropListener(onDrop);
		list.setRemoveListener(onRemove);
		list.setDragListener(onDrag);

		idFrise = this.getIntent().getIntExtra("idFrise", -1);
		if (idFrise != -1) {
			editer = true;
		}
		;

		System.out.println(idFrise);

		if (editer) {

			FrisesBDD bddFrises = new FrisesBDD(this);
			SeancesBDD bddSeances = new SeancesBDD(this);
			ActivitesBDD bddActivites = new ActivitesBDD(this);

			bddFrises.open();
			frise = bddFrises.getFriseWithId(idFrise);
			bddFrises.close();

			bddSeances.open();
			ArrayList<SeanceBean> seances = bddSeances
					.getSeancesWithIdFrise(idFrise);
			bddSeances.close();

			bddActivites.open();

			for (SeanceBean s : seances) {

				ActiviteBean actiTmp = bddActivites.getActiviteWithId(s.getIdActivite());
				listeActivites.add(actiTmp);
				arrayListlisteNomsActivites.add(actiTmp.getName());
			}
			bddActivites.close();

			CompteBDD bddComptes = new CompteBDD(this);
			bddComptes.open();
			CompteBean compte = bddComptes.getCompteWithId(frise.getIdCompte());
			bddComptes.close();

			nameFrise.setText(frise.getName());

			System.out.println("IDFRISE : " + idFrise + " compte : " + compte);

			((Button) findViewById(R.id.bouton_frise_compte)).setText(compte.getPrenom()
					+ compte.getNom());

		}

		this.buildList();

		Button bouton_compte = (Button) findViewById(R.id.bouton_frise_compte);
		bouton_compte.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(getBaseContext(),
						SelectAccountActivity.class), 2);

			}
		});

		Button bouton_ajouterSeance = (Button) findViewById(R.id.button_ajouter_seance);

		bouton_ajouterSeance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(getBaseContext(),
						SelectActivityActivity.class), 1);
			}
		});

		Button bouton_valider = (Button) findViewById(R.id.bouton_valider_frise);
		bouton_valider.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Utilities.isEmpty(nameFrise)) {
					Toast.makeText(getApplicationContext(),
							"Veuillez remplir tout les champs.",
							Toast.LENGTH_SHORT).show();
				} else {
					FrisesBDD bddFrise = new FrisesBDD(getBaseContext());
					bddFrise.open();

					FriseBean frise;
					if (editer) {
						frise = bddFrise.getFriseWithId(idFrise);

					} else {
						frise = new FriseBean();
						frise.setIdCompte(idCompte);
					}

					frise.setName(((EditText) findViewById(R.id.NameEdit))
							.getText().toString());

					if (editer) {
						bddFrise.updateFrise(idFrise, frise);
					} else {
						idFrise = (int) bddFrise.insertFrise(frise);
					}

					bddFrise.close();

					SeancesBDD bddSeance = new SeancesBDD(getBaseContext());
					bddSeance.open();

					// supprimer toutes les séances en lien avec cette frise
					// pour éviter les doublons lors de l'enregistrement des
					// séances
					bddSeance.removeSeanceWithIdFrise(idFrise);

					
					ActivitesBDD bddActivites = new ActivitesBDD(getBaseContext());
					bddActivites.open();
					
					for (int i = 0; i < listeActivites.size(); i++) {
						ActiviteBean a = listeActivites.get(i);

						SeanceBean seanceTmp = new SeanceBean();
						seanceTmp.setIdFrise(idFrise);
						seanceTmp.setIdActivite(a.getId());
						seanceTmp.setOrdre(i);

						bddSeance.insertSeance(seanceTmp);
						
						ActiviteBean actiTMP = bddActivites.getActiviteWithId(a.getId());
						actiTMP.setNbUtil(actiTMP.getNbUtil() + 1);
						
						bddActivites.updateActivite(a.getId(), actiTMP);
					}

					bddSeance.close();
					bddActivites.close();
					
					finish();
				}
			}
		});

	}

	private TouchListView.DropListener onDrop = new TouchListView.DropListener() {
		@Override
		public void drop(int from, int to) {
			String item = adapter.getItem(from);
			ActiviteBean itemActi = listeActivites.get(from);

			listeActivites.remove(from);
			arrayListlisteNomsActivites.remove(from);

			buildList();

			listeActivites.add(to, itemActi);
			arrayListlisteNomsActivites.add(to, item);

			buildList();
		}
	};

	private TouchListView.RemoveListener onRemove = new TouchListView.RemoveListener() {
		@Override
		public void remove(int which) {
			listeActivites.remove(which);
			arrayListlisteNomsActivites.remove(which);

			buildList();
		}
	};

	private TouchListView.DragListener onDrag = new TouchListView.DragListener() {

		@Override
		public void drag(int from, int to) {
			System.out.println("debut drag de " + from + " vers " + to);
		}
	};


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_selection, menu);
		return false;

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {

				ActivitesBDD bddActivites = new ActivitesBDD(this);
				bddActivites.open();

				ActiviteBean acti = bddActivites.getActiviteWithId(data
						.getIntExtra("idActivite", -1));

				bddActivites.close();
				this.listeActivites.add(acti);
				this.arrayListlisteNomsActivites.add(acti.getName());

				this.buildList();
			}
		} else if (requestCode == 2) {
			if (resultCode == RESULT_OK) {
				CompteBDD bddComptes = new CompteBDD(this);
				bddComptes.open();
				CompteBean compte = bddComptes.getCompteWithId(data
						.getIntExtra("idCompte", -1));
				System.out.println(compte);
				bddComptes.close();

				this.idCompte = compte.getId();

				((Button) findViewById(R.id.bouton_frise_compte))
						.setText(compte.getPrenom() + compte.getNom());
			}
		}
	}

	@Override
	public void onUserSelectValue(int value) {
		if (value == Selectionable.ANNULER) {
			System.out.println("dans annuler");
		} else if (value == Selectionable.SUPPRIMER) {
			System.out.println("dans supprimer");
			listeActivites.remove(posiObj);
			arrayListlisteNomsActivites.remove(posiObj);

			this.buildList();
		} else {
			System.out.println("aucune selection");
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.buildList();
	}

	class IconicAdapter extends ArrayAdapter<String> {
		IconicAdapter() {
			super(ModifyFriseActivity.this, R.layout.row2, arrayListlisteNomsActivites);
		}
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.row2, parent, false);
			}
			TextView label = (TextView) row.findViewById(R.id.label);
			label.setText(arrayListlisteNomsActivites.get(position));
			return (row);
		}
	}
}
