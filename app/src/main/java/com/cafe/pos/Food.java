package com.cafe.pos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Food extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FoodAdapter adapter;
    private ArrayList<FoodMdl> foodMdlArrayList;
    private FoodAdapter.RecyclerViewListener listener;
    private RequestQueue mRequestQueue;
    DataHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        recyclerView = (RecyclerView) findViewById(R.id.rv_food);
        db = new DataHelper(this);
        foodMdlArrayList = new ArrayList<>();

        addData();
    }

    private void setOnClickListener() {
        listener = new FoodAdapter.RecyclerViewListener() {
            @Override
            public void onClick(View v, int position) {
                //Toast.makeText(getApplicationContext(), (CharSequence) foodMdlArrayList.get(position).getNama(),Toast.LENGTH_LONG).show();
                final Dialog dialog= new Dialog(Food.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.bottom_sheet);
                final TextView dtxtNama = (TextView) dialog.findViewById(R.id.dtxtNama);
                final TextView dtxtHarga = (TextView) dialog.findViewById(R.id.dtxtHarga);
                final TextView txtTtlJumlah = (TextView) dialog.findViewById(R.id.txtTtlJumlah);
                final TextView jumlah = (TextView) dialog.findViewById(R.id.jumlah);
                Button btnMin = (Button) dialog.findViewById(R.id.btnMin);
                Button btnPlus = (Button) dialog.findViewById(R.id.btnPlus);
                Button btnCart = (Button) dialog.findViewById(R.id.btnCart);
                dtxtNama.setText(foodMdlArrayList.get(position).getNama());
                final String harga = foodMdlArrayList.get(position).getHarga();
                dtxtHarga.setText(harga);
                final String id = foodMdlArrayList.get(position).getId();
                dialog.show();
                btnMin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int newJumlah = Integer.parseInt(jumlah.getText().toString())-1;
                        int newharga = newJumlah*Integer.parseInt(harga);
                        if(newJumlah<=0){
                            txtTtlJumlah.setText("0");
                            jumlah.setText("0");
                        }else {
                            txtTtlJumlah.setText(String.valueOf(newharga));
                            jumlah.setText(Integer.toString(newJumlah));
                        }
                    }
                });
                btnPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int newJumlah = Integer.parseInt(jumlah.getText().toString())+1;
                        int newharga = newJumlah*Integer.parseInt(harga);
                        txtTtlJumlah.setText(String.valueOf(newharga));
                        jumlah.setText(Integer.toString(newJumlah));
                        //Toast.makeText(getApplicationContext(),newJumlah,Toast.LENGTH_LONG).show();
                    }
                });
                btnCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String idtrx = db.genID();
                        String idmenu = id;
                        String ket = "";
                        String nama = dtxtNama.getText().toString();
                        String harga = dtxtHarga.getText().toString();
                        String dbjumlah = jumlah.getText().toString();
                        String subtotal = txtTtlJumlah.getText().toString();
                        Toast.makeText(getApplicationContext(),nama+" Telah Ditambahakan",Toast.LENGTH_LONG).show();
                        db.addtoCart(idtrx, idmenu, nama, harga, ket , dbjumlah,subtotal );
                        dialog.cancel();
                    }
                });


            }
        };
    }

    void addData(){
        Cursor cursor = db.showMenu();
        if (cursor.getCount()==0){
            Toast.makeText(getApplicationContext(), "NO DATA", Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
                FoodMdl foodMdl = new FoodMdl();
                foodMdl.setId(cursor.getString(0));
                foodMdl.setNama(cursor.getString(1));
                foodMdl.setHarga(cursor.getString(2));
                foodMdl.setJenis(cursor.getString(3));
                foodMdl.setImg(cursor.getBlob(5));
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
