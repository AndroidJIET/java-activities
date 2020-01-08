
/*
The below functions are implemented to appoint a Student as CR and Delete a Student from CR Position
 */

package com.example.cr_appoint_delete;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText mStudentId;
    private Button mAddAsCR;
    private Button mDeleteCr;
    private DatabaseReference mRootRef;
    FireApp mFireApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mStudentId = (EditText)findViewById(R.id.StudentId);
        mAddAsCR = (Button) findViewById(R.id.appoint_CR);
        mDeleteCr = (Button) findViewById(R.id.delete_CR);
        mFireApp=new FireApp();

        mRootRef = FirebaseDatabase.getInstance().getReference().child("app").child("user").child("student").child("uid");

        mAddAsCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFireApp.setStudentId(mStudentId.getText().toString().trim());
               // mRootRef.push().setValue(mFireApp);
                Toast.makeText(MainActivity.this,"you are appointed as CR",Toast.LENGTH_SHORT).show();
            }
        });

        mDeleteCr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCR(mStudentId);
            }
        });

         }

    private void deleteCR(EditText mStudentId) {
        mRootRef.removeValue();
        Toast.makeText(MainActivity.this,"CR deleted",Toast.LENGTH_SHORT).show();
    }
}
