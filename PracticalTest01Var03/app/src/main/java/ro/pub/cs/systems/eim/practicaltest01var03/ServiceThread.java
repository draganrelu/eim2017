package ro.pub.cs.systems.eim.practicaltest01var03;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by relu on 30.03.2018.
 */

public class ServiceThread extends Thread {

    Context context;
    private String student;
    private String group;

    public ServiceThread(Context context, String student, String group) {
        this.context = context;
        this.student = student;
        this.group = group;
        Log.d(Constants.TAG, "Created service thread with " + student + " " + group);
    }

    private void sleep() {
        try {
            Thread.sleep(Constants.SLEEP_TIME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Log.d(Constants.TAG, "Service thread started");
        Random rand = new Random();

        while(true) {
            sendMessage(rand.nextInt(2));
            sleep();
        }
    }

    private void sendMessage(int msgType) {
        Intent intent = new Intent();

        switch(msgType) {
            case Constants.MSG_STUDENT:
                Log.d(Constants.TAG, "Sending MSG_STUDENT");
                intent.setAction(Constants.ACTION_STRING);
                intent.putExtra(Constants.DATA, "Student name: " + student);
                break;
            case Constants.MSG_GROUP:
                Log.d(Constants.TAG, "Sending MSG_GROUP");
                intent.setAction(Constants.ACTION_STRING);
                intent.putExtra(Constants.DATA, "Student group: " + group);
                break;
            default:
                Log.d(Constants.TAG, "Unknown msg type");
        }

        context.sendBroadcast(intent);
    }



}
