package com.arj.theblogapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Register_Activity extends AppCompatActivity {


    Button register;
    EditText email,pw,cpw;
    FirebaseAuth user;
    ProgressBar progressBar_reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);

        register=findViewById(R.id.Register_btn);
        email=findViewById(R.id.email_reg);
        pw=findViewById(R.id.pw_reg);
        cpw=findViewById(R.id.cpw_reg);
        progressBar_reg=findViewById(R.id.progressBar_reg);
        user=FirebaseAuth.getInstance();
        progressBar_reg.setVisibility(View.INVISIBLE);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Temail,Tpw,Tcpw;
                Temail=email.getText().toString();
                Tpw=pw.getText().toString();
                Tcpw=cpw.getText().toString();

                if( !TextUtils.isEmpty(Temail) && !TextUtils.isEmpty(Tpw) && !TextUtils.isEmpty(Tcpw) ){
                    progressBar_reg.setVisibility(View.VISIBLE);
                    if(Tcpw.equals(Tpw)){
                        user.createUserWithEmailAndPassword(Temail,Tcpw)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent toMain=new Intent(Register_Activity.this,Account_Setup_Activity.class);
                                    startActivity(toMain);
                                    finish();
                                }
                                else{
                                    String error=task.getException().getMessage();
                                    Toast.makeText(Register_Activity.this,"Error: "+error,Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        ;
                    }
                    else {
                        progressBar_reg.setVisibility(View.INVISIBLE);
                        Toast.makeText(Register_Activity.this,"Please Confirm Password...",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Register_Activity.this,"Please Fill Up all the details...",Toast.LENGTH_SHORT).show();
                }
                progressBar_reg.setVisibility(View.INVISIBLE);
            }
        });
    }
}
