package com.example.graana;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.graana.Adaptor.HouseAdaptor;
import com.example.graana.Model.House;

import java.util.List;

public class CatagoryActivity extends AppCompatActivity {

    String getCatagory;
    private RecyclerView HouseRecyclerView;
    private HouseAdaptor HouseAdaptor;
    List<House> houseList;
    LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory);



        Intent intent=getIntent();
        getCatagory=intent.getStringExtra("CatagoryName");

        Toast.makeText(this, getCatagory, Toast.LENGTH_SHORT).show();



    }


    public  void BackIcon(View view)
    {
        Intent intent=new Intent(this,HomePageActivity.class);
        startActivity(intent);
    }

    public void AddHouse(View view)
    {
        Intent intent=new Intent(this,AddHouseActivity.class);
        startActivity(intent);
    }
}
