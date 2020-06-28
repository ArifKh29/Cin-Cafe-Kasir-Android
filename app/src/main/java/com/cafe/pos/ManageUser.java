package com.cafe.pos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.cafe.pos.Login.MyPREFERENCES;

public class ManageUser extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<UserMdl> userMdlList;
    private UserAdapter adapter;
    UserAdapter.RecyclerViewListener listener;
    DataHelper dataHelper;
    TextView total, kembali, namaKasir;
    private int hari, bulan, tahun;
    Button btnAdd;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);
        userMdlList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.rv_user);
        dataHelper = new DataHelper(this);
        addData();
        adapter = new UserAdapter(getApplicationContext(),userMdlList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ManageUser.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        namaKasir = (TextView) findViewById(R.id.txtNamaKasir);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageUser.this,AddUser.class);
                startActivity(intent);
            }
        });
//        namaKasir.setText(sharedPreferences.getString("nama",null));
//        if(dataHelper.countCart() == 0){
//            Toast.makeText(this,"NULL",Toast.LENGTH_LONG).show();
//            TextView total0 = findViewById(R.id.txtHargaTotal);
//            total0.setText("0");
//        }
//        else{
//            final String hasilTotal = String.valueOf(dataHelper.totalCart());
//            System.out.println(hasilTotal);
//            total = findViewById(R.id.txtHargaTotal);
//            bayar = findViewById(R.id.btnBayar);
//
//
//            total.setText(hasilTotal);
//            final Dialog dialog = new Dialog(History.this);
//
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setContentView(R.layout.dialog_bayar);
//
//            bayar.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.show();
//                    kembali = dialog.findViewById(R.id.txtTtlKembali);
//                    Button btnProses = dialog.findViewById(R.id.btnProses);
//                    final TextInputEditText inputBayar = (TextInputEditText) dialog.findViewById(R.id.inputBayar);
//                    inputBayar.addTextChangedListener(new TextWatcher() {
//                        @Override
//                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                        }
//
//                        @Override
//                        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                        }
//
//                        @Override
//                        public void afterTextChanged(Editable s) {
//                            try {
//                                int input = 0;
//                                input = Integer.parseInt(String.valueOf(s));
//                                int hitung = input - Integer.parseInt(total.getText().toString());
//                                if(hitung<0){
//                                    kembali.setText("0");
//                                }
//                                else {
//                                    kembali.setText(Integer.toString(hitung));
//                                }
//                            }
//                            catch (Exception ex){
//                                kembali.setText("0");
//                            }
//
//
//                        }
//                    });
//
//                    btnProses.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            String iduser = sharedPreferences.getString("id_user", null);
//                            String kembalian = kembali.getText().toString();
//                            String bayar = inputBayar.getText().toString();
//                            Calendar calendar = Calendar.getInstance();
//                            hari = calendar.get(Calendar.DAY_OF_MONTH);
//                            bulan = calendar.get(Calendar.MONTH);
//                            tahun = calendar.get(Calendar.YEAR);
//                            String tgl = hari + "-" +bulan+ "-" + tahun;
//                            try {
//                                if(Integer.parseInt(bayar)<Integer.parseInt(hasilTotal)){
//                                    Toast.makeText(History.this,"Nominal Bayar Kurang", Toast.LENGTH_SHORT).show();
//                                }else {
//                                    try{
//                                        dataHelper.addTransaksi(iduser,tgl,hasilTotal,bayar,kembalian);
//                                        Intent home = new Intent(History.this,Home.class);
//                                        startActivity(home);
//                                        Toast.makeText(History.this,"Berhasil DiProses", Toast.LENGTH_SHORT).show();
//                                    }
//                                    catch (Exception ex){
//                                        Toast.makeText(History.this,"Masukan Jumlah Valid", Toast.LENGTH_SHORT).show();
//                                    }
//
//                                }
//                            }catch (Exception ex){
//                                Toast.makeText(History.this,"Masukan Jumlah Valid", Toast.LENGTH_SHORT).show();
//                            }
//
//
//                        }
//                    });
//                }
//            });
//
//        }


    }
    private void setOnClickListener() {
        listener = new UserAdapter.RecyclerViewListener() {
            @Override
            public void onClick(View v, final int position) {
                final String id = userMdlList.get(position).getIduser();
                final AlertDialog.Builder builder = new AlertDialog.Builder(ManageUser.this);
                builder.setTitle("Hapus User");
                builder.setMessage("Anda yakin akan menghapus user ini?");
                builder.setCancelable(false);
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataHelper.hpsUser(id);
                        Toast("User Berhasil Dihapus");
                    }
                });

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();

                //Toast.makeText(getApplicationContext(), (CharSequence) foodMdlArrayList.get(position).getNama(),Toast.LENGTH_LONG).show();
