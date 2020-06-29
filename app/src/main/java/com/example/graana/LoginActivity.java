package com.example.graana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.graana.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText edtMail;
    EditText edtPassword;
    Button btnSignIn;
    ProgressDialog loadingBar;
    String parentDBName="Users";
    static  User currentOnlineUser=new User();

    FirebaseAuth firebaseAuth;
   // private  FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtMail=findViewById(R.id.edtMail2);
        edtPassword=findViewById(R.id.edtPassword2);
        btnSignIn=findViewById(R.id.btnSignIn);
        firebaseAuth=FirebaseAuth.getInstance();
        loadingBar=new ProgressDialog(this);

        /*authStateListener=new FirebaseAuth.AuthStateListener() {
            FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseUser!=null)
                {
                    Toast.makeText(LoginActivity.this, "You Are Logged in", Toast.LENGTH_SHORT).show();

                }else
                {
                    Toast.makeText(LoginActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };*/
      btnSignIn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              LoginUser();

             /* String mail=edtMail.getText().toString().trim();
              String password=edtPassword.getText().toString().trim();
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
              else if(mail.isEmpty() &&password.isEmpty())
              {
                  Toast.makeText(LoginActivity.this, "Plz fill the boxes", Toast.LENGTH_SHORT).show();
              }
              else
              {
                  firebaseAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(LoginActivity.this, "Loged in!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, "There is some Error!", Toast.LENGTH_SHORT).show();
                            }
                      }
                  });
              }
                    */

          }
      });
    }

    private void LoginUser() {
        String phone=edtMail.getText().toString();
        String password=edtPassword.getText().toString();
        if(phone.isEmpty())
        {
            edtMail.setError("Plz enter the Email");
            edtMail.requestFocus();
        }
        else if(password.isEmpty())
        {
            edtPassword.setError("Plz enter the Password");
            edtPassword.requestFocus();
        }
        else if(phone.isEmpty() &&password.isEmpty())
        {
            Toast.makeText(LoginActivity.this, "Plz fill the boxes", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            AllowAccessToAccount(phone,password);
        }
    }

    private void AllowAccessToAccount(final String phone, final String password) {

        final DatabaseReference rootRef;
        rootRef= FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDBName).child(phone).exists())
                {
                     User userData=dataSnapshot.child(parentDBName).child(phone).getValue(User.class);
                    if( userData.getPhone().equals(phone) && userData.getPassword().equals(password))
                    {
                        currentOnlineUser.setName(userData.getName());
                        currentOnlineUser.setPhone(userData.getPhone());
                        currentOnlineUser.setMail(userData.getMail());
                        currentOnlineUser.setPassword(userData.getPassword());
                        Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        Intent intent=new Intent(LoginActivity.this,HomePageActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Account with this phone number do not exits", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

   /* @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }*/
}
