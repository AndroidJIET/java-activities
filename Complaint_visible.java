package com.example.jiet_mainactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Complaint_visible extends AppCompatActivity {

    EditText mComplaintText;
    private FirebaseDatabase mfirebaseDatabase;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_visible);

    }

        public void setVisible(final String complaintId, final String authority) {

            //lastVisible ++
            //assuming category,lastVisible,authority,mDatabase,mRoot is fetched gloabally and assuming complaints to be public


            mRoot = FirebaseDatabase.getInstance().getReference().child("app");
            mDatabase = mRoot.child("users").child("auth").child("post").child(authority).child("vcomplaint");
            mDatabase.setValue(complaintId).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                        Toast.makeText(getApplicationContext(), "Complaint forwarded", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            if (authority != null) {

                mDatabase = mRoot;


                mDatabase.child("post").child("public").child(complaintId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        lastVisible = dataSnapshot.child("lastvisible").getValue().toString();
                        category = dataSnapshot.child("category").getValue().toString();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                mRoot.child("data").child("pcategories").child(category).child(lastVisible);
                mRoot.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        authority = dataSnapshot.child("name").getValue().toString();
                        mDatabase = mRoot.child("users").child("auth").child("post").child(authority).child("vcomplaint");
                        mDatabase.setValue(complaintId).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                    Toast.makeText(getApplicationContext(), "Complaint forwarded", Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

        }

}
