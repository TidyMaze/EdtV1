package com.example.edtv1;

import java.util.Observable;
import java.util.Observer;

import android.media.AudioManager;
import android.media.ToneGenerator;

public class Bippeur implements Observer {

	private static final int NBREPEAT = 3;
	private static final int TIMEWAIT = 500;
	
	@Override
	public void update(Observable observable, Object data) {
		System.out.println("recu update");
		if (observable instanceof Minuteur) {
			System.out.println("instanceof Minuteur");
			if (data == Minuteur.END) {
				System.out.println("message : END");
				
				final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
				
				for(int i=0;i<NBREPEAT;i++){
					tg.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT);
					try {
						Thread.sleep(TIMEWAIT);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("fin start");
			}
		}
	}

}
