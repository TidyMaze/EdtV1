package com.example.edtv1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class DialogModifSuppr extends DialogFragment {

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Que voulez-vous faire ?")
               .setPositiveButton("voir", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   System.out.println("clic sur voir");
                	   Selectionable callingActivity = (Selectionable)getActivity();
                	   callingActivity.onUserSelectValue(Selectionable.VOIR);
                   }
               })
               .setNegativeButton("supprimer", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   System.out.println("clic sur supprimer");
                	   Selectionable callingActivity = (Selectionable)getActivity();
                	   callingActivity.onUserSelectValue(Selectionable.SUPPRIMER);
                   }
               });
        return builder.create();
    }
    
    
}