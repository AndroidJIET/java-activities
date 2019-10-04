package com.wizard.androidclubproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity{

    FirebaseAuth firebaseAuth;
    Button signIn,signUp;
    EditText editPw,editEmail;
    ProgressBar pbLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editEmail=findViewById(R.id.editEmail);
        editPw=findViewById(R.id.editPw);
        signUp=findViewById(R.id.signUp);
        signIn=findViewById(R.id.signIn);
        pbLogin=findViewById(R.id.pbLogin);
        firebaseAuth =FirebaseAuth.getInstance();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!networkAvail()){
                    Toast.makeText(LoginActivity.this,"Connection Lost",Toast.LENGTH_SHORT).show();
                }else {
                    String email=editEmail.getText().toString(),pw=editPw.getText().toString();
                    if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pw)){
                        pbLogin.setVisibility(View.VISIBLE);
                        firebaseAuth.signInWithEmailAndPassword(editEmail.getText().toString(),editPw.getText().toString())
                                .addOnCompleteListener(LoginActivity.this,
                                        new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()){
                                                    pbLogin.setVisibility(View.VISIBLE);
                                                    editEmail.setBackgroundColor(Color.GREEN);
                                                    editPw.setBackgroundColor(Color.GREEN);
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            sendToMain();
                                                            finish();
                                                        }
                                                    },2000);
                                                }
                                                else {
                                                    pbLogin.setVisibility(View.INVISIBLE);
                                                    Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG
                                                    ).show();
                                                }
                                            }
                                        });
                    }
                    if (TextUtils.isEmpty(email)){
                        editEmail.setError("Please Fill Valid Email Id !");
                    }
                    if (TextUtils.isEmpty(pw)){
                        editPw.setError("please Enter the valid password !");
                    }
                }
            }
        });
    }

    private void sendToMain() {
        Intent main=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(main);
    }
    @Override
    protected void onStart() {
        super.onStart();
            FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
            if(currentUser!=null){
                sendToMain();
                finish();
            }
        }
    private boolean networkAvail(){
        ConnectivityManager cm=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=cm.getActiveNetworkInfo();
            return networkInfo!=null && networkInfo.isConnected();
    }
}
