package com.example.madine;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationService extends Service {

    NotificationManagerCompat notificationManagerCompat;
    Notification notification;
    private DatabaseReference database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = FirebaseDatabase.getInstance().getReference().child("user");
        getData();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // WorkRequest workGetData = new OneTimeWorkRequest.Builder(WorkerData.class).build();
        // WorkManager.getInstance(getApplicationContext()).enqueue(workGetData);

        Log.d("NOTIFICATION_SERVICE", "Start Running");

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class WorkerData extends Worker {
        public WorkerData(@NonNull Context context,
                                      @NonNull WorkerParameters params)
        {
            super(context, params);
        }
        @NonNull
        @Override
        public Result doWork() {

            // getData();
            return Result.success();
        }
    }

    private void getData() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long userCount = dataSnapshot.getChildrenCount();
                // Log.d("NOTIFICATION_SERVICE", String.valueOf(userCount));

                if (userCount > 10) {
                    // Jika jumlah pengguna melebihi 10, tampilkan peringatan di sini
                    createNotificationChannel();
                }
                //createNotificationChannel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error jika ada
                Log.e("Notification_Service", "Error Notification Service");
            }
        });
    }


    private void createNotificationChannel() {
        Long timeId = System.currentTimeMillis();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(timeId.toString(), getPackageName(), NotificationManager.IMPORTANCE_HIGH);

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(getApplicationContext(), AdminLogin.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, timeId.toString())
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.logomadine)
                .setContentIntent(pendingIntent)
                .setContentTitle("User more than 10!")
                .setContentText("Please Delete User in Admin");


        notification = builder.build();

        notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(timeId.intValue(), notification);
    }
}
