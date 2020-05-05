package com.example.hussein.farhetkm;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hussein.farhetkm.Model.Products;
import com.example.hussein.farhetkm.Model.users;
import com.example.hussein.farhetkm.Prevalent.Prevalent;
import com.example.hussein.farhetkm.ViewHolder.ProductHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SimpleDressesitem extends AppCompatActivity {
    DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_dressesitem);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Products").child("Category").child("Dresses").child("Simple Dresses");
        recyclerView= findViewById(R.id.rys1);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>().setQuery(databaseReference,Products.class).build();
        FirebaseRecyclerAdapter<Products,ProductHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductHolder holder, int position, @NonNull final Products model) {
                holder.proname.setText(model.getCategory());
                holder.prodesc.setText(model.getDescription());
                holder.proprice.setText("Price = " +model.getPrice());
                holder.protype.setText(model.getType());
                holder.proowner.setText(model.getProowner());
                Picasso.get().load(model.getImage()).into(holder.proimg);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("users1").child(Prevalent.currentonlienuser.getPhone());
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                users u = dataSnapshot.getValue(users.class);
                                if (u.getUsertype().equals("admin")) {
                                    CharSequence sequence[] = {
                                            "Remove", "Block User", "View ", "UnBlock User"
                                    };
                                    AlertDialog.Builder builder = new AlertDialog.Builder(SimpleDressesitem.this);
                                    builder.setTitle("Admin Options");
                                    builder.setItems(sequence, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (i == 0) {

                                                databaseReference.child(model.getID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(SimpleDressesitem.this, "Item removed ", Toast.LENGTH_SHORT).show();

                                                            Intent intent = new Intent(SimpleDressesitem.this, HomeActivity.class);

                                                            startActivity(intent);
                                                        }
                                                    }
                                                });
                                            }
                                            if (i == 1) {
                                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users1").child(model.getProowner());
                                                if (ref.child("state").getKey() == "blk") {

                                                    Toast.makeText(getApplication(), "Already Blocked", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    HashMap<String, Object> userMap = new HashMap<>();
                                                    userMap.put("state", "blk");

                                                    ref.updateChildren(userMap);
                                                }
                                            }
                                            if (i == 2) {
                                                Intent intent = new Intent(SimpleDressesitem.this,ProdetaliesActivity.class);
                                                intent.putExtra("pid", model.getID());

                                                startActivity(intent);
                                            }
                                            if (i == 3) {
                                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users1").child(model.getProowner());

                                                if (ref.child("state").getKey() == "unb") {

                                                    Toast.makeText(getApplication(), "Already UnBlocked", Toast.LENGTH_SHORT).show();
                                                } else {

                                                    HashMap<String, Object> userMap = new HashMap<>();
                                                    userMap.put("state", "unb");

                                                    ref.updateChildren(userMap);
                                                }
                                            }

                                        }
                                    });
                                    builder.show();

                                } else {
                                    if (!u.getPhone().equals(model.getProowner())) {

                                        Intent intent = new Intent(SimpleDressesitem.this,ProdetaliesActivity.class);
                                        intent.putExtra("pid", model.getID());

                                        startActivity(intent);

                                    } else {


                                        CharSequence sequence[] = {
                                                "Remove", "View ","Edit"
                                        };
                                        AlertDialog.Builder builder = new AlertDialog.Builder(SimpleDressesitem.this);
                                        builder.setTitle("Owner Options");
                                        builder.setItems(sequence, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                if (i == 0) {

                                                    databaseReference.child(model.getID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(SimpleDressesitem.this, "Item removed ", Toast.LENGTH_SHORT).show();

                                                                Intent intent = new Intent(SimpleDressesitem.this, HomeActivity.class);

                                                                startActivity(intent);
                                                            }
                                                        }
                                                    });
                                                }


                                                if (i == 1) {
                                                    Intent intent = new Intent(SimpleDressesitem.this, ProdetaliesActivity.class);
                                                    intent.putExtra("pid", model.getID());

                                                    startActivity(intent);
                                                }
                                                if (i == 2) {
                                                    Intent intent = new Intent(SimpleDressesitem.this, EditPro.class);
                                                    intent.putExtra("pid", model.getID());
                                                    intent.putExtra("cat", model.getCategory());
                                                    intent.putExtra("subcat", model.getSubcategory());
                                                    intent.putExtra("price", model.getPrice());
                                                    intent.putExtra("desc", model.getDescription());
                                                    startActivity(intent);
                                                }


                                            }
                                        });
                                        builder.show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


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
