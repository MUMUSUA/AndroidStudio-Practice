package com.bignerdranch.android.mediaplayer;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import static android.content.Context.NOTIFICATION_SERVICE;
import android.support.v4.app.NotificationCompat;

public class FourthFragment extends Fragment  implements View.OnClickListener{


    public FourthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fourth, container, false);
        Button sendNotice = (Button) view.findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_notice:
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                PendingIntent pi = PendingIntent.getActivity(getActivity(), 0, intent, 0);
                NotificationManager manager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(getActivity())
                        .setContentTitle("通知！")
                        .setContentText("这是一条严肃的通知。")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .setContentIntent(pi)
                        //        .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")))
                        //        .setVibrate(new long[]{0, 1000, 1000, 1000})
                        //        .setLights(Color.GREEN, 1000, 1000)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        //        .setStyle(new NotificationCompat.BigTextStyle().bigText("Learn how to build notifications, send and sync data, and use voice actions. Get the official Android IDE and developer tools to build apps for Android."))
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.big_image)))
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .build();
                manager.notify(1, notification);
                break;
            default:
                break;
        }
    }



}
