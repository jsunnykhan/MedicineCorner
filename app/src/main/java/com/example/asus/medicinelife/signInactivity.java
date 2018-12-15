package com.example.asus.medicinelife;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signInactivity extends AppCompatActivity {

    private EditText emailEt;
    private EditText passEt;
    private Button signInBt;
    private TextView textView;

    private FirebaseAuth mauth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_inactivity);
        mauth = FirebaseAuth.getInstance();

        emailEt = findViewById(R.id.emailEt);
        passEt = findViewById(R.id.passEt);
        signInBt = findViewById(R.id.signInBt);
        textView = findViewById(R.id.createAccount);


        signInBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailEt.getText().toString();
                String pass = passEt.getText().toString();

               mauth.signInWithEmailAndPassword(email , pass)
               .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {

                       if (task.isSuccessful()){
                           Intent intent = new Intent(signInactivity.this , MainActivity.class);
                           startActivity(intent);
                           finish();
                       }else{
                           Toast.makeText(signInactivity.this, "Failed to Sign in", Toast.LENGTH_SHORT).show();
                       }
                   }
               });

            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signInactivity.this , LoginContainer.class);
                startActivity(intent);
            }
        });


    }
}
