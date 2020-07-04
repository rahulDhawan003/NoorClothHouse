package com.rbr.noorclothhouse.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rbr.noorclothhouse.R;

import java.util.List;

public class Recycler_Adapter extends RecyclerView.Adapter<Recycler_Adapter.StockViewHolder> {

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Stock> stocklist;

    public Recycler_Adapter(Context applicationContext, List<Stock> list){
    mCtx=applicationContext;
    stocklist=list;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.card_view, null);
        return new StockViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Recycler_Adapter.StockViewHolder holder, int position) {

        //getting the product of the specified position
        Stock product = stocklist.get(position);

        //binding the data with the viewholder views from here
        holder.short_no.setText(product.getShort_code());
        holder.price.setText(product.getStock_price());
        holder.quantity.setText(product.getStock_quantity());
        holder.name.setText(product.getStock_name());

        //Setting image with handling error of invalid url
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(mCtx).load(product.getStock_img_url_()).apply(options).into(holder.stock_img);

    }

    @Override
    public int getItemCount() {
        return stocklist.size();
    }

    public class StockViewHolder extends RecyclerView.ViewHolder {

        TextView name,quantity,price,short_no;
        ImageView stock_img;
        public StockViewHolder(@NonNull View itemView) {
            super(itemView);

            //Take view from here
            name=itemView.findViewById(R.id.card_stock_name_tv);
            quantity=itemView.findViewById(R.id.card_stock_quantity_tv);
            price=itemView.findViewById(R.id.card_stock_price_tv);
            short_no=itemView.findViewById(R.id.card_short_no_tv);
            stock_img=itemView.findViewById(R.id.card_stock_img);
        }
    }
}
