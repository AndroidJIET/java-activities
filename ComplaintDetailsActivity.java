package com.jiet.androidclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//This Activity will show details of complaint
public class ComplaintDetailsActivity extends AppCompatActivity {
    private TextView mTitle,mDate,mDescription,mNoOfUpvotes,mStatus;
    private ImageView mUpVoteIcon;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_details);

        mTitle = findViewById(R.id.Title);
        mDate = findViewById(R.id.Date);
        mDescription = findViewById(R.id.Description);
        mNoOfUpvotes = findViewById(R.id.NoOfUpvotes);
        mStatus = findViewById(R.id.Status);
        mUpVoteIcon = findViewById(R.id.UpVoteIcon);

        String problem = getIntent().getStringExtra("problem");
        String complaintId = getIntent().getStringExtra("complaintId");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("app").child("complaints").child(problem).child(complaintId);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ComplaintData complaintData = dataSnapshot.getValue(ComplaintData.class);
                mTitle.setText(complaintData.getTitle());
                mDate.setText(complaintData.getDate());
                mDescription.setText(complaintData.getDescription());
                mNoOfUpvotes.setText(complaintData.getUpVote());
                mStatus.setText(complaintData.getStatus());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ComplaintDetailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
