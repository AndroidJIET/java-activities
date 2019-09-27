package com.example.friendsmarketing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {

    private Button mNeedanaccountbtn;
    private Button mLogin;
    private Button mForgetpasswordbutton;
    private EditText mEmail;
    private EditText mPassword;
    private ProgressDialog mProgressdialog;
    private String uemail;
    private String upassword;
    private FirebaseAuth mAuth;
    FirebaseUser mCurrentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Keyboard popup kill
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //instances
        mNeedanaccountbtn = (Button) findViewById(R.id.loginneedanacountbutton);
        mLogin = (Button) findViewById(R.id.loginloginbutton);
        mEmail = (EditText) findViewById(R.id.loginemailedittext);
        mPassword = (EditText) findViewById(R.id.loginpasswordedittext);
        mProgressdialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mForgetpasswordbutton = (Button)findViewById(R.id.loginforgetpasswordbutton);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uemail = mEmail.getText().toString();
                upassword = mPassword.getText().toString();
                if (uemail.length() == 0)
                    mEmail.setError("Email is required");
                else if (upassword.length() == 0)
                    mPassword.setError("Passsword is required");
                else {
                    mProgressdialog.setTitle("Logging in");
                    mProgressdialog.setMessage("Please wait..");
                    mProgressdialog.setCanceledOnTouchOutside(false);
                    mProgressdialog.show();
                    loginUser(uemail, upassword);

                }
            }
        });

        mNeedanaccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent senttosignuppagedialog = new Intent(getApplicationContext(),signupdialog.class);
                startActivity(senttosignuppagedialog);
            }
        });

        mForgetpasswordbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendtoforgetpasswordpopup = new Intent(getApplicationContext(),forgetpasswordpopup.class);
                startActivity(sendtoforgetpasswordpopup);
            }
        });

        //code to sent otp folded for future need
        {
/*
    private void sendotp() {
        contact = "+91 " + contact;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                contact,
                60,
                TimeUnit.SECONDS,
                Login.this,
                mCallbacks);
    }

        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                codesent = s;
                mProgressdialog.dismiss();
                Intent sendtovalidatepopupforlogin  = new Intent(getApplicationContext(),validatepopupforlogin.class);
                sendtovalidatepopupforlogin.putExtra("otpfromlogin",codesent);
                startActivity(sendtovalidatepopupforlogin);
            }
        };*/
        }

    }

    private void loginUser(String uemail, String upassword) {
        mAuth.signInWithEmailAndPassword(uemail, upassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mCurrentuser = mAuth.getCurrentUser();
                    mProgressdialog.dismiss();
                    Intent sendtomainpage = new Intent(getApplicationContext(), Mainpage.class);
                    sendtomainpage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(sendtomainpage);
                    finish();
                } else {
                    mProgressdialog.hide();
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
