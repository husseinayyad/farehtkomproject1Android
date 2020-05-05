package com.example.hussein.farhetkm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccessoriesView extends AppCompatActivity {
    Button wedd,pardd, sd ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessories_view);
        wedd=findViewById(R.id.wd);
        pardd=findViewById(R.id.pd);

        wedd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplication(),Womenaccessories.class);
                startActivity(intent);
            }
        });
        pardd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplication(),Menaccessoriesitrm.class);
                startActivity(intent);
            }
        });

    }
}