//                final Dialog dialog = new Dialog(History.this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.dialog_cart);
//                final TextView dtxtNama = (TextView) dialog.findViewById(R.id.dtxtNama);
//                final TextView dtxtHarga = (TextView) dialog.findViewById(R.id.dtxtHarga);
//                final TextView txtTtlJumlah = (TextView) dialog.findViewById(R.id.txtTtlJumlah);
//                final TextView jumlah = (TextView) dialog.findViewById(R.id.jumlah);
//                jumlah.setText(cartMdlList.get(position).getJumlah());
//                txtTtlJumlah.setText(cartMdlList.get(position).getHarga());
//                Button btnMin = (Button) dialog.findViewById(R.id.btnMin);
//                Button btnPlus = (Button) dialog.findViewById(R.id.btnPlus);
//                Button btnHapus = (Button) dialog.findViewById(R.id.btnHapus);
//                Button btnUbah = (Button) dialog.findViewById(R.id.btnUbah);
//                dtxtNama.setText(cartMdlList.get(position).getNama());
//                final String harga = cartMdlList.get(position).getHarga();
//                dtxtHarga.setText(harga);
//                final String id = cartMdlList.get(position).getId();
//                dialog.show();
//                btnMin.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int newJumlah = Integer.parseInt(jumlah.getText().toString()) - 1;
//                        int newharga = newJumlah * Integer.parseInt(harga);
//                        if (newJumlah <= 0) {
//                            txtTtlJumlah.setText("0");
//                            jumlah.setText("0");
//                        } else {
//                            txtTtlJumlah.setText(String.valueOf(newharga));
//                            jumlah.setText(Integer.toString(newJumlah));
//                        }
//                    }
//                });
//                btnPlus.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int newJumlah = Integer.parseInt(jumlah.getText().toString()) + 1;
//                        int newharga = newJumlah * Integer.parseInt(harga);
//                        txtTtlJumlah.setText(String.valueOf(newharga));
//                        jumlah.setText(Integer.toString(newJumlah));
//                        //Toast.makeText(getApplicationContext(),newJumlah,Toast.LENGTH_LONG).show();
//                    }
//                });
//                btnHapus.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String id_cart = cartMdlList.get(position).getId();
//                        dataHelper.hpsCart(id_cart);
//                        Toast.makeText(History.this, "Pesanan Dihapus"+id_cart,Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(History.this, Cart.class);
//                        startActivity(intent);
//                        finish();
//
//                    }
//                });
//                btnUbah.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String id_cart = cartMdlList.get(position).getId();
//                        String jml = jumlah.getText().toString();
//                        String subtotal = txtTtlJumlah.getText().toString();
//                        dataHelper.ubahCart(id_cart, jml,subtotal);
//                        Toast.makeText(History.this, "Pesanan Diubah"+id_cart,Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(History.this, Cart.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                });



            }
        };
    }

    void addData(){

        Cursor cursor = dataHelper.getUser();
        if (cursor.getCount()==0){
            Toast.makeText(getApplicationContext(), "NO DATA", Toast.LENGTH_LONG).show();
        }else {
            int total = 0;
            while (cursor.moveToNext()){
                UserMdl userMdl = new UserMdl();
                userMdl.setIduser(cursor.getString(0));
                userMdl.setUsername(cursor.getString(1));
                userMdl.setNama(cursor.getString(2));
                userMdl.setStatus(cursor.getString(4));
                //historyMdl.setJumlah(cursor.getString(5));

                userMdlList.add(userMdl);
            }
            setOnClickListener();
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adapter = new UserAdapter(getApplicationContext(), userMdlList, listener);
            recyclerView.setAdapter(adapter);
        }

//        cartMdlList.add(new CartMdl("Dimas Maulana", "1414370309", "123456789","aaaaa"));
//        cartMdlList.add(new CartMdl("Fadly Yonk", "1214234560", "987654321","aasasas"));
//        cartMdlList.add(new CartMdl("Ariyandi Nugraha", "1214230345", "987648765","asasas"));
//        cartMdlList.add(new CartMdl("Aham Siswana", "1214378098", "098758124","asasa"));
    }

    private void Toast(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

}
