package com.manoj.androidclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {
    DatabaseReference mDatabase;
    FirebaseUser mCurrentUser;
    String mEmail;
    String mName;
    String mDepartmant;
    String mYear;
    String mRegNumber;
    String mCollege;
    String mPassword;
    String mConfirmPassword;
    String mStudentId;

    private EditText EmailEditText;
    private EditText NameEditText;
    private EditText DepartmantEditText;
    private EditText YearEditText;
    private EditText CollegeEditText;
    private EditText RegNumberEditText;
    private EditText ClassEditText;
    private EditText PasswordEditText;
    private EditText ConfirmPasswordEditText;
    private Button SubmitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
        //Dummy inputs for testing purpose
        // mEmail = "manoj.17jccs047@jietjodhpur.ac.in";
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmail = EmailEditText.getText().toString().trim();
                if(mEmail.isEmpty()||!isValidEmail(mEmail)){
                    EmailEditText.setError("Please enter your valid college mail ID");
                }else{
                    //Checking domain name
                    //Domain name must be jietjodhpur.ac.in
                    //Below arrOfStr[1] will return domain name
                    String[] arrOfStr = mEmail.split("@", 2);
                    if (arrOfStr[1].equals("jietjodhpur.ac.in")) {
                        if (true/*getIntent().getStringExtra("student").equals("Student")*/) {
                            studentRegistration(mEmail);
                        } else {
                            YearEditText.setVisibility(View.GONE);
                            CollegeEditText.setVisibility(View.GONE);
                            RegNumberEditText.setVisibility(View.GONE);
                            ClassEditText.setVisibility(View.GONE);
                            authorityRegistration(mEmail);
                        }
                    } else {
                        EmailEditText.setError("Please enter your valid college mail ID");
                    }
                }
            }
        });
    }
    private void init() {
        mDatabase = FirebaseDatabase.getInstance().getReference(/*Get Reference according to database*/);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        EmailEditText = findViewById(R.id.EmailEditText);
        NameEditText = findViewById(R.id.NameEditText);
        DepartmantEditText = findViewById(R.id.DepartmantEditText);
        YearEditText = findViewById(R.id.YearEditText);
        CollegeEditText = findViewById(R.id.CollegeEditText);
        RegNumberEditText = findViewById(R.id.RegNumberEditText);
        SubmitButton = findViewById(R.id.SubmitButton);
    }
    private void studentRegistration(String mEmail) {
        String[] arrOfStr = mEmail.split("\\.", 2);
        //arrOfStr[0] will return "manoj"
        mName = arrOfStr[0];
        NameEditText.setText("Name : " + mName.toUpperCase());
        //arrOfStr[1] will return "17jccs047@jietjodhpur.ac.in"
        //arrOfStr[1].substring(0,2) will return 17
        int mAdmissionYear = Integer.parseInt(arrOfStr[1].substring(0, 2));
        //Calendar.getInstance().get(Calendar.YEAR) it will return current year e.g 2019
        int mCurrentYear = (Calendar.getInstance().get(Calendar.YEAR) - (mAdmissionYear + 2000)) + 1;
        mYear = "" + mCurrentYear;
        YearEditText.setText("Year : " + mYear);
        mDepartmant = arrOfStr[1].substring(4, 6);//arrOfStr[1].substring(4,6) will return branch e.g. cs
        DepartmantEditText.setText("Branch : " + mDepartmant.toUpperCase());

        mCollege = arrOfStr[1].substring(2, 4);
        //arrOfStr[1].substring(2,4) will return college i.e. jc and jt

        mRegDigit = arrOfStr[1].substring(6, 9);
        // mRegDigit stores the last digit of registration number

        if (mCollege.equals("jc")) {
            CollegeEditText.setText("College : JIET-COE");
            mRegNumber = "COE/" + mDepartmant.toUpperCase() + "/" + mAdmissionYear + "/" + arrOfStr[1].substring(6, 9);
            mRegNumber = "COE/" + mDepartmant.toUpperCase() + "/" + mAdmissionYear + "/" + mRegDigit;
            RegNumberEditText.setText("Reg. No.: " + mRegNumber);
        } else {
            mRegNumber = "COED/" + mDepartmant.toUpperCase() + "/" + mAdmissionYear + "/" + arrOfStr[1].substring(6, 9);
            mRegNumber = "COED/" + mDepartmant.toUpperCase() + "/" + mAdmissionYear + "/" + mRegDigit;
            CollegeEditText.setText("College : JIET");
            RegNumberEditText.setText("Reg. No.: " + mRegNumber);
        }

        //TODO create studentPOJOClass having fields mName,mYear,mDepartmant,mCollege,mRegNumber;
        // String userUid = mCurrentUser.getUid();
        //TODO mDatabase.child("students").child(userUid).setValue(studentPOJOClass.class);
        /* Functional call to genetrate id of student based upon constraits provided
           Parameter Description
           @param : mAdmission year stores the student admission year in two digit
           @param : mCollege stores the college in which student is enrolled in 
           @param : mDeaprtment stores the department of college 
           @param : mRegDigit stores the student id number of three digit
          */
        mStudentId = generateId(mAdmissionYear,mCollege,mDepartment,mRegDigit);

        /*
         TODO create studentPOJOClass having fields mName,mYear,mDepartmant,mCollege,mRegNumber,studentId;
         String userUid = mCurrentUser.getUid();
         TODO mDatabase.child("students").child(userUid).setValue(studentPOJOClass.class);
         Store Student id into database under tag "studentid" stored in string StudentId 
        */

        startActivity(new Intent(SignUpActivity.this,VerifyEmailPopUp.class));
    }

    private void authorityRegistration(String mEmail) {
        //TODO (Under Development)
    }
    private boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public String generateId(String mAdmissionYear,String mCollege,String mDepartment,String mRegDigit){
     /*
      Merge function here
     */
    }
}
