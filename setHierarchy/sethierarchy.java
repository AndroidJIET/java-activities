package com.example.generateid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static java.security.AccessController.getContext;

public class sethierarchy extends AppCompatActivity implements sequenceRecyclerAdapter.OnItemClicked {



    ViewSwitcher mViewSwitcher;
    ArrayList<String> mPosts;
    ArrayList<sequenceData> mHierarchy;
    ArrayAdapter<String> mPostAdapter;
    HashMap<String, HashMap<String, String>> mHierarchyMap;
    Button mNextButton;
    Button mFinsihButton;
    Button mClear;
    String category, postName;
    Spinner mPostSpinner;
    int sNo = 0;
    RecyclerView mHierarchyRecycler;
    sequenceRecyclerAdapter mRecyclerAdapter;
    DatabaseReference mRoot, mDatabase;
    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sethierarchy);
        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        getSupportActionBar().setTitle("Set hierarchy");
        intialise();
        fetchdata();
        register();
    }


    private void register() {


        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postName = mPostSpinner.getSelectedItem().toString();
                if (postName.equals("Select post")) {
                    Toast.makeText(getApplicationContext(), "Please select post", Toast.LENGTH_LONG).show();
                } else {
                    sNo = sNo + 1;
                    mHierarchy.add(new sequenceData(postName));
                    mRecyclerAdapter.notifyDataSetChanged();
                }

            }
        });


        mFinsihButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int SNo = 0;
                if (mHierarchy.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Add atleast one node", Toast.LENGTH_SHORT).show();
                } else {
                    for (final sequenceData mSingle : mHierarchy) {
                        if (!mSingle.isCloseClicked()) {
                            SNo = SNo + 1;
                            mHierarchyMap.put("" + SNo, new HashMap<String, String>());
                            mHierarchyMap.get("" + SNo).put("name", mSingle.getName());
                        }

                    }


                    mDatabase.child("pcategory").child(category).child("hierarchy").setValue(mHierarchyMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Hierarchy added", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });

        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHierarchy.clear();
                mRecyclerAdapter.notifyDataSetChanged();
                sNo = 0;
            }
        });
    }

    private void fetchdata() {

        mDatabase.child("pcategory").child(category).child("hierarchy").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot sequence : dataSnapshot.getChildren()) {
                        mHierarchy.add(new sequenceData(sequence.child("name").getValue().toString()));

                    }
                    setRecyler(); //called only if sequence exists
                } else {
                    Toast.makeText(getApplicationContext(), "No heirarchy added", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase.child("post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot postName : dataSnapshot.getChildren()) {
                        mPosts.add(postName.getKey());
                    }

                    mPostAdapter.notifyDataSetChanged();
                    mViewSwitcher.showNext();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void setRecyler() {

        mRecyclerAdapter = new sequenceRecyclerAdapter(getApplicationContext(), mHierarchy);
        mHierarchyRecycler.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.setOnClick(sethierarchy.this); // Bind the listener


    }

    private void intialise() {


        mViewSwitcher = findViewById(R.id.sethierarchyviewswitcher);
        Animation in = AnimationUtils.loadAnimation(this,android.R.anim.fade_in); // load an animation
        mViewSwitcher.setInAnimation(in); // set in Animation for ViewSwitcher
        mRoot = FirebaseDatabase.getInstance().getReference().child("app").child("data");
        mDatabase = mRoot;
        mHierarchyRecycler = findViewById(R.id.sethierarchyrecyclerview);
        mNextButton = findViewById(R.id.setHierarchyNextButton);
        mFinsihButton = findViewById(R.id.sethierarchyfinishbutton);
        mClear = findViewById(R.id.sethierarchyclearbutton);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mHierarchyRecycler.setNestedScrollingEnabled(false);
        mHierarchyRecycler.setLayoutManager(horizontalLayoutManagaer);
        mHierarchy = new ArrayList<>();
        mProgress = new ProgressBar(getApplicationContext());

        mPosts = new ArrayList<>();
        mPosts.add("Select post");
        mPostSpinner = findViewById(R.id.setHierarchySpinner);
        mPostAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, mPosts);
        mPostSpinner.setAdapter(mPostAdapter);

        mHierarchyMap = new HashMap<>();

        setRecyler();


    }

    @Override
    public void onItemClick(int position) {


        sequenceData single = mHierarchy.get(position);
        boolean isClosedClicked = single.isCloseClicked();
        single.setCloseClicked(!isClosedClicked);
        mRecyclerAdapter.notifyDataSetChanged();

    }
}
