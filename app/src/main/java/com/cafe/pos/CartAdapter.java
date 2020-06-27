package com.cafe.pos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{
    Context context;
    LayoutInflater inflater;
    private ArrayList<CartMdl> dataList;
    private RecyclerViewListener listener;

    public CartAdapter(Context ctx, ArrayList<CartMdl> dataList, RecyclerViewListener listener){
        this.inflater = LayoutInflater.from(ctx);
        this.dataList = dataList;
        this.listener = listener;
    }



    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.cart_list,parent,false);
//        FoodViewHolder vHolder = new FoodViewHolder(view);

        return new CartViewHolder(view);
//        return vHolder;
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, final int position) {
        holder.txtFood.setText(dataList.get(position).getNama());
        holder.txtHarga.setText(dataList.get(position).getHarga());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class CartViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtFood, txtHarga;
        private CardView cvmain;
        public CartViewHolder(View itemView) {
            super(itemView);
            txtFood = (TextView) itemView.findViewById(R.id.txtFood);
            txtHarga = (TextView) itemView.findViewById(R.id.txtHarga);
            cvmain = (CardView) itemView.findViewById(R.id.cv_cart);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
//            ((BtmSheetModal) context).customBottomDialog(dataList.get(getAdapterPosition()).getNama());
        }
    }


    public interface RecyclerViewListener{
        void onClick(View v, int position );
    }
}
