package com.example.hussein.farhetkm.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hussein.farhetkm.ItemonClick;
import com.example.hussein.farhetkm.R;

public class CartViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView tproname , tproqun  , tproprice,proowner,im;
public  ImageView image;
    ItemonClick itemonClick;

    public CartViewHolder(View itemView) {
        super(itemView);
        tproname=itemView.findViewById(R.id.proudctname);
        tproqun=itemView.findViewById(R.id.productqu);
        tproprice=itemView.findViewById(R.id.productprice);
        proowner=itemView.findViewById(R.id.prodowner0);
    image=itemView.findViewById(R.id.proimeg0);

    }

    @Override
    public void onClick(View view) {
        itemonClick.onClick(view, getAdapterPosition(),false);

    }
    public void setonclick (ItemonClick itemonClick){
        this.itemonClick=itemonClick;

    }
}
