package com.example.asus.medicinelife;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginContainer extends AppCompatActivity {

    private SignUpActivity signUpActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_container);

        signUpActivity = new SignUpActivity();

        setFragment(signUpActivity);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.signUpinContainer, fragment);
        transaction.commit();
    }
}
