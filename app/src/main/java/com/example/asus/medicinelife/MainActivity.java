package com.example.asus.medicinelife;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment drugsFragment;
    private Fragment orderFragment;
    private Fragment cartFragment;
    private Fragment profileFragment;
    private DrawerLayout drawerLayout;

    private FirebaseAuth mauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mauth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.start_open, R.string.start_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        drugsFragment = new DrugsHomeFragment();
        orderFragment = new order_fragment();
        cartFragment = new cart_fragment();
        profileFragment = new profile_fragment();


        if (savedInstanceState == null){
            setFragment(drugsFragment);
            navigationView.setCheckedItem(R.id.nav_home);
        }


    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FragmentContainer, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home: {
                setFragment(drugsFragment);
                break;
            }
            case R.id.nav_order: {

                FirebaseUser user =  mauth.getCurrentUser();

                if (user == null){

                    Intent intent = new Intent(MainActivity.this , signInactivity.class);
                    startActivity(intent);
                }else{
                    setFragment(orderFragment);
                }

                break;
            }
            case R.id.nav_cart: {

               FirebaseUser user =  mauth.getCurrentUser();

               if (user == null){

                   Intent intent = new Intent(MainActivity.this , signInactivity.class);
                   startActivity(intent);
               }else{
                   setFragment(cartFragment);
               }

                break;
            }
            /*case R.id.nav_profile: {
                //setFragment(profileFragment);
                break;
            }*/
            case R.id.nav_logout: {

                mauth.signOut();
                Toast.makeText(this, "Sign out", Toast.LENGTH_SHORT).show();
                setFragment(drugsFragment);

                break;
            }
            case R.id.nav_info: {
                Toast.makeText(this, "NullStudio Lt.", Toast.LENGTH_SHORT).show();

                break;
            }



        }
        return true;
    }
}

