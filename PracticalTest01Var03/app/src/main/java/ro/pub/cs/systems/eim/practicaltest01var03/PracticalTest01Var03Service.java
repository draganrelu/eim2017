package ro.pub.cs.systems.eim.practicaltest01var03;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class PracticalTest01Var03Service extends Service {

    private final int startMode = Service.START_STICKY;
    private ServiceThread serviceThread;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Constants.TAG, "service onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent,
                              int flags,
                              int startId) {
        Log.d(Constants.TAG, "service onStartCommand()");
        Bundle data = intent.getExtras();

        String student = data.getString(Constants.STUDENT_EDIT_TEXT);
        String group = data.getString(Constants.GROUP_EDIT_TEXT);

        serviceThread = new ServiceThread(this, student, group);
        serviceThread.start();

        return startMode;
    }

    @Override
    public void onDestroy() {
        Log.d(Constants.TAG, "service onDestroy()");
        try {
            serviceThread.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
