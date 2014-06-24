package com.example.edtv1;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;

public class AdminActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        // Show the Up button in the action bar.
        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }	
        return super.onOptionsItemSelected(item);
    }
    
    public void onClick(View v) {
		switch(v.getId()) {
		case R.id.boutonCompte:
			startActivity(new Intent(this, SelectAccountActivity.class));
			break;
		case R.id.boutonActivite:
			startActivity(new Intent(this, SelectActivityActivity.class));
			break;
		case R.id.boutonFrise:
			startActivity(new Intent(this, SelectFriseActivity.class));
			break;
		case R.id.boutonParametres:
			startActivity(new Intent(this, AboutActivity.class));
			break;
		}
	}
    
}
