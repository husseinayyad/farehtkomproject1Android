package com.example.hussein.farhetkm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SuitsSubview extends AppCompatActivity {
    Button wedd,pardd, sd ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suits_subview);
        wedd=findViewById(R.id.wd);
        pardd=findViewById(R.id.pd);
        sd=findViewById(R.id.sd);
        wedd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplication(),weddingsuititem.class);
                startActivity(intent);
            }
        });
        pardd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplication(),Partysuititem.class);
                startActivity(intent);
            }
        });
        sd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplication(),Simplesuititem.class);
                startActivity(intent);
            }
        });
    }
}
