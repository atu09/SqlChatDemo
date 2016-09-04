package alm.com.sqlchatdemo.Services;

/**
 * Created by Atirek on 3/22/2016.
 */

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.Random;

import alm.com.sqlchatdemo.R;

public class GcmMessageHandler extends GcmListenerService {

    public static int MESSAGE_NOTIFICATION_ID;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");

        createNotification(from, message);
    }

    // Creates notification based on title and body received
    private void createNotification(String title, String body) {

        Random random = new Random();
        MESSAGE_NOTIFICATION_ID = random.nextInt();

        Context context = getBaseContext();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title)
                .setContentText(body);

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
    }

}
