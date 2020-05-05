package com.example.hussein.farhetkm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegActivity extends AppCompatActivity {
    Button req ;
    EditText phone, name , pass;
private ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        req = findViewById(R.id.regbtn);
        phone=findViewById(R.id.erph);
        name=findViewById(R.id.ername);
        pass=findViewById(R.id.erpaas);
        progressDialog= new ProgressDialog(this);
req.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        creataccount();
    }
});
    }

    private void creataccount() {
     String name1= name.getText().toString();
        String phone1= phone.getText().toString();
        String pass1= pass.getText().toString();
        if (TextUtils.isEmpty(name1)){

            Toast.makeText(this,"Please enter your name " ,Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(pass1)){

            Toast.makeText(this,"Please enter your password " ,Toast.LENGTH_LONG).show();
        }
       else  if (TextUtils.isEmpty(phone1)){

            Toast.makeText(this,"Please  enter your phone " ,Toast.LENGTH_LONG).show();
        }
        else  if ( !phone1.startsWith("07")){

            Toast.makeText(this,"Please Enter a Valid Phone Number" ,Toast.LENGTH_LONG).show();
        }
          else  if (phone1.length()>10 || phone1.length()<10 ){

            Toast.makeText(this,"Please Enter a Valid Phone Number" ,Toast.LENGTH_LONG).show();
        }
        else
        {
            progressDialog.setTitle("create account ");
            progressDialog.setMessage("please wait ");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            validatephonenumber(name1,phone1,pass1);




        }

    }

    private void validatephonenumber(final String name, final String phone, final String pass) {
final DatabaseReference reference ;
reference= FirebaseDatabase.getInstance().getReference();
reference.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (!(dataSnapshot.child("users1").child(phone).exists())){
            HashMap<String,Object> objectHashMap = new HashMap<>();
            objectHashMap.put("phone", phone);
            objectHashMap.put("password", pass);
            objectHashMap.put("name", name);
            objectHashMap.put("usertype", "user");
            objectHashMap.put("state", "unb");


            reference.child("users1").child(phone).updateChildren(objectHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegActivity.this, "  Done , your account  has been created", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(RegActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(RegActivity.this, " network error plz rty again", Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
        else
        {

            Toast.makeText(RegActivity.this,"this " + phone +"already exists ",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            Toast.makeText(RegActivity.this,"please try again with another phone number",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(RegActivity.this,MainActivity.class);
            startActivity(intent);

        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});
    }
}
