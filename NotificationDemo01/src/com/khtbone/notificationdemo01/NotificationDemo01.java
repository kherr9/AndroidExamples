package com.khtbone.notificationdemo01;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class NotificationDemo01 extends Activity implements Button.OnClickListener {
	
	private static final int HELLO_ID = 1;
	private static UpdateNote _holder = null;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ((Button) findViewById(R.id.btnToast)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnSimpleNotification)).setOnClickListener(this);
        
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		mNotificationManager.cancel(HELLO_ID);
        
		if (_holder != null)
			_holder.Cancel();
		
    }

	public void onClick(View v) {
		
		if(v == findViewById(R.id.btnToast)){
			Context context = getApplicationContext();
			CharSequence text = "Hello Toast";
			int duration = Toast.LENGTH_SHORT;
			
			Toast t = Toast.makeText(context, text, duration);
			t.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
			t.show();
		}else if (v == findViewById(R.id.btnSimpleNotification)){
		
			//	get a ref to manager
			String ns = Context.NOTIFICATION_SERVICE;
			NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
			
			
			//	instantiate the Notification
			int icon = R.drawable.icon;
			CharSequence tickerText = "Hello";
			long when = System.currentTimeMillis();
			
			Notification notification = new Notification(icon,tickerText,when);
			
			//	Define the expanded text and Intent
			Context context = getApplicationContext();
			CharSequence contentTitle = "My Notificaiton";
			CharSequence contentText = "This is my text";
			Intent notificationIntent = new Intent(this,NotificationDemo01.class);
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
			
			notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
			
			//	optional stuff
			notification.defaults = Notification.DEFAULT_SOUND;
			
			long[] vibrate = {0,100,200,300};
			notification.vibrate = vibrate;
			
			//	pass the notification to the NotificationManager
			mNotificationManager.notify(HELLO_ID, notification);
			
		}

	}
	
	class UpdateNote implements Runnable{
		
		private Notification _n;
		private Context _c;
		private CharSequence _t;
		private CharSequence _txt;
		private PendingIntent _i;
		private boolean _b;
		private int _counter;
		
		public UpdateNote(Notification n, Context c, CharSequence title, CharSequence text, PendingIntent intent){
			_n = n;
			_c = c;
			_t = title;
			_txt = text;
			_i = intent;
			_b = true;
			_counter = 1;
		}
		
		public void Cancel(){
			_b = false;
		}
		
		public void run(){
			while(_b){
				try{
					Thread.sleep(5000);
					
				}catch(Exception e){}

				_n.setLatestEventInfo(_c, _t, _txt + String.valueOf(_counter), _i);
				
				_counter++;
			}
		}
		
		
	}

}