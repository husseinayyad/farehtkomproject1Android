package com.example.hussein.farhetkm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.hussein.farhetkm.Model.Products;
import com.example.hussein.farhetkm.ViewHolder.ProductHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SearchActivity extends AppCompatActivity {
    private Button serc ;
    private EditText inputtext ;
    private RecyclerView recyclerView;
    String serinput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        serc=findViewById(R.id.btn55);
        inputtext=findViewById(R.id.serc);
        recyclerView=findViewById(R.id.rys4);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        serc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serinput=inputtext.getText().toString();
                onStart();
            }
        });

    }

    @Override
    protected void onStart() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Products").child("Category").child("Dresses").child("Party Dresses");
        super.onStart();

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>().
                setQuery(reference.orderByChild("price").equalTo(serinput),Products.class).build();
        FirebaseRecyclerAdapter<Products,ProductHolder>adapter= new FirebaseRecyclerAdapter<Products, ProductHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductHolder holder, int position, @NonNull final Products model) {
                holder.proname.setText(model.getCategory());
                holder.prodesc.setText(model.getDescription());
                holder.proprice.setText("Price = " +model.getPrice());
                holder.protype.setText(model.getType());
                holder.proowner.setText(model.getProowner());
                Picasso .get().load(model.getImage()).into(holder.proimg);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent= new Intent(SearchActivity.this,ProdetaliesActivity.class);
                        intent.putExtra("pid",model.getID());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent , false);
                ProductHolder holder = new ProductHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
