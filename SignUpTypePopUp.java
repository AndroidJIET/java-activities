/*
Created by Ashok for Grievance system on 2/10/2019
 */
package com.example.grievancesystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SignUpTypePopUp extends AppCompatActivity {

    Spinner mSpinnerForUserType;
    Button mNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_type_pop_up);

        init();  // init Alias of intialise()
        events(); // function call to register all events
    }

    /*
    The function intialises the all the instances and is called at creation of the activity
    */
    private void init() {

        mSpinnerForUserType = findViewById(R.id.SignUpTypePopUpUserTypeSpinner);
        mNextButton = findViewById(R.id.SignUpTypePopUpNextButton);

        String[] userType = {"student","authority"};
        ArrayAdapter<String> stateadapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, userType);
        mSpinnerForUserType.setAdapter(stateadapter);

    }

    /*
    The function intialises the all the instances and is called at creation of the activity
    */
    private void events() {

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToSignUp();  //This function is called when user make it's selection whether he is student or authority and click on next
            }
        });
    }

    /*
    This function send the intent to SignUp.class with putting the selected choice by the user with key "type"
     */
    private void sendToSignUp() {
        Intent sendToSignUp = new Intent(SignUpTypePopUp.this,SignUp.class);
        sendToSignUp.putExtra("type",mSpinnerForUserType.getSelectedItem().toString());
        startActivity(sendToSignUp);
    }
}
