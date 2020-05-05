package com.example.hussein.farhetkm.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hussein.farhetkm.ItemonClick;
import com.example.hussein.farhetkm.R;

public class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
 public TextView proname , prodesc  , proprice ,protype,proowner ;
     public ImageView proimg ;
    ItemonClick itemonClick;
    public ProductHolder(View itemView) {
        super(itemView);
        proimg = itemView.findViewById(R.id.proimg);
        proname=itemView.findViewById(R.id.proname1);
        prodesc=itemView.findViewById(R.id.prodesc);
        proprice=itemView.findViewById(R.id.proprice1);
        protype=itemView.findViewById(R.id.protype);
        proowner=itemView.findViewById(R.id.proowner);
    }
public void setonclick (ItemonClick itemonClick){
this.itemonClick=itemonClick;

}
    @Override
    public void onClick(View view) {
itemonClick.onClick(view, getAdapterPosition(),false);
    }
}
