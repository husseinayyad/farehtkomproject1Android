package com.example.hussein.farhetkm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hussein.farhetkm.Model.users;
import com.example.hussein.farhetkm.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    Button joinnow ;
    Button wellcomelog ;
    private String tt = "users1";
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        joinnow = findViewById(R.id.join);
        wellcomelog = findViewById(R.id.wellcome);
        loadingBar = new ProgressDialog(this);
        Paper.init(this);
        wellcomelog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        joinnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegActivity.class);
                startActivity(intent);
            }
        });
        String phonekey= Paper.book().read(Prevalent.userphonekey);
        String passkey= Paper.book().read(Prevalent.userpasswordkey);
        if (phonekey!="" && passkey!=""){
            if (!TextUtils.isEmpty(passkey) && !TextUtils.isEmpty(phonekey)){
                Allowacces(phonekey ,passkey);
                loadingBar.setTitle("Already logged in ");
                loadingBar.setMessage("Please wait.....");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

            }

        }

    }

    private void Allowacces(final String phonekey, final String passkey) {


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("users1").child(phonekey).exists()) {
                    users usersData = dataSnapshot.child("users1").child(phonekey).getValue(users.class);

                    if ( usersData.getPhone().equalsIgnoreCase(phonekey)) {
                        if (usersData.getPassword().equals(passkey)) {
                            if (usersData.getUsertype().equals("admin")) {
                                Toast.makeText(MainActivity.this, "Welcome Admin, you are logged in Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intent);
                            } else if (usersData.getUsertype().equals("user")) {
                                Toast.makeText(MainActivity.this, "you are already logged in...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                Prevalent.currentonlienuser = usersData;
                                startActivity(intent);
                            }
                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                else {
                    Toast.makeText(MainActivity.this, "Account with this " + phonekey + " number do not exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
