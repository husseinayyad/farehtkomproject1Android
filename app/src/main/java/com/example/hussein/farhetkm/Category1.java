package com.example.hussein.farhetkm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Category1 extends AppCompatActivity {
    TextView dress, suit,accs ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category1);
        dress=findViewById(R.id.Dresses);
        suit=findViewById(R.id.Suits);
        accs=findViewById(R.id.Accessories);
        dress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplication(),DressesSubview.class);
                startActivity(intent);
            }
        });
        suit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplication(),SuitsSubview.class);
                startActivity(intent);
            }
        });
        accs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplication(),AccessoriesView.class);
                startActivity(intent);
            }
        });
    }
}
