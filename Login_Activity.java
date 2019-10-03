package com.arj.theblogapp;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Login_Activity extends AppCompatActivity {

    EditText email,pw;
    Button login_btn,reg_btn;
    ProgressBar progressBar_login;
    FirebaseAuth mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        email=findViewById(R.id.email_login);
        pw=findViewById(R.id.pw_login);
        progressBar_login=findViewById(R.id.progressBar_login);
        login_btn=findViewById(R.id.login_btn);
        progressBar_login.setVisibility(View.INVISIBLE);
        reg_btn=findViewById(R.id.reg_btn);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRegister=new Intent(Login_Activity.this, Register_Activity.class);
                startActivity(toRegister);
                finish();
            }
        });

        mUser=FirebaseAuth.getInstance();


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_id=email.getText().toString();
                String pass=pw.getText().toString();
                if(!TextUtils.isEmpty(email_id) && !TextUtils.isEmpty(pass)){
                    progressBar_login.setVisibility(View.VISIBLE);
                    mUser.signInWithEmailAndPassword(email_id,pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                sendToMain();
                            }
                            else {
                                String error_login= task.getException().getMessage();
                                Toast.makeText(Login_Activity.this,"Error: "+error_login,Toast.LENGTH_SHORT).show();
                                progressBar_login.setVisibility(View.INVISIBLE);
                            }
                        }
                    })
                    ;
                    progressBar_login.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(Login_Activity.this,"Please Fill up All The Details...",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null) {
            sendToMain();
        }
    }

    private void sendToMain() {
        Intent main_act=new Intent(Login_Activity.this,MainActivity.class);
        startActivity(main_act);
        finish();
    }
}
