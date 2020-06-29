package com.example.graana.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.graana.Adaptor.CatagoryAdaptor;
import com.example.graana.Adaptor.HouseAdaptor;
import com.example.graana.Model.CatagoryModel;
import com.example.graana.Model.House;
import com.example.graana.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatagoryDataFragment extends Fragment{


    public CatagoryDataFragment() {
        // Required empty public constructor
    }
    private RecyclerView HouseRecyclerView;
    private HouseAdaptor HouseAdaptor;
    List<House> houseList;
    LinearLayoutManager layoutManager;
    DatabaseReference reference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        houseList=new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference().child("Houses");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren())
                {
                    houseList.add(new House((data.child("OwnerName").getValue().toString()),data.child("OwnerPhone").getValue().toString(),data.child("Rent").getValue().toString(),"Lahore",data.child("Category").getValue().toString()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        View view= inflater.inflate(R.layout.fragment_catagory_data, container, false);
        HouseRecyclerView=view.findViewById(R.id.houseRecyclerView);

        layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        HouseRecyclerView.setLayoutManager(layoutManager);
        HouseAdaptor=new HouseAdaptor(houseList);
        HouseRecyclerView.setAdapter(HouseAdaptor);
        HouseAdaptor.notifyDataSetChanged();

        return  view;
    }


}
