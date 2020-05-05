package com.example.hussein.farhetkm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ForgetPassword extends AppCompatActivity {
    Button ch ;
    EditText e ,e1;
    String phone ,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        e=findViewById(R.id.chp);
        e1=findViewById(R.id.chpass);
        ch=findViewById(R.id.btnchange);
        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass=e.getText().toString();
                phone=e1.getText().toString();
                if (TextUtils.isEmpty(pass)){
                    Toast.makeText(getApplication(), "Please Enter Your Password Number...", Toast.LENGTH_SHORT).show();
                }
              else  if (TextUtils.isEmpty(phone)){
                    Toast.makeText(getApplication(), "Please Enter Your Phone Number...", Toast.LENGTH_SHORT).show();
                }
                else {
                    final DatabaseReference RootRef;
                    RootRef = FirebaseDatabase.getInstance().getReference();


                    RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child("users1").child(phone).exists() ){
                                HashMap<String,Object> objectHashMap = new HashMap<>();

                                objectHashMap.put("password", pass);
                                    RootRef.child("users1").child(phone).updateChildren(objectHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getApplication(), "  Done , your password changed", Toast.LENGTH_LONG).show();

                                            Intent intent = new Intent(ForgetPassword.this,LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                            }
                            else {
                                Toast.makeText(getApplication(), "Account with this " + phone + " number do not exists.", Toast.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }
}
