package com.example.graana;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.graana.Adaptor.HouseImageViewAdaptor;
import com.example.graana.Model.HouseImageView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HouseDetailsActivity extends AppCompatActivity {

    HouseImageViewAdaptor adaptor;

    RecyclerView recyclerView;
    List<HouseImageView> houseImageViewList;
    HouseImageView houseImageView;

    TextView tvOwnerName,tvRent,tvBed,tvBath,tvCategory;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_details);
        reference= FirebaseDatabase.getInstance().getReference();

        recyclerView=findViewById(R.id.houseImageViewRecycler);
        houseImageViewList=new ArrayList<>();

        houseImageViewList.add(new HouseImageView("hfghf"));
        adaptor=new HouseImageViewAdaptor(this,houseImageViewList);
        recyclerView.setAdapter(adaptor);
    }


    public  void BackIcon(View view)
    {
        Intent intent=new Intent(this,HomePageActivity.class);
        startActivity(intent);
    }
    public void Call(View view)
    {

    }
}
