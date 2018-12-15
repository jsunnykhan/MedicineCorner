package com.example.asus.medicinelife;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MedicineInfoShowActivity extends AppCompatActivity {

    private TextView medName;
    private TextView medCompany;
    private TextView medForm;
    private TextView medPrice;
    private TextView medQuantity;
    private Button cardButton ;
    private String DrugsId;

    private DatabaseReference mref;
    private DatabaseReference carref;
    private FirebaseAuth myauth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_info_show);

        myauth = FirebaseAuth.getInstance();
        mref = FirebaseDatabase.getInstance().getReference().child("medinfo");
        mref.keepSynced(true);




        DrugsId = getIntent().getExtras().get("medicine_Id").toString();

        medName = (TextView)findViewById(R.id.medNameTv);
        medCompany = (TextView) findViewById(R.id.medCompanyTv);
        medForm = (TextView) findViewById(R.id.medFormTv);
        medPrice = (TextView) findViewById(R.id.medPriceTv);
        medQuantity = (TextView) findViewById(R.id.medQuantityTv);
        cardButton = (Button) findViewById(R.id.cardButtonId) ;

        retrievDataFromDatabase();


        cardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user =  myauth.getCurrentUser();


                if (user == null){
                    Intent intent = new Intent(MedicineInfoShowActivity.this , signInactivity.class);
                    startActivity(intent);
                }
                else {
                    String userId = user.getUid();
                    carref = FirebaseDatabase.getInstance().getReference().child("customer").child(userId).child("cart").push();


                    carref.child("id").setValue(DrugsId);
                    Toast.makeText(MedicineInfoShowActivity.this, "Added To Card", Toast.LENGTH_SHORT).show();
                }




            }
        });

    }

    private void retrievDataFromDatabase() {

        mref.child(DrugsId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    String name = dataSnapshot.child("mn").getValue().toString();
                    String company = dataSnapshot.child("mc").getValue().toString();
                    String form = dataSnapshot.child("mf").getValue().toString();
                    String price = dataSnapshot.child("mp").getValue().toString();
                    String quantity = dataSnapshot.child("mq").getValue().toString();

                    medName.setText("Drugs Name    : " + name);
                    medCompany.setText("Manufecturer  : " + company);
                    medForm.setText("Drugs Form     : " + form);
                    medPrice.setText("Price                 : " + price);
                    medQuantity.setText("Quantity           : " + quantity);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
