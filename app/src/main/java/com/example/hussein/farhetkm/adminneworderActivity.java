package com.example.hussein.farhetkm;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hussein.farhetkm.Model.orders;
import com.example.hussein.farhetkm.Model.users;
import com.example.hussein.farhetkm.Prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class adminneworderActivity extends AppCompatActivity {
    private RecyclerView orderlist ;
    String t;
    private DatabaseReference databaseReference ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminneworder);
        t=getIntent().getStringExtra("powner");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("orders").child(Prevalent.currentonlienuser.getPhone());
        orderlist=findViewById(R.id.rys1);
        orderlist.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              final orders uo = dataSnapshot.child("orders").getValue(orders.class);

                    FirebaseRecyclerOptions<orders> options = new FirebaseRecyclerOptions.Builder<orders>().setQuery(databaseReference,orders.class).build();
                    FirebaseRecyclerAdapter<orders,orderholder> adapter = new FirebaseRecyclerAdapter<orders, orderholder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull orderholder holder, final int position, @NonNull final orders model) {
                            holder.usename.setText(model.getName());
                            holder.userphone.setText(model.getPhone());
                            holder.totalprice.setText("Total Price "+model.getTotalAmount());
                            holder.city.setText( "Address :"+model.getAddress()+  " City :"+ model.getCity());
                            holder.userdatetime.setText( " order at  :"+model.getDate()  +" "+ model.getTime());
                           Picasso .get().load(model.getImage()).into(holder.showallproducts);


                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CharSequence [] sequences = new CharSequence[]{
                                            "yes","no"
                                    };
                                    AlertDialog.Builder builder = new AlertDialog.Builder(adminneworderActivity.this);
                                    builder.setTitle("have you shipped this order products ?");
                                    builder.setItems(sequences, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (which==0){

                                                String uid = getRef(position).getKey();
                                                removeorder(uid);
                                            }
                                            else
                                            {
                                                finish();
                                            }

                                        }
                                    });
                                    builder.show();
                                }
                            });


                        }

                        @NonNull
                        @Override
                        public orderholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.orders,parent,false);
                            return new orderholder(view);
                        }
                    };
                    orderlist.setAdapter(adapter);
                    adapter.startListening();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void removeorder(String uid) {
        databaseReference.child(uid).removeValue();
    }

    public static  class orderholder extends RecyclerView.ViewHolder{
        public TextView usename , userphone ,city , price ,totalprice  , userdatetime;
        public ImageView showallproducts ;
        public orderholder(View itemView) {
            super(itemView);
            usename=itemView.findViewById(R.id.username1);
            userphone=itemView.findViewById(R.id.phonenum1);
            city=itemView.findViewById(R.id.address11);
            totalprice=itemView.findViewById(R.id.priceorder);
            userdatetime=itemView.findViewById(R.id.dateandtime);
           showallproducts=itemView.findViewById(R.id.proimeg1);

        }
    }

}
