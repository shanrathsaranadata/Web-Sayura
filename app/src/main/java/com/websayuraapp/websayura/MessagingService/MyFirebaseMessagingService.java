package com.websayuraapp.websayura.MessagingService;

import android.widget.Toast;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public MyFirebaseMessagingService() {
        super();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);

//        Toast.makeText(this, "From: " + remoteMessage.getFrom(), Toast.LENGTH_SHORT).show();
//
//        if (remoteMessage.getData().size() > 0) {
//
//            Toast.makeText(this, "Message data payload: " + remoteMessage.getData(), Toast.LENGTH_SHORT).show();
//        }
//
//        if (remoteMessage.getNotification() != null) {
//            Toast.makeText(this, "Message Notification Body: " + remoteMessage.getNotification().getBody(), Toast.LENGTH_SHORT).show();
//        }
    }
}
