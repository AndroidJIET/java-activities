/*
Created by Abhishek Kachhwaha for Grievance system on 1/10/2019
 */
package com.example.grievancesystem;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordPopUp extends AppCompatActivity {
    private Button mSendLinkButton;
    private EditText mForgetPasswordEmailEditText;
    private String uEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forget_password_pop_up);

        init();  // init Alias of intialise()
        events(); // function call to register all events


    }

    /*
    The function register all events such as click listener or other action triggered events
    this function is called after intialisation of instances i.e after init()
     */
    private void events() {

        mSendLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRecoveryLink(); /* The function is called when user click on SendLink button */
            }
        });

    }

    /*
     The function takes input in uEmail defined in global section either through user or thorugh Intent
     and send a verification link to the given email through firebase Password reset feature
     */
    private void sendRecoveryLink() {
        uEmail = mForgetPasswordEmailEditText.getText().toString();
        if (uEmail.length() == 0)
            mForgetPasswordEmailEditText.setError("Email is required");
        else {
            mForgetPasswordEmailEditText.setEnabled(false);
            mSendLinkButton.setEnabled(false);
            FirebaseAuth.getInstance().sendPasswordResetEmail(uEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Email sent", Toast.LENGTH_SHORT).show();
                                mForgetPasswordEmailEditText.setEnabled(true);
                                mSendLinkButton.setEnabled(true);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }

    }

    /*
    The function intializes the all the instances and is called at creation of the activity
    */
    private void init() {

        /* Keyboard popup kill */
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mForgetPasswordEmailEditText = (EditText) findViewById(R.id.forgetPasswordPopUpEmailEditText);
        mSendLinkButton = (Button) findViewById(R.id.forgetPasswordPopUpSendLinkButton);

        /*
        To check if the stringExtra passed from Login.class with key "email" has email empty or not
         */
        uEmail = getIntent().getStringExtra("email");
        if (!uEmail.equals(""))
            mForgetPasswordEmailEditText.setText(uEmail);
    }
}
