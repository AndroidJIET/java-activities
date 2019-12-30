package com.example.jiet_mainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mMainBottomNavBar;
    private FrameLayout mMainFrame;
    private PublicFragment mpublicFragment;
    private ClassFragment mclassFragment;
    private ResolvedFragment mresolvedFragment;
    private SolvedFragment msolvedFragment;
    private DatabaseReference databaseReference;
    private RecyclerView mResutList;
    private SearchView mSearchField;
    private Button mSubmitButton;
    private ArrayList<Search> list = new ArrayList<Search>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSubmitButton = (Button) findViewById(R.id.submit_button);
        mSearchField = (SearchView) findViewById(R.id.search_field);
        mResutList = (RecyclerView)findViewById(R.id.result_list);
        mMainFrame = (FrameLayout) findViewById(R.id.MainFrame);
        mMainBottomNavBar = (BottomNavigationView) findViewById(R.id.MainBottomNavBar);
        mclassFragment = new ClassFragment();
        mresolvedFragment = new ResolvedFragment();
        msolvedFragment = new SolvedFragment();
        mpublicFragment = new PublicFragment();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("app").child("complaints");
        databaseReference.push().setValue("123");



        setFragment(mpublicFragment);
        mMainBottomNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_Public:
                        mMainBottomNavBar.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(mpublicFragment);                     //method to shift from one fragment to another
                        return true;

                    case R.id.nav_Class:
                        mMainBottomNavBar.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(mclassFragment);
                        return true;

                    case R.id.nav_Resolved:
                        mMainBottomNavBar.setItemBackgroundResource(R.color.colorPrimaryDark);
                        setFragment(mresolvedFragment);
                        return true;

                    case R.id.nav_Solved:
                        mMainBottomNavBar.setItemBackgroundResource(R.color.design_default_color_primary_dark);
                        setFragment(msolvedFragment);
                        return true;

                    default:
                        return false;

                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(databaseReference!=null){
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            list.add(ds.getValue(Search.class));
                        }

                        AdapterClass adapterClass = new AdapterClass(list);
                        mResutList.setAdapter(adapterClass);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(mSearchField!=null){
            mSearchField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);

                    return true;
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dot_popup_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.raise_a_complaint_menuItem:
                Intent mIntent=new Intent(MainActivity.this,RaiseComplaint.class);
                startActivity(mIntent);
                return false;

            case R.id.settings_menuItem:
                Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Contact_Us_menuItem:
                Toast.makeText(this, "Contact Selected", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.MainFrame, fragment);
        fragmentTransaction.commit();

    }

//    Function to search anything Related to the Cell,Student,Year,etc. and thus to add the info into ArrayList of String Type

    private void search(String str){

        ArrayList<Search> myList = new ArrayList<>();
        for(Search object : list){

            if(object.getDescription().toLowerCase().contains(str.toLowerCase())){

                myList.add(object);
            }

        }

        AdapterClass adapterClass = new AdapterClass(myList);
        mResutList.setAdapter(adapterClass);
    }
}
