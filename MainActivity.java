package com.example.jiet_mainactivity;

import androidx.annotation.NonNull;
/*
The below functions are implemented to appoint a Student as CR and Delete a Student from CR Position
 */

package com.example.cr_appoint_delete;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mMainBottomNavBar;
    private FrameLayout mMainFrame;
    private PublicFragment mpublicFragment;
    private ClassFragment mclassFragment;
    private ResolvedFragment mresolvedFragment;
    private SolvedFragment msolvedFragment;

    private EditText mStudentId;
    private Button mAddAsCR;
    private Button mDeleteCr;
    private DatabaseReference mRootRef;
    FireApp mFireApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainFrame=(FrameLayout)findViewById(R.id.MainFrame);
        mMainBottomNavBar=(BottomNavigationView)findViewById(R.id.MainBottomNavBar);

        mpublicFragment = new PublicFragment();
        mclassFragment = new ClassFragment();
        mresolvedFragment = new ResolvedFragment();
        msolvedFragment = new SolvedFragment();
        mStudentId = (EditText)findViewById(R.id.StudentId);
        mAddAsCR = (Button) findViewById(R.id.appoint_CR);
        mDeleteCr = (Button) findViewById(R.id.delete_CR);
        mFireApp=new FireApp();

        mRootRef = FirebaseDatabase.getInstance().getReference().child("app").child("user").child("student").child("uid");

        mMainBottomNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
        mAddAsCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.nav_Public :
                        mMainBottomNavBar.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(mpublicFragment);                     //method to shift from one fragment to another
                        return true;

                    case R.id.nav_Class :
                        mMainBottomNavBar.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(mclassFragment);
                        return true;

                    case R.id.nav_Resolved  :
                        mMainBottomNavBar.setItemBackgroundResource(R.color.colorPrimaryDark);
                        setFragment(mresolvedFragment);
                        return true;

                    case R.id.nav_Solved  :
                        mMainBottomNavBar.setItemBackgroundResource(R.color.design_default_color_primary_dark);
                        setFragment(msolvedFragment);
                        return true;

                        default:return false;

                }
            public void onClick(View v) {
                mFireApp.setStudentId(mStudentId.getText().toString().trim());
               // mRootRef.push().setValue(mFireApp);
                Toast.makeText(MainActivity.this,"you are appointed as CR",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.dot_popup_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.raise_a_complaint_menuItem :
                Toast.makeText(this,"Complaint raised selected",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.settings_menuItem  :
                Toast.makeText(this,"Settings clicked",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Contact_Us_menuItem  :
                Toast.makeText(this,"Contact Selected",Toast.LENGTH_SHORT).show();
                return true;

                default:
                    return super.onOptionsItemSelected(item);

        }
    }
        mDeleteCr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCR(mStudentId);
            }
        });

    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.MainFrame,fragment);
        fragmentTransaction.commit();
         }
