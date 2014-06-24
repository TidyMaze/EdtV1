package com.example.edtv1;

import gestionBD.DAO.ActivitesBDD;
import gestionBD.DAO.CompteBDD;
import gestionBD.DAO.FrisesBDD;
import gestionBD.DAO.ImagesBDD;
import gestionBD.DAO.SeancesBDD;
import gestionBD.bean.ActiviteBean;
import gestionBD.bean.CompteBean;
import gestionBD.bean.FriseBean;
import gestionBD.bean.SeanceBean;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class FriseAffichageActivity extends Activity implements Selectionable/*
																			 * implements
																			 * OnClickListener
																			 */{
	private int idCourant;
	private ImageButton bouton_valider;
	private ImageButton bouton_annuler;
	private LinearLayout friseLayout;
	private Minuteur minuteur;
	
	


	public static final int SEANCEWIDTH = 300;
	public static final int SEANCEHEIGHT = 300;
	private static final int IDVALIDER=1;
	private static final int IDANNULER=2;
	private static final int BUTTONWIDTH = 100;
	private static final int BUTTONHEIGHT = 100;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_frise_affichage);

		
		
		bouton_valider = new ImageButton(getBaseContext());
		bouton_annuler = new ImageButton(getBaseContext());

		bouton_valider.setId(IDVALIDER);
		bouton_annuler.setId(IDANNULER);

		bouton_valider.setImageResource(R.drawable.ok);
		bouton_valider.setBackgroundResource(0);
		bouton_valider.setPadding(0, 0, 0, 0);
		bouton_valider.setScaleType(ScaleType.FIT_CENTER);
		bouton_valider.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				activiteSuivante();
			}
		});

		bouton_annuler.setImageResource(R.drawable.ko);
		bouton_annuler.setBackgroundResource(0);
		bouton_annuler.setPadding(0, 0, 0, 0);
		bouton_annuler.setScaleType(ScaleType.FIT_CENTER);
		bouton_annuler.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				activitePrecedente();
			}
		});

		int idFrise = this.getIntent().getIntExtra("idFrise", -1);
		System.out.println("ID FRISE : " + idFrise);

		int orientation = getResources().getConfiguration().orientation;

		if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
			HorizontalScrollView scrollview = (HorizontalScrollView) this.findViewById(R.id.ScrollFriseLand);
			friseLayout = (LinearLayout) scrollview.findViewById(R.id.layoutFriseLand);
		} else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			ScrollView scrollview = (ScrollView) this.findViewById(R.id.ScrollFrise);
			friseLayout = (LinearLayout) scrollview.findViewById(R.id.layoutFrise);
		}

		// FrisesBDD bddFrises = new FrisesBDD(this);
		SeancesBDD bddSeances = new SeancesBDD(this);
		ActivitesBDD bddActivites = new ActivitesBDD(this);
		ImagesBDD bddImages = new ImagesBDD(this);

		bddSeances.open();
		ArrayList<SeanceBean> seances = bddSeances.getSeancesWithIdFrise(idFrise);
		bddSeances.close();

		ArrayList<ActiviteBean> listeActivites = new ArrayList<ActiviteBean>();

		bddActivites.open();
		for (SeanceBean s : seances) {
			listeActivites.add(bddActivites.getActiviteWithId(s.getIdActivite()));
		}
		bddActivites.close();

		bddImages.open();

		FrisesBDD bddFrises = new FrisesBDD(this);
		bddFrises.open();
		FriseBean frise = bddFrises.getFriseWithId(idFrise);
		bddFrises.close();

		CompteBDD bddComptes = new CompteBDD(this);
		bddComptes.open();
		CompteBean compte = bddComptes.getCompteWithId(frise.getIdCompte());
		bddComptes.close();

		for (ActiviteBean a : listeActivites) {
			View vueElem = LayoutInflater.from(getApplicationContext())
					.inflate(R.layout.elem, null);

			ImageView im = (ImageView) vueElem.findViewById(R.id.icon);

			if (a.getIdImage() != -1) {
				im.setImageURI(Utilities.stringToUri(bddImages.getImageWithId(a.getIdImage())
						.getImagePath()));
			}

			TextView label = (TextView) vueElem.findViewById(R.id.label);
			if (compte.isAfficherTexte()) {
				label.setText(a.getName());
			} else {
				label.setVisibility(View.INVISIBLE);
			}

			vueElem.setLayoutParams(new LayoutParams(SEANCEWIDTH, SEANCEHEIGHT));

			friseLayout.addView(vueElem);
		}
		bddImages.close();

		if (friseLayout.getChildCount() <= 0) {
			return;
		}

		idCourant = 0;
		setSeanceCourante(idCourant);

		minuteur = new Minuteur(FriseAffichageActivity.this, this,
				(ProgressWheel) findViewById(R.id.progressWheel));

		Bippeur bippeur = new Bippeur();

		if (compte.isBip()) {

			minuteur.deleteObserver(bippeur);
			minuteur.addObserver(bippeur);

		}

	}

	protected void activitePrecedente() {
		System.out.println("idcourant = " + idCourant);

		if (idCourant > 0) {
			if (idCourant < friseLayout.getChildCount()) {
				setSeanceNormale(idCourant);
			}
			setSeanceCourante(idCourant - 1);

			if (idCourant > 1) {
				setSeancePrecedente(idCourant - 2);
			}

			idCourant--;
		}
	}

	protected void activiteSuivante() {
		System.out.println("idcourant = " + idCourant);

		if (idCourant < friseLayout.getChildCount()) {
			if (idCourant > 0) {
				setSeanceNormale(idCourant - 1);
			}

			if (idCourant >= 0) {
				setSeancePrecedente(idCourant);
			}

			if (idCourant < friseLayout.getChildCount() - 1) {
				setSeanceCourante(idCourant + 1);
			}

			idCourant++;
		}
	}

	private void setSeanceNormale(int id) {
		System.out.println("seance normale : " + id);
		View vue = friseLayout.getChildAt(id);

		RelativeLayout rel_layout_elem = (RelativeLayout) vue.findViewById(R.id.rel_layout_elem);

		vue.getLayoutParams().height = SEANCEHEIGHT;
		vue.getLayoutParams().width = SEANCEWIDTH;

		System.out.println(bouton_annuler.getId());
		System.out.println(bouton_valider.getId());
		rel_layout_elem.removeView(vue.findViewById(bouton_annuler.getId()));
		rel_layout_elem.removeView(vue.findViewById(bouton_valider.getId()));
	}

	private void setSeancePrecedente(int id) {
		System.out.println("seance precedente : " + id);
		setSeanceNormale(id);

		View vue = friseLayout.getChildAt(id);

		RelativeLayout rel_layout_elem_prec = (RelativeLayout) vue.findViewById(R.id.rel_layout_elem);

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				BUTTONWIDTH,
				BUTTONHEIGHT);
		lp.addRule(RelativeLayout.ALIGN_RIGHT, R.id.icon);
		lp.addRule(RelativeLayout.ALIGN_TOP, R.id.icon);
		rel_layout_elem_prec.addView(bouton_annuler, lp);
	}

	private void setSeanceCourante(int id) {
		System.out.println("seance courante : " + id);
		setSeanceNormale(id);

		View vue = friseLayout.getChildAt(id);

		vue.getLayoutParams().height = (int) (SEANCEHEIGHT*1.5);
		vue.getLayoutParams().width = (int) (SEANCEWIDTH*1.5);

		RelativeLayout rel_layout_elem = (RelativeLayout) vue.findViewById(R.id.rel_layout_elem);

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				BUTTONWIDTH,
				BUTTONHEIGHT);
		lp.addRule(RelativeLayout.ALIGN_RIGHT, R.id.icon);
		lp.addRule(RelativeLayout.ALIGN_TOP, R.id.icon);
		rel_layout_elem.addView(bouton_valider, lp);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ECLAIR
				&& (keyCode == KeyEvent.KEYCODE_BACK) && event.getRepeatCount() == 0) {
			onBackPressed();
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		DialogQuitter dial = new DialogQuitter();
		dial.setActivity(this);
		dial.show(getFragmentManager(), "dial");
	}

	@Override
	public void onUserSelectValue(int value) {
		if (value == Selectionable.QUITTER) {
			System.out.println("dans quitter");
			String password = this.getIntent().getStringExtra("password");
			System.out.println("Le mdp est : " + password);
		} else if (value == Selectionable.ANNULER) {
			return;
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		if (minuteur != null) {
			minuteur.detruireMinuteur();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		if (minuteur != null) {
			minuteur.detruireMinuteur();
		}
	}

	
}