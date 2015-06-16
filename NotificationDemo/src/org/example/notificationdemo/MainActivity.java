package org.example.notificationdemo;

import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private NotificationManager mNotificationManager;
	private int notificationID = 1500;
	private int numMessages = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	      Button startBtn = (Button) findViewById(R.id.start);
	      startBtn.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View view) {
	            displayNotification();
	         }
	      });

	      Button cancelBtn = (Button) findViewById(R.id.cancel);
	      cancelBtn.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View view) {
	            cancelNotification();
	         }
	      });
	   
	      Button updateBtn = (Button) findViewById(R.id.update);
	      updateBtn.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View view) {
	            updateNotification();
	         }
	      });
	   }
	   protected void displayNotification() {
	      Log.i("Start", "notification");

	      /* Invoking the default notification service */
	      Notification.Builder  mBuilder = new Notification.Builder(this);	
	      mBuilder.setSmallIcon(R.drawable.women).setTicker("show  details").setWhen(0);
	      mBuilder.setAutoCancel(true).setContentTitle("New Message");
	      mBuilder.setStyle(new Notification.BigTextStyle().bigText("Dounloding RBS Data... \nWhat ever what happenn there is just secreat ok doky wow....."));
	  //    mBuilder.setContentText("You've received new message. HELLO WORLD!!!!").build();
	     // mBuilder.setTicker("Dounloding RBS Data.....");
	   //   .setStyle(new Notification.BigTextStyle().bigText(longText)) 
	  //    mBuilder.setPriority(Notification.PRIORITY_HIGH);
	    
	      /* Increase notification number every time a new notification arrives */
	      mBuilder.setNumber(++numMessages);
	    
	      
	      /* Creates an explicit intent for an Activity in your app */
	      Intent resultIntent = new Intent(this, NotificationView.class);

	      TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
	      stackBuilder.addParentStack(NotificationView.class);

	      /* Adds the Intent that starts the Activity to the top of the stack */
	      stackBuilder.addNextIntent(resultIntent);
	      PendingIntent resultPendingIntent =
	         stackBuilder.getPendingIntent(
	            0,
	            PendingIntent.FLAG_UPDATE_CURRENT
	         );

	      mBuilder.setContentIntent(resultPendingIntent);

	      mNotificationManager =
	      (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

	      /* notificationID allows you to update the notification later on. */
	      mNotificationManager.notify(0, mBuilder.build());
	   }

	   protected void cancelNotification() {
	      Log.i("Cancel", "notification");
	      mNotificationManager.cancel(notificationID);
	   }

	   protected void updateNotification() {
	      Log.i("Update", "notification");

	      /* Invoking the default notification service */
	      NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(this);	

	      mBuilder.setContentTitle("UPDATE NOTIFICATION");
	      mBuilder.setContentText("You've got updated message. \nAND CHECK IF THE CRAP APPEARS THERE???");
	      mBuilder.setTicker("Updated Message Alert!");
	      mBuilder.setSmallIcon(R.drawable.women);

	     /* Increase notification number every time a new notification arrives */
	      mBuilder.setNumber(++numMessages);
	      
	      /* Creates an explicit intent for an Activity in your app */
	      Intent resultIntent = new Intent(this, NotificationView.class);

	      TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
	      stackBuilder.addParentStack(NotificationView.class);

	      /* Adds the Intent that starts the Activity to the top of the stack */
	      stackBuilder.addNextIntent(resultIntent);
	      PendingIntent resultPendingIntent =
	         stackBuilder.getPendingIntent(
	            0,
	            PendingIntent.FLAG_UPDATE_CURRENT
	         );

	      mBuilder.setContentIntent(resultPendingIntent);

	      mNotificationManager =
	      (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

	      /* Update the existing notification using same notification ID */
	      mNotificationManager.notify(notificationID, mBuilder.build());
	   }

}
