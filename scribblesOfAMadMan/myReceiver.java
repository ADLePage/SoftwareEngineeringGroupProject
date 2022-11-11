@@ -4,6 +4,8 @@ import android.app.Notification;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.media.MediaPlayer;
        import android.net.Uri;

        import androidx.core.app.NotificationCompat;
        import androidx.core.app.NotificationManagerCompat;
@ -16,10 +18,20 @@ import androidx.core.app.NotificationManagerCompat;
//A majority of code is here thanks to them.
public class MyReceiver extends BroadcastReceiver {

    public static MediaPlayer alarmSounder;
    //Alarm clock sound used.
    //Sound used: https://freesound.org/people/joedeshon/sounds/78562/
    //Creative license: https://creativecommons.org/licenses/by/4.0/

    //Source: http://www.java2s.com/example/java-api/android/app/notificationchannel/setsound-2-0.html
    //Format for Uri creation and setting sound for channel. Needed to be able to set the sound
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Uri alarmSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.alarm_sound);
        alarmSounder = MediaPlayer.create(context,alarmSound);
        alarmSounder.start();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Alarm System")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("BAADS stress relief Alarm Manager")
        @ -28,6 +40,7 @@ public class MyReceiver extends BroadcastReceiver {
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH);


            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());
        }