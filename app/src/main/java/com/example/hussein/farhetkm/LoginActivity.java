package com.example.hussein.farhetkm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hussein.farhetkm.Model.users;
import com.example.hussein.farhetkm.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    EditText phone, passs;
    Button mlog;
    private String tt = "users1";
    private ProgressDialog loadingBar;
    private CheckBox box ;
    private TextView adminlink , notadminlink,forget ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        forget=findViewById(R.id.forgt);
        phone = findViewById(R.id.eph1);
        passs = findViewById(R.id.epaas11);


        loadingBar = new ProgressDialog(this);
        mlog = findViewById(R.id.loginbtn);
        box = findViewById(R.id.cherem);
        Paper.init(this);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this,ForgetPassword.class);
                startActivity(intent);
            }
        });
        mlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             LoginUser();
            }
        });

    }

    private void LoginUser() {
        String phone1 =phone.getText().toString();
        String password = passs.getText().toString();

        if (TextUtils.isEmpty(phone1)) {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);


            loadingBar.show();


            AllowAccessToAccount(phone1, password);
        }
    }


    private void AllowAccessToAccount(final String phone1, final String password) {

if (box.isChecked()){

    Paper.book().write(Prevalent.userphonekey,phone1);
    Paper.book().write(Prevalent.userpasswordkey,password);
}

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("users1").child(phone1).exists() ) {
                    users usersData = dataSnapshot.child("users1").child(phone1).getValue(users.class);


                        if ( usersData.getPhone().equalsIgnoreCase(phone1)) {
                            if (usersData.getPassword().equals(password)) {
                                if (  usersData.getState().equals("blk")){

                                    Toast.makeText(LoginActivity.this, "Sorry You Are Blocked.", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                }
                                else {
                                if (usersData.getUsertype().equals("admin")) {
                                    Toast.makeText(LoginActivity.this, "Welcome Admin, you are logged in Successfully...", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                    Intent intent = new Intent(LoginActivity.this, AdminPage.class);
                                    Prevalent.currentonlienuser = usersData;
                                    startActivity(intent);
                                } else if (usersData.getUsertype().equals("user")) {
                                    Toast.makeText(LoginActivity.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    Prevalent.currentonlienuser = usersData;
                                    startActivity(intent);
                                }
                            }} else {
                                loadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                else {
                        Toast.makeText(LoginActivity.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
