package com.cafe.pos;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

public class ManageDrink extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FoodAdapter adapter;
    private ArrayList<FoodMdl> foodMdlArrayList;
    private FoodAdapter.RecyclerViewListener listener;
    private RequestQueue mRequestQueue;
    DataHelper db;
    private String ID_MENU = "idmenu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_drink);
        recyclerView = (RecyclerView) findViewById(R.id.rv_drink);
        db = new DataHelper(this);
        foodMdlArrayList = new ArrayList<>();

        addData();
    }

    private void setOnClickListener() {
        listener = new FoodAdapter.RecyclerViewListener() {
            @Override
            public void onClick(View v, int position) {
                String id_menu = foodMdlArrayList.get(position).getId();
                Intent intent = new Intent(ManageDrink.this, EditDrink.class);
                intent.putExtra(ID_MENU,id_menu);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), (CharSequence) foodMdlArrayList.get(position).getNama(),Toast.LENGTH_LONG).show();
//                final Dialog dialog= new Dialog(ManageFood.this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.bottom_sheet);
//                final TextView dtxtNama = (TextView) dialog.findViewById(R.id.dtxtNama);
//                final TextView dtxtHarga = (TextView) dialog.findViewById(R.id.dtxtHarga);
//                final TextView txtTtlJumlah = (TextView) dialog.findViewById(R.id.txtTtlJumlah);
//                final TextView jumlah = (TextView) dialog.findViewById(R.id.jumlah);
//                Button btnMin = (Button) dialog.findViewById(R.id.btnMin);
//                Button btnPlus = (Button) dialog.findViewById(R.id.btnPlus);
//                Button btnCart = (Button) dialog.findViewById(R.id.btnCart);
//                dtxtNama.setText(foodMdlArrayList.get(position).getNama());
//                final String harga = foodMdlArrayList.get(position).getHarga();
//                dtxtHarga.setText(harga);
//                final String id = foodMdlArrayList.get(position).getId();
//                dialog.show();
//                btnMin.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int newJumlah = Integer.parseInt(jumlah.getText().toString())-1;
//                        int newharga = newJumlah*Integer.parseInt(harga);
//                        if(newJumlah<=0){
//                            txtTtlJumlah.setText("0");
//                            jumlah.setText("0");
//                        }else {
//                            txtTtlJumlah.setText(String.valueOf(newharga));
//                            jumlah.setText(Integer.toString(newJumlah));
//                        }
//                    }
//                });
//                btnPlus.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int newJumlah = Integer.parseInt(jumlah.getText().toString())+1;
//                        int newharga = newJumlah*Integer.parseInt(harga);
//                        txtTtlJumlah.setText(String.valueOf(newharga));
//                        jumlah.setText(Integer.toString(newJumlah));
//                        //Toast.makeText(getApplicationContext(),newJumlah,Toast.LENGTH_LONG).show();
//                    }
//                });
//                btnCart.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String idtrx = db.genID();
//                        String idmenu = id;
//                        String ket = "";
//                        String nama = dtxtNama.getText().toString();
//                        String harga = dtxtHarga.getText().toString();
//                        String dbjumlah = jumlah.getText().toString();
//                        String subtotal = txtTtlJumlah.getText().toString();
//                        Toast.makeText(getApplicationContext(),nama+" Telah Ditambahakan",Toast.LENGTH_LONG).show();
//                        db.addtoCart(idtrx, idmenu, nama, harga, ket , dbjumlah,subtotal );
//                        dialog.cancel();
//                    }
//                });


            }
        };
    }

    void addData(){
        Cursor cursor = db.showCoffe();
        if (cursor.getCount()==0){
            Toast.makeText(getApplicationContext(), "NO DATA", Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
                FoodMdl foodMdl = new FoodMdl();
                foodMdl.setId(cursor.getString(0));
                foodMdl.setNama(cursor.getString(1));
                foodMdl.setHot(cursor.getString(2));
                foodMdl.setIce(cursor.getString(3));
                foodMdl.setJenis(cursor.getString(4));
                foodMdl.setImg(cursor.getBlob(6));
                foodMdlArrayList.add(foodMdl);

            }
            setOnClickListener();
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adapter = new FoodAdapter(getApplicationContext(), foodMdlArrayList, listener);
            recyclerView.setAdapter(adapter);
        }
    }

    public void btnCart(View v){
        Intent intent = new Intent(getApplicationContext(),Cart.class);
        startActivity(intent);
    }


}
