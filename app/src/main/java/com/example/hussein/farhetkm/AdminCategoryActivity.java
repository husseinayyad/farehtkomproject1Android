package com.example.hussein.farhetkm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {
    private ImageView tshiret ,shoes , head , femaldree ;
    private ImageView swe ,glass ,watch, bags ;
    private ImageView laptop ,headphone ,mobail, sports ;
    private Button order,logout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        logout=findViewById(R.id.logoutbtn);
        tshiret = findViewById(R.id.tshir);  femaldree=findViewById(R.id.femaldress);
        watch=findViewById(R.id.watches); bags=findViewById(R.id.bags) ;

        tshiret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplication(),Add_suitscat.class);
                intent.putExtra("catgory","Suites");
                startActivity(intent);
            }
        });

        femaldree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplication(),Adddresscat.class);
                intent.putExtra("catgory","Dresses");
                startActivity(intent);
            }
        });
       bags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminCategoryActivity.this,Addaccsscat.class);
                intent.putExtra("catgory","Accessories");
                startActivity(intent);
            }
        });


        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminCategoryActivity.this,Addaccsscat.class);
                intent.putExtra("catgory","bags");
                startActivity(intent);
            }
        });

logout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent= new Intent(AdminCategoryActivity.this,HomeActivity.class);

        startActivity(intent);
        finish();
    }
});

    }
}
