/*
Created by Abhishek Kachhwaha for Grievance system on 1/10/2019
 */
package com.example.grievancesystem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifyEmailPopUp extends AppCompatActivity {

    private Button mVerifyButton;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_verify_email_pop_up);
        setFinishOnTouchOutside(false);

        init(); // init Alias of intialise()
        events(); // function call to register all events

    }

    /*
    The function register all events such as click listener or other action triggered events
    this function is called after intialisation of instances i.e after init()
     */
    private void events() {

        mVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCurrentUser.sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),"Verification link sent",Toast.LENGTH_SHORT).show();
                                    sentologin();
                                }

                            }
                        });

            }
        });

    }

    /*
    The function intializes the all the instances and is called at creation of the activity
    */
    private void init() {
        mVerifyButton = findViewById(R.id.VerifyEmailPopUpEmailVerifyButton);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
    }

    /*
    The function is called everytime when the back button is pressed while activity is running
    ,it overrides the normal behaviour of the application flow
    */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //replaces the default 'Back' button action
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            FirebaseAuth.getInstance().signOut(); //Signing out the user
            sentologin(); // The function is called when the user presses the back key to avoid unverified access
        }
        return true;
    }

    /*
    The function send user to login page when the user press back button to prevent unverified authorisation
     */
    private void sentologin() {
        this.startActivity(new Intent(VerifyEmailPopUp.this, Login.class));
    }
}