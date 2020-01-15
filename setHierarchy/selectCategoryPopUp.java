package com.example.generateid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class selectCategoryPopUp extends AppCompatActivity {

    Spinner mSelectCategory;
    DatabaseReference mRootDatabase;
    DatabaseReference mDatabase;
    Button mOk;
    ViewSwitcher mViewSwitcher;
    ArrayList<String> categories;
    ArrayAdapter<String> categoryAdapter;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category_pop_up);
        getSupportActionBar().setTitle("Select category");
        intialise();
        fetchdata();
        events();
    }

    private void events() {

        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = mSelectCategory.getSelectedItem().toString();
                if (category.equals("select category")) {
                    Toast.makeText(getApplicationContext(), "Error,Select Category", Toast.LENGTH_LONG).show();
                } else {
                    Intent sendToSetHierarchy = new Intent(selectCategoryPopUp.this, sethierarchy.class);
                    sendToSetHierarchy.putExtra("category", category);
                    startActivity(sendToSetHierarchy);
                }
            }
        });
    }


    public void intialise() {

        mViewSwitcher = findViewById(R.id.selectcategorypopupviewswitcher);
        mSelectCategory = findViewById(R.id.selectCategorySpinner);
        mOk = findViewById(R.id.selectCategoryOk);
        mRootDatabase = FirebaseDatabase.getInstance().getReference().child("app");
        mDatabase = mRootDatabase.child("data").child("pcategory");
        categories = new ArrayList<String>();
        categories.add("select category");

    }

    private void fetchdata() {

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot singlecategory : dataSnapshot.getChildren())
                        categories.add(singlecategory.getKey());
                    categoryAdapter.notifyDataSetChanged();
                    mViewSwitcher.showNext();
                } else {
                    Toast.makeText(getApplicationContext(), "No category found, add category", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        categoryAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, categories);
        mSelectCategory.setAdapter(categoryAdapter);

    }
}
