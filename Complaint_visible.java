package com.example.jiet_mainactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Complaint_visible extends AppCompatActivity {

    public static final int REST_TIME=10;
    // irrelevant use of REST TIME
    EditText mComplaintText;
    private ChildEventListener mChildEventListener;
    private FirebaseDatabase mfirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_visible);
        mComplaintText=(EditText)findViewById(R.id.comp);
        mfirebaseDatabase=mfirebaseDatabase.getInstance();
        mDatabaseReference=mfirebaseDatabase.getReference().child("app").child("users").child("auth").child("post").child("authority").child("vcomplaint").child("complaintId");

        if(REST_TIME>10){
            setComplaintVisibility("",""); // Have to do over firebase

             /* Just accessing the firebase need to be read write */
            mChildEventListener=new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Authority authority=dataSnapshot.getValue(Authority.class);
                    setComplaintVisibility(authority.getComplaintId(),"");
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
        }

    }

    public boolean setComplaintVisibility(String complaintID,String authority){

            if (authority == null) {
                mComplaintText.setVisibility(View.VISIBLE);  // setVisibility? why on views has to over firebase 
                Authority authority1 = new Authority(complaintID);
                mDatabaseReference.push().setValue(authority1);
            } else {
                mComplaintText.setVisibility(View.VISIBLE);
                mDatabaseReference.push().setValue(authority);
            }

        return true;
    }
}
