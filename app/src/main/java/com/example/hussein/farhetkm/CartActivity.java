package com.example.hussein.farhetkm;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hussein.farhetkm.Model.Cart;
import com.example.hussein.farhetkm.Prevalent.Prevalent;
import com.example.hussein.farhetkm.ViewHolder.CartViewHolder;
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
import com.squareup.picasso.Target;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button next;
    private TextView totalprice, msg;
    private int overprice = 0;
    String x=""
;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = findViewById(R.id.catrlist1);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        next = findViewById(R.id.next);
        totalprice = findViewById(R.id.totalprice);
        msg = findViewById(R.id.msg1);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CartActivity.this, HomeActivity.class);

                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Cart list");
        final FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>().setQuery(reference.child("User View").child(Prevalent.currentonlienuser.getPhone()).child("Products"), Cart.class).build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model) {
                holder.tproname.setText(" " + model.getName());
                holder.tproprice.setText("Price =  " + model.getPrice() + "$");
                holder.tproqun.setText("for  " + model.getType());

                holder.proowner.setText(model.getOwner());
                Picasso.get().load(model.getIm()).into(holder.image);
                x=model.getIm();
                int onetypeprice = ((Integer.valueOf(model.getPrice())));
                overprice = Integer.valueOf(model.getPrice());


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence sequence[] = {
                                 "Remove", "Confirm Order", "Call"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Options");
                        builder.setItems(sequence, new DialogInterface.OnClickListener() {
                            @SuppressLint("MissingPermission")
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (i == 0) {
                                    reference.child("User View").child(Prevalent.currentonlienuser.getPhone()).child("Products").child(model.getPid())
                                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(CartActivity.this, "Item removed ", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(CartActivity.this, HomeActivity.class);

                                                startActivity(intent);
                                            }

                                        }
                                    });
                                }
                                if (i == 1) {
                                    Intent intent = new Intent(CartActivity.this, ConfirmorderActivity.class);
                                    intent.putExtra("pid", model.getPid());
                                    intent.putExtra("powner", model.getOwner());
                                    intent.putExtra("tprice", model.getPrice());
                                    intent.putExtra("tpimg",x);
                                    startActivity(intent);
                                }
                                if (i == 2) {
                                    String s = model.getOwner();
                                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", s, null));
                                    startActivity(intent);

                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View  view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cartlistitem,parent,false);
                CartViewHolder  viewHolder= new CartViewHolder(view);
                return viewHolder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    }

