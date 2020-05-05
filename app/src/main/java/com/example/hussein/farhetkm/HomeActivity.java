package com.example.hussein.farhetkm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
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

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DatabaseReference databaseReference,databaseReference1;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Products").child("Category").child("Dresses").child("Simple Dresses");
        databaseReference1=FirebaseDatabase.getInstance().getReference().child("Products").child("Category").child("Dresses").child("Party Dresses");
        recyclerView= findViewById(R.id.rys);

        Paper.init(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent  intent = new Intent(HomeActivity.this,CartActivity.class);
             startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);
        TextView username = headerview.findViewById(R.id.userprofilename);
        CircleImageView circleImageView = headerview.findViewById(R.id.profile_image);
username.setText(Prevalent.currentonlienuser.getName());
Picasso.get().load(Prevalent.currentonlienuser.getImage()).placeholder(R.drawable.profile).into(circleImageView);
recyclerView.setHasFixedSize(true);
layoutManager= new LinearLayoutManager(this);
recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products>   options = new FirebaseRecyclerOptions.Builder<Products>().setQuery(databaseReference,Products.class).build();
        FirebaseRecyclerAdapter<Products,ProductHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductHolder>(options) {
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
                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("users1").child(Prevalent.currentonlienuser.getPhone());
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                users u = dataSnapshot.getValue(users.class);
                                if (u.getUsertype().equals("admin")) {
                                    CharSequence sequence[] = {
                                            "Remove", "Block User", "View ", "UnBlock User"
                                    };
                                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                                    builder.setTitle("Admin Options");
                                    builder.setItems(sequence, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (i == 0) {

                                                databaseReference.child(model.getID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(HomeActivity.this, "Item removed ", Toast.LENGTH_SHORT).show();

                                                            Intent intent = new Intent(HomeActivity.this, HomeActivity.class);

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
                                                Intent intent = new Intent(HomeActivity.this,ProdetaliesActivity.class);
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

                                        Intent intent = new Intent(HomeActivity.this,ProdetaliesActivity.class);
                                        intent.putExtra("pid", model.getID());

                                        startActivity(intent);

                                    } else {


                                        CharSequence sequence[] = {
                                                "Remove", "View ","Edit"
                                        };
                                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                                        builder.setTitle("Owner Options");
                                        builder.setItems(sequence, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                if (i == 0) {

                                                    databaseReference.child(model.getID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(HomeActivity.this, "Item removed ", Toast.LENGTH_SHORT).show();

                                                                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);

                                                                startActivity(intent);
                                                            }
                                                        }
                                                    });
                                                }


                                                if (i == 1) {
                                                    Intent intent = new Intent(HomeActivity.this, ProdetaliesActivity.class);
                                                    intent.putExtra("pid", model.getID());

                                                    startActivity(intent);
                                                }
                                                if (i == 2) {
                                                    Intent intent = new Intent(HomeActivity.this, EditPro.class);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart) {
            Intent  intent = new Intent(HomeActivity.this,CartActivity.class);
            startActivity(intent);
        }





        else if (id == R.id.nav_categories) {
            Intent  intent = new Intent(HomeActivity.this,Category1.class);
            startActivity(intent);


        }
        else if (id == R.id.nav_settings) {
            Intent intent = new Intent(HomeActivity.this,SettingsActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.add_pro) {
            Intent intent = new Intent(HomeActivity.this,AdminCategoryActivity.class);
            startActivity(intent);

        }

        else if (id == R.id.myordera) {
            Intent intent = new Intent(HomeActivity.this,adminneworderActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_logout) {
            Paper.book().destroy();
            Intent intent = new Intent(HomeActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
