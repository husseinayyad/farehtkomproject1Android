package com.example.hussein.farhetkm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.hussein.farhetkm.Model.Products;
import com.example.hussein.farhetkm.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Prodetdreswed extends AppCompatActivity {
    private Button addtocart ;
    private ImageView proimg;

    private TextView proname , proprice,proderc,type0,proowner ;
    private String prodid="" , state="normal",imge="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodetdreswed);

        type0= findViewById(R.id.btnnum);
        proimg= findViewById(R.id.proimg);
        proname=findViewById(R.id.prodname11);
        proderc=findViewById(R.id.prodsec11);
        proowner=findViewById(R.id.proDowner);
        prodid=getIntent().getStringExtra("pid");
        addtocart=findViewById(R.id.addtocart);

        proprice=findViewById(R.id.proprice11);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (state.equals("order shipped")){
                    Toast.makeText(Prodetdreswed.this, "you can add purchase more products , once your order is shipped or confirmed",Toast.LENGTH_LONG).show();
                }
                else {
                    addtocartlist();
                }
            }
        });
        getproductsdetalies(prodid);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void addtocartlist() {
        String savecurrentdata, savecurrenttime ;
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("MMM dd ,yyyy");
        savecurrentdata=simpleDateFormat.format(calendar.getTime());
        SimpleDateFormat simpletimeFormat =new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime=simpletimeFormat.format(calendar.getTime());
        final DatabaseReference carlistref = FirebaseDatabase.getInstance().getReference().child("Cart list");
        final HashMap<String,Object> cartmap = new HashMap<>();
        cartmap.put("pid",prodid);
        cartmap.put("name",proname.getText().toString());
        cartmap.put("price",proprice.getText().toString());
        cartmap.put("date",savecurrentdata);
        cartmap.put("im",imge);
        cartmap.put("time",savecurrenttime);
        cartmap.put("owner",proowner.getText().toString());
        cartmap.put("type",type0.getText().toString());
        //  cartmap.put("img",proimg);
        cartmap.put("discount","");
        carlistref.child("User View")
                .child(Prevalent.currentonlienuser.getPhone()).child("Products").child(prodid).updateChildren(cartmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Prodetdreswed.this,"added to cart list", Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(Prodetdreswed.this, HomeActivity.class);
                    startActivity(intent);
                  }
            }
        });
    }

    private void getproductsdetalies(String prodid) {

        DatabaseReference proref= FirebaseDatabase.getInstance().getReference().child("Products").child("Category").child("Dresses").child("wedding Dresses");
        proref.child(prodid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Products products = dataSnapshot.getValue(Products.class);
                proname.setText(products.getCategory());
                proderc.setText(products.getDescription());
                proprice.setText(products.getPrice());
                type0.setText(products.getType());
                proowner.setText(products.getProowner());

                Picasso.get().load(products.getImage()).into(proimg);
                imge=products.getImage();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    }


