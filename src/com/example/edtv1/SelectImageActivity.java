package com.example.edtv1;

import gestionBD.DAO.ImagesBDD;
import gestionBD.bean.ImageBean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class SelectImageActivity extends Activity implements Selectionable,
		Importable {

	private static final int REQUEST_IMAGE = 100;
	private static final int SELECT_PICTURE = 1;
	final int REQUIRED_SIZE = 200;

	protected int idObj;
	GridView grid;
	private Uri dst;

	private void buildList() {
		grid = ((GridView) findViewById(R.id.gridView1));
		ImagesBDD imagesBDD = new ImagesBDD(getBaseContext());
		imagesBDD.open();

		ArrayList<ImageBean> listImagesStockees = new ArrayList<ImageBean>();
		listImagesStockees = imagesBDD.getAllImages();

		imagesBDD.close();

		grid.setAdapter(new ListImageBeanAdapter(listImagesStockees, this));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_image);

		this.buildList();

		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("idImage", ((ImageBean) parent
						.getItemAtPosition(position)).getId());
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		grid.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				idObj = ((ImageBean) parent.getItemAtPosition(position))
						.getId();
				DialogModifSuppr dial = new DialogModifSuppr();
				dial.show(getFragmentManager(), "dial");
				return true;
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
			DialogGalerieApn dial = new DialogGalerieApn();
			dial.show(getFragmentManager(), "dialogue");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public byte[] bitmapToByteArray(Bitmap image) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.PNG, 100, out);
		return out.toByteArray();
	}

	@Override
	public void onUserSelectValue(int value) {

		if (value == Importable.APN) {
			System.out.println("dans apn");
			try {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				dst = setImageUri();
				intent.putExtra(MediaStore.EXTRA_OUTPUT, dst);
				startActivityForResult(intent, REQUEST_IMAGE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (value == Importable.GALERIE) {
			System.out.println("dans galerie");
			Intent i = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(i, SELECT_PICTURE);
		} else if (value == Selectionable.VOIR) {
			System.out.println("dans voir");
		} else if (value == Selectionable.SUPPRIMER) {
			System.out.println("dans supprimer");
			try {
				ImagesBDD imagebdd = new ImagesBDD(this);
				imagebdd.open();
				imagebdd.removeImageWithID(idObj);
				imagebdd.close();
				this.buildList();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println(e.getClass().getName());
				Toast.makeText(getApplicationContext(),
						"Image utilisée par une activité", Toast.LENGTH_SHORT)
						.show();
			}

		} else {
			System.out.println("aucune selection");
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Uri uriImage = null;
		Bitmap image = null;
		if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK) {
			uriImage = dst;
			
			image = ImageHandler.decodeFile(uriImage.getPath(), REQUIRED_SIZE);
		} else if (requestCode == SELECT_PICTURE
				&& resultCode == Activity.RESULT_OK) {
			uriImage = data.getData();
			image = ImageHandler.decodeFile(
					Utilities.getAbsolutePath(this, uriImage), REQUIRED_SIZE);

		} else {
			/* aucune image valide */
			return;
		}

		System.out.println("uriTmp" + uriImage);

		System.out.println("apres decode, image : " + image);

		try {
			ImagesBDD bddImages = new ImagesBDD(getBaseContext());
			bddImages.open();
			ImageBean imageBeanTmp = new ImageBean();
			imageBeanTmp.setImagePath("");
			long rowID = (int) bddImages.insertImage(imageBeanTmp);

			File fichier = ImageHandler.ajouterImage(image,
					Long.toString(rowID));
			imageBeanTmp.setImagePath(fichier.getAbsolutePath());
			bddImages.updateImage((int) rowID, imageBeanTmp);
			bddImages.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Uri setImageUri() throws IOException {
		return Uri.fromFile(File.createTempFile("edt", ".jpg",getExternalCacheDir()));
	}

	protected void onResume() {
		super.onResume();
		this.buildList();
	}

}
