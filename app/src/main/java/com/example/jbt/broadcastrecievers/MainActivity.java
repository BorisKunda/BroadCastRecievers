package com.example.jbt.broadcastrecievers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//IN THIS EXAMPLE OUR APP RECEIVES BROADCAST FROM SYSTEM THAT THERE WAS RECEIVED A CALL


    @SuppressLint("NewApi")// Android 6.0 (API level 23) and above needs runtime permissions.Our min sdk in this project level 15,so there no need in runtime permissions.We added runtime permissions only for sake of example,this why we needed  @SuppressLint("NewApi") cause system "comlains" that there no need in runtime permissions.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {//check if there was already  permission  granted by user for READ_PHONE_STATE USING "checkSelfPermission"
            requestPermissions(new String[] { Manifest.permission.READ_PHONE_STATE} ,12 );//if there wasnt permission granted for READ_PHONE_STATE ask it from user using "requestPermissions"
        }
        else
        {//PERMISSION GRANTED
            //create new instance of broadcast receiver
            MyPhoneReceiver myPhoneReceiver = new MyPhoneReceiver();
            //set new intent filter
            IntentFilter myPhoneFilter= new IntentFilter("android.intent.action.PHONE_STATE");//DEFINE WITCH BROADCASTS (INTENTS) THIS RECEIVER GONNA INTERCEPT
           // IntentFilter myPhoneFilter= new IntentFilter(Intent.ACTION_PHONE_STATE);--> same thing
            //register receiver
            registerReceiver(myPhoneReceiver,myPhoneFilter );

        }
    }



    //CLASS MUST EXTEND BroadcastReceiver SO IT WOULD BE ABLE TO RECEIVE BROADCASTS
    public  class MyPhoneReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {//DEFINE APP'S RESPONSE ON RECEIVING BROADCAST
            String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Toast.makeText(context, number, Toast.LENGTH_SHORT).show();
        }
    }


}
