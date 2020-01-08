package com.example.cr_appoint_delete;


import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class FireApp extends Application {

    private String StudentId;

    public FireApp(){

    }
    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }
}
