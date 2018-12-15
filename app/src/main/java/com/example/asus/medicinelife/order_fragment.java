package com.example.asus.medicinelife;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class order_fragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference orderRef;
    private FirebaseAuth mauth ;


    public order_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_fragment, container, false);

        mauth = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();
        String user_Id = user.getUid();
        orderRef = FirebaseDatabase.getInstance().getReference().child("customer").child(user_Id).child("order") ;
        orderRef.keepSynced(true);



        recyclerView = (RecyclerView) view.findViewById(R.id.orderContainer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));




        return view;
    }




    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<orderRecycleViewHelper , orderViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<orderRecycleViewHelper, orderViewHolder>
                (orderRecycleViewHelper.class , R.layout.single_order_row , orderViewHolder.class , orderRef) {
            @Override
            protected void populateViewHolder(orderViewHolder viewHolder, orderRecycleViewHelper model, int position) {


                viewHolder.setItemName(model.getOrderName());
                viewHolder.setItemPrice(model.getOrderPrice());
                viewHolder.setItemStatus(model.getOrderStatus());

            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    public static class orderViewHolder extends RecyclerView.ViewHolder{
        View mview;

        public orderViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
        }

        public void setItemName(String itemName){
            TextView  t1= mview.findViewById(R.id.itemName);
            t1.setText("Name : " + itemName);
        }
        public void setItemPrice(String itemPrice){
            TextView  t2= mview.findViewById(R.id.itemPrice);
            t2.setText("Price   : " +itemPrice + " ৳");
        }
        public void setItemStatus(String itemStatus){
            TextView  t3= mview.findViewById(R.id.itemStatus);
            t3.setText("Status : "+itemStatus);
        }



    }

}
