package com.example.hussein.farhetkm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hussein.farhetkm.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.HashMap;

public class Add_suitscat extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton r1 ,r2 ;

    private  String getcatgory ,category11 , des1  ,type1,owner,
            subcategory1, price1 , savedata , savetime , proudctrandomkey , donlowadimg;
    private Button addnew ;
    private EditText category , des , price , type,subcategory,proowner;
    private ImageView inputimage ;
    private  static  final  int galaryn =1;
    private Uri imageuri ;
    private StorageReference storageReference ;
    private DatabaseReference databaseReference;
    private ProgressDialog loadingBar;
    String radiotext;
    Spinner spinner , spinner1;
    TextView category0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_suitscat);
        addnew=findViewById(R.id.addpro);
        // category=findViewById(R.id.procat);

       radioGroup=findViewById(R.id.protype);
        category0= findViewById(R.id.tv30);
        r1=findViewById(R.id.rent);
        r2=findViewById(R.id.sale);


        spinner1 = (Spinner) findViewById(R.id.subcatspiner);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.suitssub_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter1);






        des=findViewById(R.id.prodesc);
        price=findViewById(R.id.proprice);
        inputimage=findViewById(R.id.selectpro);
        storageReference=FirebaseStorage.getInstance().getReference().child(" images");
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Products").child("Category");
        loadingBar= new ProgressDialog(this);
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validaadata();
            }
        });
        inputimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallry();
            }
        });
        getcatgory=getIntent().getExtras().getString("catgory").toString();
        Toast.makeText(this,getcatgory,Toast.LENGTH_LONG);
    }



    private void opengallry() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/’");
        startActivityForResult(intent,galaryn);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==galaryn && resultCode==RESULT_OK && data!=null){
            imageuri=data.getData();
            inputimage.setImageURI(imageuri);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void validaadata() {
        des1=des.getText().toString();
        price1=price.getText().toString();
        category11=category0.getText().toString();


        subcategory1=spinner1.getSelectedItem().toString();

       if (TextUtils.isEmpty(category11)){
            Toast.makeText(this,"Product Category is null",Toast.LENGTH_LONG).show();

        }
        else if (TextUtils.isEmpty(des1)){
            Toast.makeText(this,"Product description  is null",Toast.LENGTH_LONG).show();

        }
        else if (price1.contains("-")){
            Toast.makeText(this,"Product  price  have minus number",Toast.LENGTH_LONG).show();

        }
       else if (TextUtils.isEmpty(price1)){
           Toast.makeText(this,"Product  price  is empty",Toast.LENGTH_LONG).show();

       }
        else if (TextUtils.isEmpty(subcategory1)){
            Toast.makeText(this,"Product Sub Category  is empty",Toast.LENGTH_LONG).show();

        }


        //else if (!r1.isSelected() && ! r2.isSelected()){
            //Toast.makeText(this,"Please select ",Toast.LENGTH_LONG).show();

        //}
        else {
            storeproductinfo();
        }
    }


    private void storeproductinfo() {
        loadingBar.setTitle("adding new product");
        loadingBar.setMessage("Please wait, while we are adding new product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        Calendar calendar = Calendar.getInstance();

        java.text.SimpleDateFormat  dateFormat = new java.text.SimpleDateFormat(SimpleDateFormat.YEAR_ABBR_MONTH);
        savedata=dateFormat.format(calendar.getTime());

        java.text.SimpleDateFormat dateFormat1 = new java.text.SimpleDateFormat("HH:mm:ss a");
        savetime=dateFormat1.format(calendar.getTime());
        proudctrandomkey=savedata+savetime;

        final StorageReference filepath = storageReference.child(imageuri.getLastPathSegment()+proudctrandomkey + ". jpg");
        final UploadTask uploadTask = filepath.putFile(imageuri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(Add_suitscat.this," Error : "+message,Toast.LENGTH_LONG).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Add_suitscat.this,"  image uploaded successfully ",Toast.LENGTH_LONG).show();
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if ( !task.isSuccessful()){
                            throw  task.getException();


                        }
                        donlowadimg=filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            donlowadimg= task.getResult().toString();

                            Toast.makeText(Add_suitscat.this,"  image save to database",Toast.LENGTH_LONG).show();
                            saveprointodatabase();
                        }
                    }
                });
            }
        });


    }



    private void saveprointodatabase() {

        if (r1.isChecked()){

            radiotext=  r1.getText().toString();
        }
      else if (r2.isChecked()){

            radiotext=r2.getText().toString();
        }
        HashMap<String,Object > objectHashMap = new HashMap<>();
        objectHashMap.put("ID", proudctrandomkey);
        objectHashMap.put("data", savedata);
        objectHashMap.put("time", savetime);
        objectHashMap.put("description", des1);
        objectHashMap.put("name", category11);
        objectHashMap.put("proowner",Prevalent.currentonlienuser.getPhone());
        objectHashMap.put("price", price1);
        objectHashMap.put("image", donlowadimg);
        objectHashMap.put("category", category11);

        objectHashMap.put("type", radiotext);
        objectHashMap.put("subcategory", subcategory1);
        databaseReference.child(category11).child(subcategory1).child(proudctrandomkey).updateChildren(objectHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    loadingBar.dismiss();
                    Intent  intent = new Intent(Add_suitscat.this , AdminCategoryActivity.class);
                    startActivity(intent);


                    Toast.makeText(Add_suitscat.this," Products is added",Toast.LENGTH_LONG).show();
                }
                else {
                    loadingBar.dismiss();
                    Toast.makeText(Add_suitscat.this," error : "+ task.getException().toString(),Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}
