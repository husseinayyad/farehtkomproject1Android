package com.example.hussein.farhetkm;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hussein.farhetkm.Model.Cart;
import com.example.hussein.farhetkm.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmorderActivity extends AppCompatActivity {
    private EditText ename ,eadrees,ecity ;

    private Button confirem ;
    private String totalamount="",powner1="" , pimage="" ,pid="", ph="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorder);
        totalamount= getIntent().getStringExtra("powner");
   pid= getIntent().getStringExtra("pid");
        powner1=getIntent().getStringExtra("tprice");
        pimage=getIntent().getStringExtra("tpimg");
        Toast.makeText(ConfirmorderActivity.this,"Total amount = "+ powner1,Toast.LENGTH_LONG).show();
        ename=findViewById(R.id.txtname);

            ph=Prevalent.currentonlienuser.getPhone();
        eadrees=findViewById(R.id.txtadrees);
        ecity=findViewById(R.id.txtcity);
        confirem=findViewById(R.id.confirembtn);
        confirem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check();

            }
        });
    }

    private void Check() {
        if (TextUtils.isEmpty(ename.getText().toString())){
            Toast.makeText(ConfirmorderActivity.this,"Please Provide your Name",Toast.LENGTH_LONG).show();
        }

         else if (TextUtils.isEmpty(eadrees.getText().toString())){
            Toast.makeText(ConfirmorderActivity.this,"Please Provide your Address",Toast.LENGTH_LONG).show();
        }
         else if (TextUtils.isEmpty(ecity.getText().toString())){
            Toast.makeText(ConfirmorderActivity.this,"Please Provide your City",Toast.LENGTH_LONG).show();
        }

        else{
            
            ConfiremOredr();
        }
    }

    private void ConfiremOredr() {
        final String savecurrentdata, savecurrenttime;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd ,yyyy");
        savecurrentdata = simpleDateFormat.format(calendar.getTime());
        SimpleDateFormat simpletimeFormat = new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime = simpletimeFormat.format(calendar.getTime());


            final DatabaseReference order = FirebaseDatabase.getInstance().getReference().child("orders").child(totalamount).child(savecurrenttime+savecurrentdata);

            HashMap<String, Object> ordermap = new HashMap<>();
            ordermap.put("TotalAmount", powner1);
            ordermap.put("name", ename.getText().toString());
            ordermap.put("phone", ph);
            ordermap.put("date", savecurrentdata);
            ordermap.put("time", savecurrenttime);
        ordermap.put("oowner", totalamount);
            ordermap.put("city", ecity.getText().toString());
            ordermap.put("Address", eadrees.getText().toString());
            ordermap.put("state", "not shipped");
        ordermap.put("image", pimage);
            order.updateChildren(ordermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        FirebaseDatabase.getInstance().getReference().child("Cart list").
                                child("User View").child(Prevalent.currentonlienuser.getPhone()).child("Products").child(pid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ConfirmorderActivity.this, "your final order has been  placed successfully", Toast.LENGTH_LONG).show();


                                    Intent intent = new Intent(ConfirmorderActivity.this, HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    finish();
                                    startActivity(intent);
                                }

                            }
                        });

                    }

                }
            });


        }
    }

