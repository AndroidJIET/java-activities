package com.jiet.androidclub;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class EndlessScrollWithRecyclerView extends AppCompatActivity {

    private static final int TOTAL_ITEM_ON_SINGLE_LOAD = 10; //No. of raws display in single request
    private DatabaseReference mDatabase;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private LinearLayoutManager linearLayout;
    private ArrayList<String> namesArray = new ArrayList<>();
    private int currentPage = 0; //helps for pagination
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);

        myAdapter = new MyAdapter(namesArray,getApplicationContext());
        recyclerView.setAdapter(myAdapter);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayout) {
            @Override
            public void onLoadMore(int current_page) {
                //progressBar.setVisibility(View.VISIBLE);
                loadMoreData();
            }
        });
        fetchData("complaint category");
    }


    private void fetchData(String category) {
        mDatabase.child("app").child("complaints").child(category)
                .limitToFirst(TOTAL_ITEM_ON_SINGLE_LOAD)
                .startAt(currentPage*TOTAL_ITEM_ON_SINGLE_LOAD).orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //namesArray.clear();
                if(!dataSnapshot.hasChildren()){
                    //This means there is no more data available
                    currentPage--;
                }
                for(DataSnapshot DS : dataSnapshot.getChildren()){
                   //Add data in model class
                }
                //progressBar.setVisibility(View.GONE);
                //myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EndlessScrollWithRecyclerView.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadMoreData() {
        currentPage++;
        fetchData("complaint category");
    }

}
