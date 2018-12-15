package com.example.asus.medicinelife;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class cart_fragment extends Fragment {

    private DatabaseReference ref;
    private FirebaseAuth mauth;

    private DatabaseReference kref;
    private DatabaseReference Orderref;

    private RecyclerView recyclerView;
    private TextView totalPrice;
    private Button b1, b2;


    int totalSum = 0;

    private TimeHelper timeHelper;

    public cart_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart_fragment, container, false);

        mauth = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();
        String user_Id = user.getUid();



        ref = FirebaseDatabase.getInstance().getReference().child("customer").child(user_Id).child("cart");
        Orderref = FirebaseDatabase.getInstance().getReference().child("customer").child(user_Id).child("order").push();
        kref = FirebaseDatabase.getInstance().getReference().child("medinfo");
        ref.keepSynced(true);

        totalPrice = view.findViewById(R.id.totalPrice);
        b1 = view.findViewById(R.id.OrderNow);
        b2 = view.findViewById(R.id.CancelNow);
        timeHelper = new TimeHelper();


        totalPrice.setText("Total price : " + totalSum + "৳");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Please hold Item", Toast.LENGTH_SHORT).show();

            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.cartContainer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        totalSum = 0;

        FirebaseRecyclerAdapter<cartRecyclerViewHelper, epicViewHolder> fp = new FirebaseRecyclerAdapter<cartRecyclerViewHelper, epicViewHolder>
                (cartRecyclerViewHelper.class, R.layout.cart_single_row, epicViewHolder.class, ref) {
            @Override
            protected void populateViewHolder(final epicViewHolder viewHolder, cartRecyclerViewHelper model, int position) {
                String cart_Id = (model.getId());

                kref.child(cart_Id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            String price = dataSnapshot.child("mp").getValue().toString();
                            String name = dataSnapshot.child("mn").getValue().toString();

                            totalSum = totalSum + Integer.parseInt(price);
                            totalPrice.setText("Total price : " + totalSum + "৳");


                            viewHolder.setKName(name);
                            viewHolder.setKPrice(price);


                            Orderref.child("orderName").setValue("1");
                            Orderref.child("orderPrice").setValue("2");
                            Orderref.child("orderStatus").setValue("3");
                            Orderref.child("time").setValue(timeHelper.getRightNowTime());


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        };
        recyclerView.setAdapter(fp);
        totalSum = 0;
    }

    public static class epicViewHolder extends RecyclerView.ViewHolder {
        View kview;

        public epicViewHolder(View itemView) {
            super(itemView);

            kview = itemView;
        }

        public void setKName(String kName) {
            TextView tm = kview.findViewById(R.id.cartName);
            tm.setText(kName);
        }

        public void setKPrice(String kPrice) {
            TextView tp = kview.findViewById(R.id.cartPrice);
            tp.setText(kPrice);
        }
    }
}
