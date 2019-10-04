package com.example.jiet_mainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mMainBottomNavBar;
    private FrameLayout mMainFrame;
    private PublicFragment mpublicFragment;
    private ClassFragment mclassFragment;
    private ResolvedFragment mresolvedFragment;
    private SolvedFragment msolvedFragment;

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


        mMainBottomNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
            }
        });
    }

   /* 
    The function everytime the activity is started
   */

    @Override
    protected void onStart()
    {
        super.onStart();
        if (!IsEmailVerified()) {
            showEmailVerificationPopUp = new Intent(Mainpage.this, emailverificationpopup.class);
            startActivity(showEmailVerificationPopUp);
        }

    }
	
    /*
    The function check if the user wither newly registered or existing user has verified email or not and then send the intent accordingly
    */	
    private boolean IsEmailVerified() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.isEmailVerified();   // check if user is verified or not
        }
        else
            return false;
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

    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.MainFrame,fragment);
        fragmentTransaction.commit();

    }
}
