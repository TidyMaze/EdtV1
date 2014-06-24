package com.example.edtv1;

import java.util.Calendar;
import java.util.Observable;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.TimePicker;

public class Minuteur extends Observable {
	protected static final Object END = null;
	protected boolean running;
	private Runnable r;
	private long dureeMinuteur = 0;
	private long tDebut = 0;
	Thread thread;
		
	public Minuteur(final Context context, final Activity activite, final ProgressWheel timerGraphique){
		timerGraphique.setDrawCount(false);
		timerGraphique.setDelayMillis(50);

		timerGraphique.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				final Calendar c = Calendar.getInstance();
				TimePickerDialog tpd = new TimePickerDialog(context,
						new OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

								dureeMinuteur = getTimeSeconds(hourOfDay, minute, 0);

							}

						}, 0, 0, true);
				tpd.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss(DialogInterface dialog) {
						tDebut = getTimeSeconds(c.get(Calendar.HOUR_OF_DAY),
								c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
						System.out.println("tdebut set : " + tDebut);

						r = new Runnable() {
							public void run() {
								running = true;
								while (running) {
									Calendar c = Calendar.getInstance();
									long currentTime = getTimeSeconds(c.get(Calendar.HOUR_OF_DAY),
											c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
									long finalTime = tDebut + dureeMinuteur;

									double progress100 = ((double) currentTime - tDebut)
											/ ((double) finalTime - tDebut) * 100;


									double progress360 = 360 - (progress100 * 360 / 100);

									timerGraphique.setProgress((int) progress360);

									if (progress100 >= 100){
										System.out.println("minuteur arrivé au bout !!!");
										
										setChanged();
										notifyObservers(Minuteur.END);
										running = false;
									}

									try {
										Thread.sleep(10);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						};

						timerGraphique.resetCount();
						thread = new Thread(r);
						thread.start();
					}
				});
				tpd.setTitle("Durée :");
				tpd.show();
				return true;
			}
		});

		
		
	}
	
	public static long getTimeSeconds(int h, int m, int s) {
		return ((h * 60) + m) * 60 + s;
	}
	
	public void detruireMinuteur(){
		if(running){
			running = false;
		}
	}
}
