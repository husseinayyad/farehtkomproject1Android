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
import com.example.hussein.farhetkm.ViewHolder.ProductHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ShowUsers extends AppCompatActivity {
    private RecyclerView recyclerView;
    DatabaseReference databaseReference;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("users1");
        setContentView(R.layout.activity_show_users);
        recyclerView= findViewById(R.id.rys1);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<users> options = new FirebaseRecyclerOptions.Builder<users>().setQuery(databaseReference,users.class).build();
        FirebaseRecyclerAdapter<users,ProductHolder> adapter = new FirebaseRecyclerAdapter<users, ProductHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull ProductHolder holder, int position, @NonNull final users model) {
                holder.proname.setText(model.getName());
                holder.prodesc.setText("User Address :"+model.getAddress());
                holder.proprice.setText("User Phone :"+model.getPhone());
                holder.protype.setText("User State :"+model.getState());
                Picasso .get().load(model.getImage()).into(holder.proimg);
                holder.proowner.setText("User Password :"+model.getPassword());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        CharSequence sequence[]={
                                "Block User","UnBlock User"
                        };
                        AlertDialog.Builder  builder = new AlertDialog.Builder(ShowUsers.this);
                        builder.setTitle("Admin Options");
                        builder.setItems(sequence, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (i==0){
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users1").child(model.getPhone());





                                        HashMap<String, Object> userMap = new HashMap<>();
                                        userMap. put("state", "blk");

                                        ref.updateChildren(userMap);
                                    Toast.makeText(getApplication(), "User Blocked", Toast.LENGTH_SHORT).show();}


                                if (i==1){
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users1").child(model.getPhone());







                                        HashMap<String, Object> userMap = new HashMap<>();
                                        userMap.put("state", "unb");

                                        ref.updateChildren(userMap);
                                    Toast.makeText(getApplication(), "User  UnBlocked", Toast.LENGTH_SHORT).show();
                                    }
                                }


                        });
                        builder.show();
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
