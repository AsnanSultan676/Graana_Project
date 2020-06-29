package com.example.graana;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText edtMail;
    EditText edtName;
    EditText edtPassword;
    EditText edtphone;
    Button btnSignUp;
    TextView tvHaveAccount;


    ProgressDialog loadingBar;


    LoginButton login;
    CallbackManager callbackManager;
    FirebaseAuth firebaseAuth;
    final String[] FbUserphone = {""};





    private static final String TAG="FacebookAuthentication";
    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edtMail=findViewById(R.id.edtMail);
        edtName=findViewById(R.id.edtName);
        edtPassword=findViewById(R.id.edtPassword);
        edtphone=findViewById(R.id.edtPhone);
        btnSignUp=findViewById(R.id.btnSignUp);
        tvHaveAccount=findViewById(R.id.tvHaveAccount);
        loadingBar=new ProgressDialog(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name=edtName.getText().toString().trim();
                final String mail=edtMail.getText().toString().trim();
                final String password=edtPassword.getText().toString().trim();
                final String phone=edtphone.getText().toString().trim();
                if(mail.isEmpty())
                {
                    edtMail.setError("Plz enter the Email");
                    edtMail.requestFocus();
                }
                else if(password.isEmpty())
                {
                    edtPassword.setError("Plz enter the Password");
                    edtPassword.requestFocus();
                }
                else if(phone.isEmpty())
                {
                    edtphone.setError("Plz enter the Password");
                    edtphone.requestFocus();
                }
                else if(mail.isEmpty() &&password.isEmpty() && phone.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Plz fill the boxes", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loadingBar.setTitle("Create Account");
                    loadingBar.setMessage("Please wait, while we are checking the credentials");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                        firebaseAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "signUp Unsuccessful, Please Try Again with Different email!", Toast.LENGTH_SHORT).show();
                                } else {
                                    // new Activity
                                    ValidatePhone(name,mail,password,phone);
                                    Intent intent=new Intent(MainActivity.this,HomePageActivity.class);
                                    startActivity(intent);

                                    //Toast.makeText(MainActivity.this, "Login Successfully new Activity ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
            }
        });

        tvHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        login=findViewById(R.id.login_button);
        login.setReadPermissions("email","public_profile");
        firebaseAuth=FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager=CallbackManager.Factory.create();
        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Log.d(TAG,"onSuccess"+loginResult);
                handleFacebookToken(loginResult.getAccessToken());
                /*do {
                    AlertDialog.Builder getNumber = new AlertDialog.Builder(MainActivity.this);
                    getNumber.setTitle("Phone Number");
                    final EditText phoneNumber = new EditText(MainActivity.this);
                    phoneNumber.setInputType(InputType.TYPE_CLASS_PHONE);
                    getNumber.setView(phoneNumber);
                    getNumber.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FbUserphone[0] =phoneNumber.getText().toString();
                        }
                    });
                    getNumber.show();
                }while(FbUserphone[0].equals(""));

                FirebaseUser user=firebaseAuth.getCurrentUser();
                loadingBar.setTitle("Create Account for facebook");
                loadingBar.setMessage("Please wait, while we are checking the credentials");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                ValidatePhone(user.getDisplayName(),user.getEmail(),user.getUid(),FbUserphone[0]);*/

                Toast.makeText(MainActivity.this, "You are Logined!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel() {
                Log.d(TAG,"onCancel");
                Toast.makeText(MainActivity.this, "You are canceled!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG,"onError"+error);
                Toast.makeText(MainActivity.this, "There is some error!", Toast.LENGTH_SHORT).show();
            }
        });
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null)
                {



                }
            }
        };
        accessTokenTracker=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken==null)
                {
                    firebaseAuth.signOut();
                }
            }
        };

    }



    private Boolean ValidatePhone(final String name, final String email, final String password, final String phone)
    {
        final DatabaseReference rootRef;
        rootRef= FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if(!(dataSnapshot.child("Users").child(phone).exists()))
                {
                    Map<String,Object> userDataMap=new HashMap<>();
                    userDataMap.put("phone",phone);
                    userDataMap.put("name",name);
                    userDataMap.put("email",email);
                    userDataMap.put("password",password);

                    rootRef.child("Users").child(phone).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this, "Account is created!", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                                startActivity(intent);

                            }

                        }
                    });



                }else
                {
                    Toast.makeText(MainActivity.this, "This "+phone+"Already Exists!", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this, "Try Again with another Phone Number ", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,StartUpActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return  true;
    }



    @Override
   protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
    private void handleFacebookToken(AccessToken token)
    {
        Log.d(TAG,"handleFacebookToken"+token);
        AuthCredential authCredential= FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Log.d(TAG,"Sign in with Credential is SuccessFull! ");







                }
                else
                {
                    Log.d(TAG,"Sign in with Credential is Fail! ",task.getException());
                    Toast.makeText(MainActivity.this, "Sign in with Credential is Fail!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!=null)
        {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}
