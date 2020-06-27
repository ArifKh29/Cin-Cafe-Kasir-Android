package com.cafe.pos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{
    Context context;
    LayoutInflater inflater;
    private ArrayList<FoodMdl> dataList;
    private RecyclerViewListener listener;

        public FoodAdapter(Context ctx, ArrayList<FoodMdl> dataList, RecyclerViewListener listener){
            this.inflater = LayoutInflater.from(ctx);
            this.dataList = dataList;
            this.listener = listener;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.food_list,parent,false);
//        FoodViewHolder vHolder = new FoodViewHolder(view);

        return new FoodViewHolder(view);
//        return vHolder;
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, final int position) {
        holder.txtFood.setText(dataList.get(position).getNama());
        holder.txtHarga.setText(dataList.get(position).getHarga());
//        Bitmap cek = BitmapFactory.decodeByteArray(dataList.get(position).getImg(),0,dataList.get(position).getImg().length);
//        System.out.println("asasas"+BitmapFactory.decodeByteArray(dataList.get(position).getImg(),0,dataList.get(position).getImg().length));
//        if(cek.equals(null)){
//        holder.img.setImageResource(R.drawable.def_menu);
//        }
//        if(dataList.get(position).getImg().length.  )) {
//            holder.img.setImageBitmap(BitmapFactory.decodeByteArray(dataList.get(position).getImg(),0,dataList.get(position).getImg().length ));
//        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class FoodViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtFood, txtHarga;
        //private ImageView imageFood;
        private CardView cvmain;
        private ImageView img;
        public FoodViewHolder(View itemView) {
            super(itemView);
            txtFood = (TextView) itemView.findViewById(R.id.txtFood);
            txtHarga = (TextView) itemView.findViewById(R.id.txtHarga);
            cvmain = (CardView) itemView.findViewById(R.id.cv_main);
            img = (ImageView) itemView.findViewById(R.id.imgfood);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
//            ((BtmSheetModal) context).customBottomDialog(dataList.get(getAdapterPosition()).getNama());
        }
    }

//    public class FoodViewHolder extends  RecyclerView.ViewHolder{
//        private TextView txtFood, txtHarga;
//        private CardView cvmain;
//        public FoodViewHolder(View itemView) {
//            super(itemView);
//            txtFood = (TextView) itemView.findViewById(R.id.txtFood);
//            txtHarga = (TextView) itemView.findViewById(R.id.txtHarga);
//            cvmain = (CardView) itemView.findViewById(R.id.cv_main);
//            //itemView.setOnClickListener(this);
//        }
//
////        @Override
////        public void onClick(View v) {
////            listener.onClick(v, getAdapterPosition());
//////            ((BtmSheetModal) context).customBottomDialog(dataList.get(getAdapterPosition()).getNama());
////        }
//    }

    public interface RecyclerViewListener{
        void onClick(View v, int position );
    }
}
