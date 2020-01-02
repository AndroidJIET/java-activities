/*
Created by Abhishek Kachhwaha for Grievance system on 1/10/2019
 */
package com.example.grievancesystem;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentuser;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init(); // init Alias of intialise()
    }

    /*
    The function intialises the all the instances and is called at creation of the activity
    */
    private void init() {
        mAuth = FirebaseAuth.getInstance();
        mHandler = new Handler();
    }

    /*
    The function is called everytime when the activity starts ,it overrides the normal behaviour of the application flow
    */
    @Override
    public void onStart() {
        super.onStart();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCurrentuser = mAuth.getCurrentUser();
                if (mCurrentuser == null) {
                    
                    /*
                      Add logo animation to existing splash screen
                    */
                    sendToStart();
                } else {
                    Intent sendtomainpage = new Intent(SplashScreen.this, MainPage.class);
                    mHandler.removeCallbacksAndMessages(null);
                    startActivity(sendtomainpage);

                }
                finish();
            }
        }, 2000);  // 2000 millisecond = 2 seconds
    }

    /*
    Function to send intent to log in page
     */
    private void sendToStart() {
        Intent i = new Intent(SplashScreen.this, MainPage.class);
        startActivity(i);
        finish();
    }
}

