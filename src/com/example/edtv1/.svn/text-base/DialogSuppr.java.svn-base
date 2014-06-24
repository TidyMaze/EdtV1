package com.example.edtv1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class DialogSuppr extends DialogFragment {

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Que voulez-vous faire ?")

               .setPositiveButton("supprimer", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   System.out.println("clic sur supprimer");
                	   Selectionable callingActivity = (Selectionable)getActivity();
                	   callingActivity.onUserSelectValue(Selectionable.SUPPRIMER);
                   }
               })
               
               .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   System.out.println("clic sur annuler");
                	   Selectionable callingActivity = (Selectionable)getActivity();
                	   callingActivity.onUserSelectValue(Selectionable.ANNULER);
                   }
               });
        return builder.create();
    }
    
    
}