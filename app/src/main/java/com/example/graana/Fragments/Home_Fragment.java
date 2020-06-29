package com.example.graana.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.graana.Adaptor.CatagoryAdaptor;

import com.example.graana.Model.CatagoryModel;
import com.example.graana.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Fragment extends Fragment {



    public Home_Fragment() {
        // Required empty public constructor
    }
    private RecyclerView catagoryRecyclerView;
    private CatagoryAdaptor catagoryAdaptor;
   public static  List<CatagoryModel> catagoryModelList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home_, container, false);
        catagoryRecyclerView=view.findViewById(R.id.catagoryRecyclerView);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        catagoryRecyclerView.setLayoutManager(layoutManager);
         catagoryModelList=new ArrayList<>();
        catagoryModelList.add(new CatagoryModel("Link","Home"));
        catagoryModelList.add(new CatagoryModel("Link","3 Marla"));
        catagoryModelList.add(new CatagoryModel("Link","5 Marla"));
        catagoryModelList.add(new CatagoryModel("Link","10 Marla"));
        catagoryModelList.add(new CatagoryModel("Link","single Story"));
        catagoryModelList.add(new CatagoryModel("Link","Double Story"));
        catagoryModelList.add(new CatagoryModel("Link","saprate"));

        catagoryAdaptor=new CatagoryAdaptor(catagoryModelList);
        catagoryRecyclerView.setAdapter(catagoryAdaptor);
        catagoryAdaptor.notifyDataSetChanged();
        return view;

    }


}
