package com.example.graana;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.graana.Fragments.Home_Fragment;
import com.example.graana.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddHouseActivity extends AppCompatActivity {

    Spinner categorySpinner;
    ArrayList<Uri> imageList=new ArrayList<>();
    Uri imageUri;
    ImageView image;
    EditText edtRent;
    RadioGroup rdgBedRoom,rdgBath;
    RadioButton rdbBedRoom,rdbBath;
    EditText edtDiscreption;
    ProgressDialog progressDialog;
    String houseId;
    ArrayList<String> imageArray=new ArrayList<>();
    Map<String,Object> hashMap=new HashMap<>();
    int uploadCount=0;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house);

        categorySpinner=findViewById(R.id.spinnerCategory);
        image=findViewById(R.id.imvHouseImage);
        edtRent=findViewById(R.id.edtRent);
        rdgBedRoom=findViewById(R.id.rdgBedOption);
        rdgBath=findViewById(R.id.rdgBathOption);
        edtDiscreption=findViewById(R.id.edtDiscreption);

        String[] array=new String[Home_Fragment.catagoryModelList.size()];
        array[0]="Select";
        for(int i=1;i<Home_Fragment.catagoryModelList.size();i++)
        {
            array[i]=Home_Fragment.catagoryModelList.get(i).getCatagoryName();
        }

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item, array);
        categorySpinner.setAdapter(arrayAdapter);

    }


    public void AddPic(View view)
    {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(intent,1);




    }

    public void Add(View view)
    {
        int radioBedId=rdgBedRoom.getCheckedRadioButtonId();
        rdbBedRoom=findViewById(radioBedId);
        int radioBathId=rdgBath.getCheckedRadioButtonId();
        rdbBath=findViewById(radioBathId);
        edtDiscreption=findViewById(R.id.edtDiscreption);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading.......");
        progressDialog.show();

        final String bed=rdbBedRoom.getText().toString();
        final String bath=rdbBath.getText().toString();

        if(categorySpinner.getSelectedItem().toString().equals("Select"))
        {
            Toast.makeText(this, "Pleas Select the Category!", Toast.LENGTH_SHORT).show();
            categorySpinner.requestFocus();
        }
        else if(edtRent.getText().toString().equals(""))
        {
            edtRent.requestFocus();
            Toast.makeText(this, "Pleas Enter Rent of House", Toast.LENGTH_SHORT).show();
        }
        else if(edtDiscreption.getText().toString().equals(""))
        {
            edtDiscreption.requestFocus();
            Toast.makeText(this, "Pleas enter Discreption of your House", Toast.LENGTH_SHORT).show();
        }else {
            hashMap.put("OwnerName",LoginActivity.currentOnlineUser.getName());
            hashMap.put("OwnerPhone",LoginActivity.currentOnlineUser.getPhone());
            hashMap.put("Catgeory",categorySpinner.getSelectedItem().toString());
            hashMap.put("Rent",edtRent.getText().toString());
            hashMap.put("Bad",bed);
            hashMap.put("Bath",bath);
            hashMap.put("Discreption",edtDiscreption.getText().toString());

            final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
            houseId =databaseReference.push().getKey();
            databaseReference.child("Houses").child(houseId).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
            hashMap.clear();

            StorageReference imageFolder = FirebaseStorage.getInstance().getReference().child("ImageFolder");

            for (uploadCount = 0; uploadCount < imageList.size(); uploadCount++) {
                Uri individualImage = imageList.get(uploadCount);
                final StorageReference imageName = imageFolder.child("Images" + individualImage.getLastPathSegment());
                imageName.putFile(individualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = String.valueOf(uri);
                                imageArray.add(url);
                            }
                        });

                    }
                });
            }
            HashMap<String,String> map=new HashMap<>();
                for(int i=0;i<imageArray.size();i++)
                {

                    map.put("Image_"+i,imageArray.get(i));
                }
            Toast.makeText(this, "Image Array Size"+imageArray.size(), Toast.LENGTH_SHORT).show();

            DatabaseReference reference=FirebaseDatabase.getInstance().getReference();

            reference.child("Houses").child(houseId).child("Images").setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    progressDialog.dismiss();
                }
            });



                }

        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode == RESULT_OK && data.getClipData()!=null)
        {
            int countClipData=data.getClipData().getItemCount();
            int currentImageSelected=0;
            while(currentImageSelected<countClipData)
            {
                imageUri=data.getClipData().getItemAt(currentImageSelected).getUri();
                imageList.add(imageUri);
                currentImageSelected++;
            }



        }

    }




    public void BackIcon(View view)
    {
        Intent intent=new Intent(this,HomePageActivity.class);
        startActivity(intent);
        finish();
    }
}
