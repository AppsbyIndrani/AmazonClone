package com.example.amazonclone.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amazonclone.ItemClickListener;
import com.example.amazonclone.R;
import com.google.firebase.database.annotations.NotNull;

public class RelatedProductsHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView relatedProdName, relatedProdPrice;
    private ItemClickListener itemClickListener;
    public ImageView relatedProdImg;

    public RelatedProductsHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        relatedProdName=itemView.findViewById(R.id.relatedProdName);
        relatedProdPrice=itemView.findViewById(R.id.relatedProdPrice);
        relatedProdImg=itemView.findViewById(R.id.relatedProdImg);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View view)
    {
        itemClickListener.OnClick(view,getAdapterPosition(),false);

    }
}
