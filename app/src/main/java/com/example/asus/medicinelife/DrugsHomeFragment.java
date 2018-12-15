package com.example.asus.medicinelife;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DrugsHomeFragment extends Fragment {

    private DatabaseReference myref;
    private RecyclerView recyclerView;

    public DrugsHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_drugs_home, container, false);

        myref = FirebaseDatabase.getInstance().getReference().child("medinfo");
        myref.keepSynced(true);

        recyclerView = (RecyclerView) v.findViewById(R.id.rContainer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<MedInfo, DrugsHomeFragment.MedInfoViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MedInfo, DrugsHomeFragment.MedInfoViewHolder>
                (MedInfo.class, R.layout.m_single_row, DrugsHomeFragment.MedInfoViewHolder.class, myref) {
            @Override
            protected void populateViewHolder(DrugsHomeFragment.MedInfoViewHolder viewHolder, MedInfo model, final int position) {

                viewHolder.setMn("Name       : " + model.getMn());
                viewHolder.setMc("Company : " + model.getMc());
                viewHolder.setFo("Form        : " + model.getMf());
                viewHolder.setPr("Price        : " + model.getMp());
                viewHolder.setQua("Quantity   : " + model.getMq());



                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String medicine_Id = getRef(position).getKey();

                        Intent intent = new Intent(getContext() , MedicineInfoShowActivity.class);
                        intent.putExtra("medicine_Id" ,medicine_Id);
                        startActivity(intent);

                    }
                });

            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    public static class MedInfoViewHolder extends RecyclerView.ViewHolder{
        View mview;

        public MedInfoViewHolder(View itemView) {
            super(itemView);

            mview = itemView;
        }

        public void setMn(String mn){
            TextView mName = (TextView) mview.findViewById(R.id.mnTv);
            mName.setText(mn);
        }
        public void setMc(String mc){
            TextView mCom = (TextView) mview.findViewById(R.id.mcTv);
            mCom.setText(mc);
        }
        public void setFo(String fo){
            TextView mFo = (TextView) mview.findViewById(R.id.mfTv);
            mFo.setText(fo);
        }
        public void setPr(String pr){
            TextView mpTv = (TextView) mview.findViewById(R.id.mpTv);
            mpTv.setText(pr);
        }
        public void setQua(String qua){
            TextView mQua = (TextView) mview.findViewById(R.id.mqTv);
            mQua.setText(qua);
        }

    }
}
