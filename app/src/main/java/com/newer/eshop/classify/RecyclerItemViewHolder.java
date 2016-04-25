package com.newer.eshop.classify;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.newer.eshop.R;

public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

    public ImageView img;
    public TextView name;
    public TextView sell;
    public TextView price;

    public RecyclerItemViewHolder(View itemView, ImageView img, TextView name, TextView sell, TextView price) {
        super(itemView);
        this.img = img;
        this.name = name;
        this.sell = sell;
        this.price = price;
    }


    public static RecyclerItemViewHolder newInstance(View parent) {
        ImageView img = (ImageView) parent.findViewById(R.id.classifyreuslt_grid_item_img);
        TextView name = (TextView) parent.findViewById(R.id.classifyreuslt_grid_item_name);
        TextView sell = (TextView) parent.findViewById(R.id.classifyreuslt_grid_item_sell);
        TextView price = (TextView) parent.findViewById(R.id.classifyreuslt_grid_item_price);
        return new RecyclerItemViewHolder(parent, img, name, sell, price);
    }


}
