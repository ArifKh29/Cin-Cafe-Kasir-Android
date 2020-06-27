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


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{
    Context context;
    LayoutInflater inflater;
    private ArrayList<HistoryMdl> dataList;
    private RecyclerViewListener listener;

    public HistoryAdapter(Context ctx, ArrayList<HistoryMdl> dataList, RecyclerViewListener listener){
        this.inflater = LayoutInflater.from(ctx);
        this.dataList = dataList;
        this.listener = listener;
    }



    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.history_list,parent,false);
//        FoodViewHolder vHolder = new FoodViewHolder(view);

        return new HistoryViewHolder(view);
//        return vHolder;
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, final int position) {
//        holder.txtFood.setText(dataList.get(position).getNama()+" "+dataList.get(position).getKet());
//        holder.txtHarga.setText(dataList.get(position).getHarga()+" x "+dataList.get(position).getJumlah()+"pcs");
//        holder.txtKet.setText(dataList.get(position).getKet());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class HistoryViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtFood, txtHarga, txtKet;
        private CardView cvmain;
        public HistoryViewHolder(View itemView) {
            super(itemView);
            txtFood = (TextView) itemView.findViewById(R.id.txtFood);
            txtHarga = (TextView) itemView.findViewById(R.id.txtHarga);
//            txtKet = (TextView) itemView.findViewById(R.id.txtKet);
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
