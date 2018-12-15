package com.example.asus.medicinelife;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends Fragment {

    private EditText customerName;
    private EditText customerEmail;
    private EditText customerPass;
    private EditText customerPhone;
    private EditText customerAdress;
    private Button loginButton;
    private FirebaseAuth mauth;
    private Context context;
    private DatabaseReference signupRef;


    public SignUpActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        mauth = FirebaseAuth.getInstance();
        signupRef = FirebaseDatabase.getInstance().getReference().child("customer");

        customerName = view.findViewById(R.id.customerName);
        customerEmail = view.findViewById(R.id.customerEmail);
        customerPass = view.findViewById(R.id.customerPass);
        customerPhone = view.findViewById(R.id.customerphone);
        customerAdress = view.findViewById(R.id.customerAdress);
        loginButton = view.findViewById(R.id.loginButton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = customerName.getText().toString();
                final String email = customerEmail.getText().toString();
                String pass = customerPass.getText().toString();
                final String phone = customerPhone.getText().toString();
                final String address = customerAdress.getText().toString();


                mauth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    FirebaseUser user = mauth.getCurrentUser();
                                    String userId = user.getUid();


                                    signupRef.child(userId).child("name").setValue(name);
                                    signupRef.child(userId).child("email").setValue(email);
                                    signupRef.child(userId).child("phone").setValue(phone);
                                    signupRef.child(userId).child("address").setValue(address);
                                    signupRef.child(userId).child("userId").setValue(userId);
                                    signupRef.child(userId).child("userInfo").setValue(user);

                                    Toast.makeText(context, "Success!!", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getActivity() , MainActivity.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(context, "Failed to Sign Up \n check your Internet connection", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
