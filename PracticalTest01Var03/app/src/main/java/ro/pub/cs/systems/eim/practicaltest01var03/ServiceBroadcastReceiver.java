package ro.pub.cs.systems.eim.practicaltest01var03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by relu on 30.03.2018.
 */

public class ServiceBroadcastReceiver extends BroadcastReceiver {

    public ServiceBroadcastReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String action = intent.getAction();

        if (action.equals(Constants.ACTION_STRING)) {
            String data = (String)extras.get(Constants.DATA);
            Log.d(Constants.TAG, "Receiver received " + data);
        } else {
            Log.d(Constants.TAG, "Receiver received unknown action");
        }

    }
}
