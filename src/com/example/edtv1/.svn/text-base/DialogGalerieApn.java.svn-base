package com.example.edtv1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class DialogGalerieApn extends DialogFragment {

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Prendre une image depuis ...")
               .setPositiveButton("l'appareil photo", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   System.out.println("clic sur apn");
                	   Importable callingActivity = (Importable)getActivity();
                	   callingActivity.onUserSelectValue(Importable.APN);
                   }
               })
               .setNegativeButton("la galerie", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   System.out.println("clic sur galerie");
                	   Importable callingActivity = (Importable)getActivity();
                	   callingActivity.onUserSelectValue(Importable.GALERIE);
                   }
               });
        return builder.create();
    }
    
    
}