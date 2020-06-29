package com.example.graana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartUpActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

    }
    public void SignIn(View v)
    {
        Intent intent=new Intent(StartUpActivity.this,LoginActivity.class);
        startActivity(intent);
    }
    public void SignUp(View v)
    {
        Intent intent=new Intent(StartUpActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
