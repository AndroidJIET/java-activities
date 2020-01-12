package com.jiet.androidclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//This Activity will show details of complaint
public class AuthorityComplaintDetailsActivity extends AppCompatActivity {
    private TextView mTitle,mDate,mDescription,mStatus,mLastResponse;
    private Button mResponse;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_details);
        mTitle = findViewById(R.id.Title);
        mDate = findViewById(R.id.Date);
        mDescription = findViewById(R.id.Description);
        mResponse = findViewById(R.id.mResponse);
        mStatus = findViewById(R.id.Status);
        mLastResponse = findViewById(R.id.mLastResponse);

        String problem = getIntent().getStringExtra("problem");
        String complaintId = getIntent().getStringExtra("complaintId");
        String college = getIntent().getStringExtra("college");
        String branch = getIntent().getStringExtra("branch");
        String year = getIntent().getStringExtra("year");
        String section = getIntent().getStringExtra("section");


        mDatabase = FirebaseDatabase.getInstance().getReference().child("app")
                .child("post").child(problem).child(college).child(branch).child(year).child(section).child(complaintId);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ComplaintData complaintData = dataSnapshot.getValue(ComplaintData.class);
                mTitle.setText(complaintData.getTitle());
                mDate.setText(complaintData.getDate());
                mDescription.setText(complaintData.getDescription());
                mStatus.setText(complaintData.getStatus());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AuthorityComplaintDetailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        mResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send response
            }
        });

    }
}
