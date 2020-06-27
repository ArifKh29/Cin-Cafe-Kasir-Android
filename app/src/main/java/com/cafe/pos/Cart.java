package com.cafe.pos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

import static com.cafe.pos.Login.MyPREFERENCES;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<CartMdl> cartMdlList;
    private CartAdapter adapter;
    CartAdapter.RecyclerViewListener listener;
    DataHelper dataHelper;
    TextView total, kembali, namaKasir;
    private int hari, bulan, tahun;
    //private TextInputEditText inputBayar;
    Button bayar;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartMdlList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.rv_cart);
        dataHelper = new DataHelper(this);
        addData();
        adapter = new CartAdapter(getApplicationContext(),cartMdlList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Cart.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        namaKasir = (TextView) findViewById(R.id.txtNamaKasir);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        namaKasir.setText(sharedPreferences.getString("nama",null));
        if(dataHelper.countCart() == 0){
            Toast.makeText(this,"NULL",Toast.LENGTH_LONG).show();
            TextView total0 = findViewById(R.id.txtHargaTotal);
            total0.setText("0");
        }
        else{
            final String hasilTotal = String.valueOf(dataHelper.totalCart());
            System.out.println(hasilTotal);
            total = findViewById(R.id.txtHargaTotal);
            bayar = findViewById(R.id.btnBayar);


            total.setText(hasilTotal);
            final Dialog dialog = new Dialog(Cart.this);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_bayar);

            bayar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.show();
                    kembali = dialog.findViewById(R.id.txtTtlKembali);
                    Button btnProses = dialog.findViewById(R.id.btnProses);
                    final TextInputEditText inputBayar = (TextInputEditText) dialog.findViewById(R.id.inputBayar);
                    inputBayar.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if(bayar.getText().equals("")){
                                kembali.setText("0");
                            }
                            else {

                                int hitung = Integer.parseInt(String.valueOf(s)) - Integer.parseInt(total.getText().toString());
                                if(hitung<0){
                                    kembali.setText("0");
                                }
                                else {
                                    kembali.setText(Integer.toString(hitung));
                                }
                            }

                        }
                    });

                    btnProses.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String iduser = "1";
                            String kembalian = kembali.getText().toString();
                            String bayar = inputBayar.getText().toString();
                            Calendar calendar = Calendar.getInstance();
                            hari = calendar.get(Calendar.DAY_OF_MONTH);
                            bulan = calendar.get(Calendar.MONTH);
                            tahun = calendar.get(Calendar.YEAR);
                            String tgl = hari + "-" +bulan+ "-" + tahun;
                            dataHelper.addTransaksi(iduser,tgl,hasilTotal,bayar,kembalian);
                        }
                    });
                }
            });
        }


    }

    void addData(){

        Cursor cursor = dataHelper.showCart();
        if (cursor.getCount()==0){
            Toast.makeText(getApplicationContext(), "NO DATA", Toast.LENGTH_LONG).show();
        }else {
            int total = 0;
            while (cursor.moveToNext()){
                CartMdl cartMdl = new CartMdl();
                cartMdl.setId(cursor.getString(0));
                cartMdl.setNama(cursor.getString(1));
                cartMdl.setHarga(cursor.getString(2));
                cartMdl.setJenis(cursor.getString(3));

                cartMdlList.add(cartMdl);

            }
            //setOnClickListener();
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adapter = new CartAdapter(getApplicationContext(), cartMdlList, listener);
            recyclerView.setAdapter(adapter);
        }

//        cartMdlList.add(new CartMdl("Dimas Maulana", "1414370309", "123456789","aaaaa"));
//        cartMdlList.add(new CartMdl("Fadly Yonk", "1214234560", "987654321","aasasas"));
//        cartMdlList.add(new CartMdl("Ariyandi Nugraha", "1214230345", "987648765","asasas"));
//        cartMdlList.add(new CartMdl("Aham Siswana", "1214378098", "098758124","asasa"));
    }

}
