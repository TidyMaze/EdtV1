package com.example.edtv1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

public class DialogQuitter extends DialogFragment {

	private Activity a;

	public void setActivity(Activity a) {
		this.a = a;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		final EditText et = new EditText(getActivity());
		et.setInputType(InputType.TYPE_CLASS_TEXT);
		et.setHint("Mot De Passe");

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("Vérification :")
		.setView(et)

		.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				System.out.println("clic sur quitter");
				Selectionable callingActivity = (Selectionable)getActivity();
				callingActivity.onUserSelectValue(Selectionable.QUITTER);
				dismiss();
				if (et.getText().toString().equals("admin")) {
					if (a != null) a.finish();
				}
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

	public interface EditNameDialogListener {
		void onFinishEditDialog(String inputText);
	}

}