package com.example.graana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.graana.Model.CatagoryModel;

import com.example.graana.Model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG="HomePageActivity";

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;

    ImageView userImage;
    TextView userName;

    FrameLayout frameLayout;
    ArrayList<CatagoryModel> catagoryModel;
    String parentDBName="Users";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        initViews();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        String user=getUser(LoginActivity.currentOnlineUser.getName());

       // View view=navigationView.findViewById(R.id.navigation_header);
//        userName=view.findViewById(R.id.tvHeaderUserName);
      //  userName.setText(user);







    }

    private void initViews() {
        Log.d(TAG,"initViews:started");
        drawer=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.navigation_drawer);
        toolbar=findViewById(R.id.toolbar);

        frameLayout=findViewById(R.id.main_frame_layout);

        navigationView.setNavigationItemSelectedListener(this);
    }
    public  String getUser(final String number)
    {
        final String[] user = new String[1];
        final User[] userData = new User[1];

        final DatabaseReference rootRef;
        rootRef= FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Toast.makeText(HomePageActivity.this, ""+number, Toast.LENGTH_SHORT).show();
                  //  userData[0] = dataSnapshot.child(parentDBName).child(number).getValue(User.class);
                   // user[0] = userData[0].getName()+"";

                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
            return user[0];
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.item1:
                Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item2:
                Intent intent=new Intent(this,AddHouseActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Add House selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item3:
                Toast.makeText(this, "Item 3 selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item4:
                Toast.makeText(this, "Item 4 selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item5:
                Toast.makeText(this, "Item 5 selected", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void AddHouse(View view)
    {
        Intent intent=new Intent(this,AddHouseActivity.class);
        startActivity(intent);
    }


}
