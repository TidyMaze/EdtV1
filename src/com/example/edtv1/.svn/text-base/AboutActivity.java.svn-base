package com.example.edtv1;

import java.io.File;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}
	
	 public void onClick(View v) {
		 switch(v.getId()) {
			case R.id.item_policy:
				startActivity(new Intent(this, CGUActivity.class));
				break;
			case R.id.send_bug:
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"projetdut2.ps@gmail.com"});
				i.putExtra(Intent.EXTRA_SUBJECT, "Retour de bugs");
				i.putExtra(Intent.EXTRA_TEXT   , "Voici le(s) bug(s) que je rencontre :");
				try {
				    startActivity(Intent.createChooser(i, "Envoyer un mail via ..."));
				} catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(AboutActivity.this, "Il n'y a pas de client mail installé.", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.item_notice:
				File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ "notice_application.pdf");
				Intent target = new Intent(Intent.ACTION_VIEW);
				target.setDataAndType(Uri.fromFile(file),"application/pdf");
				target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

				Intent intent = Intent.createChooser(target, "Open File");
				try {
				    startActivity(intent);
				} catch (ActivityNotFoundException e) {
					Toast.makeText(AboutActivity.this, "Il n'y a pas d'application PDF d'installé.", Toast.LENGTH_SHORT).show();
				}   
		 }
	 }
}


