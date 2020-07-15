package com.cafe.pos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

import static com.cafe.pos.Login.MyPREFERENCES;
import static com.cafe.pos.btSetting.BTPREFERENCES;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<CartMdl> cartMdlList;
    private CartAdapter adapter;
    CartAdapter.RecyclerViewListener listener;
    DataHelper dataHelper;
    TextView total, kembali, namaKasir;
    private int hari, bulan, tahun;
    Button bayar;
//    String idtrx;
    SharedPreferences sharedPreferences, btpreferences;
    // android built in classes for bluetooth operations
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    // needed for communication to bluetooth device / network
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;

    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartMdlList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.rv_cart);
        dataHelper = new DataHelper(this);
        addData();
        final String idtrx = dataHelper.genID();
        adapter = new CartAdapter(getApplicationContext(),cartMdlList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Cart.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        namaKasir = (TextView) findViewById(R.id.txtNamaKasir);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        btpreferences = getSharedPreferences(BTPREFERENCES, Context.MODE_PRIVATE);
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
                            try {
                                int input = 0;
                                input = Integer.parseInt(String.valueOf(s));
                                int hitung = input - Integer.parseInt(total.getText().toString());
                                if(hitung<0){
                                    kembali.setText("0");
                                }
                                else {
                                    kembali.setText(Integer.toString(hitung));
                                }
                            }
                            catch (Exception ex){
                                kembali.setText("0");
                            }


                        }
                    });

                    btnProses.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String iduser = sharedPreferences.getString("id_user", null);
                            String namakasir = sharedPreferences.getString("nama", null);
                            String kembalian = kembali.getText().toString();
                            String bayar = inputBayar.getText().toString();
                            Calendar calendar = Calendar.getInstance();
                            hari = calendar.get(Calendar.DAY_OF_MONTH);
                            bulan = calendar.get(Calendar.MONTH);
                            tahun = calendar.get(Calendar.YEAR);
                            String tgl = hari + "-" +bulan+ "-" + tahun;
                            try {
                                if(Integer.parseInt(bayar)<Integer.parseInt(hasilTotal)){
                                    Toast.makeText(Cart.this,"Nominal Bayar Kurang", Toast.LENGTH_SHORT).show();
                                }else {
                                    try{
                                        dataHelper.addTransaksi(iduser,tgl,hasilTotal,bayar,kembalian);
                                        Intent home = new Intent(Cart.this,Home.class);
                                        startActivity(home);
                                            findBT();
                                            openBT();
                                            sendData(tgl,namakasir, idtrx, hasilTotal, bayar, kembalian);

                                        Toast.makeText(Cart.this,"Berhasil DiProses", Toast.LENGTH_SHORT).show();
                                    }
                                    catch (Exception ex){
                                        Toast.makeText(Cart.this,"Masukan Jumlah Valid", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }catch (Exception ex){
                                Toast.makeText(Cart.this,"Masukan Jumlah Valid", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                }
            });

        }


    }
    private void setOnClickListener() {
        listener = new CartAdapter.RecyclerViewListener() {
            @Override
            public void onClick(View v, final int position) {
                //Toast.makeText(getApplicationContext(), (CharSequence) foodMdlArrayList.get(position).getNama(),Toast.LENGTH_LONG).show();
                final Dialog dialog = new Dialog(Cart.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_cart);
                final TextView dtxtNama = (TextView) dialog.findViewById(R.id.dtxtNama);
                final TextView dtxtHarga = (TextView) dialog.findViewById(R.id.dtxtHarga);
                final TextView txtTtlJumlah = (TextView) dialog.findViewById(R.id.txtTtlJumlah);
                final TextView jumlah = (TextView) dialog.findViewById(R.id.jumlah);
                jumlah.setText(cartMdlList.get(position).getJumlah());
                txtTtlJumlah.setText(cartMdlList.get(position).getHarga());
                Button btnMin = (Button) dialog.findViewById(R.id.btnMin);
                Button btnPlus = (Button) dialog.findViewById(R.id.btnPlus);
                Button btnHapus = (Button) dialog.findViewById(R.id.btnHapus);
                Button btnUbah = (Button) dialog.findViewById(R.id.btnUbah);
                dtxtNama.setText(cartMdlList.get(position).getNama());
                final String harga = cartMdlList.get(position).getHarga();
                dtxtHarga.setText(harga);
                final String id = cartMdlList.get(position).getId();
                dialog.show();
                btnMin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int newJumlah = Integer.parseInt(jumlah.getText().toString()) - 1;
                        int newharga = newJumlah * Integer.parseInt(harga);
                        if (newJumlah <= 0) {
                            txtTtlJumlah.setText("0");
                            jumlah.setText("0");
                        } else {
                            txtTtlJumlah.setText(String.valueOf(newharga));
                            jumlah.setText(Integer.toString(newJumlah));
                        }
                    }
                });
                btnPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int newJumlah = Integer.parseInt(jumlah.getText().toString()) + 1;
                        int newharga = newJumlah * Integer.parseInt(harga);
                        txtTtlJumlah.setText(String.valueOf(newharga));
                        jumlah.setText(Integer.toString(newJumlah));
                        //Toast.makeText(getApplicationContext(),newJumlah,Toast.LENGTH_LONG).show();
                    }
                });
                btnHapus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id_cart = cartMdlList.get(position).getId();
                        dataHelper.hpsCart(id_cart);
                        Toast.makeText(Cart.this, "Pesanan Dihapus"+id_cart,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Cart.this, Cart.class);
                        startActivity(intent);
                        finish();

                    }
                });
                btnUbah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id_cart = cartMdlList.get(position).getId();
                        String jml = jumlah.getText().toString();
                        String subtotal = txtTtlJumlah.getText().toString();
                        dataHelper.ubahCart(id_cart, jml,subtotal);
                        Toast.makeText(Cart.this, "Pesanan Diubah"+id_cart,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Cart.this, Cart.class);
                        startActivity(intent);
                        finish();
                    }
                });



            }
        };
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
                cartMdl.setNama(cursor.getString(2));
                cartMdl.setHarga(cursor.getString(3));
                cartMdl.setKet(cursor.getString(4));
                cartMdl.setJumlah(cursor.getString(5));

                cartMdlList.add(cartMdl);

            }
            setOnClickListener();
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adapter = new CartAdapter(getApplicationContext(), cartMdlList, listener);
            recyclerView.setAdapter(adapter);
        }

//        cartMdlList.add(new CartMdl("Dimas Maulana", "1414370309", "123456789","aaaaa"));
//        cartMdlList.add(new CartMdl("Fadly Yonk", "1214234560", "987654321","aasasas"));
//        cartMdlList.add(new CartMdl("Ariyandi Nugraha", "1214230345", "987648765","asasas"));
//        cartMdlList.add(new CartMdl("Aham Siswana", "1214378098", "098758124","asasa"));
    }

    public void addUser(View view) {
    }

    void sendData(String tgl, String namakasir, String idtrx, String hasilTotal, String bayar, String kembalian) throws IOException {
        try {

            Cursor cursor = dataHelper.showCartStruk(idtrx);

//            System.out.println(idtrx);
//            TextView getTotal = findViewById(R.id.txtHargaTotal);

            // the text typed by the user

            String BILL = "";

            BILL =  "              CINNAR COFFE & Bakery    \n" +
                    "           Jl. Bumi Mandiri Wirokerten     \n " +
                    "                 NO 25 ABC ABCDE    \n" +
                    "                   XXXXX YYYYYY      \n" +
                    "                   MMM 590019091      \n";
            BILL = BILL + "-----------------------------------------------";
//            BILL = BILL + "\n" + "No Transaksi"+ " : ";
//            BILL = BILL + "\n" + "Tanggal     "+ " : "+tgl;
//            BILL = BILL + "\n" + "Kasir       "+ " : "+namakasir;

            BILL = BILL + "\n" + String.format("%1$-15s %2$-3s", "No Transaksi", " : ")+idtrx;
            BILL = BILL + "\n" + String.format("%1$-15s %2$-3s", "Tanggal", " : ")+tgl;
            BILL = BILL + "\n" + String.format("%1$-15s %2$-3s", "Kasir", " : ")+namakasir;
            BILL = BILL +"\n";
            BILL = BILL + "-----------------------------------------------";
            BILL = BILL +"\n";
            while (cursor.moveToNext()) {
                String nama = cursor.getString(2);
                BILL = BILL + "\n " + String.format("%1$-10s", nama);
                BILL = BILL + "\n " + String.format("%1$-5s %2$10s %3$15s %4$10s", "", cursor.getString(4), cursor.getString(5), cursor.getString(3));
            }

            BILL = BILL +"\n";
            BILL = BILL + "-----------------------------------------------";
            BILL = BILL + "\n\n";

            BILL = BILL + "                   Total  :" + "     " + hasilTotal + "\n";
            BILL = BILL + "                   Bayar  :" + "     " + bayar + "\n";
            BILL = BILL + "                   Kembali:" + "     " + kembalian + "\n";

            BILL = BILL + "-----------------------------------------------";
            BILL = BILL +"\n";
            BILL = BILL + "\n\n ";

            mmOutputStream.write(BILL.getBytes());
            System.out.println(BILL);

            // tell the user data were sent
            Toast.makeText(Cart.this, "Cetak sedang diproses", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void findBT() {

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if(mBluetoothAdapter == null) {
                Toast.makeText(Cart.this,"No bluetooth adapter available",Toast.LENGTH_SHORT).show();
//                myLabel.setText("No bluetooth adapter available");
            }

            if(!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if(pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {

                    // RPP300 is the name of the bluetooth printer device
                    // we got this name from the list of paired devices
                    if (device.getName().equals(btpreferences.getString("namabt",null))) {
                        mmDevice = device;
                        break;
                    }
                }
            }

            Toast.makeText(Cart.this, "Bluetoot ditemukan", Toast.LENGTH_SHORT).show();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // tries to open a connection to the bluetooth printer device
    void openBT() throws IOException {
        try {

            // Standard SerialPortService ID
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            beginListenForData();

            Toast.makeText(Cart.this, "Bluettoth Terbuka", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * after opening a connection to bluetooth printer device,
     * we have to listen and check if a data were sent to be printed.
     */
    void beginListenForData() {
        try {
            final Handler handler = new Handler();

            // this is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                        try {

                            int bytesAvailable = mmInputStream.available();

                            if (bytesAvailable > 0) {

                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);

                                for (int i = 0; i < bytesAvailable; i++) {

                                    byte b = packetBytes[i];
                                    if (b == delimiter) {

                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length
                                        );

                                        // specify US-ASCII encoding
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        // tell the user data were sent to bluetooth printer device
                                        handler.post(new Runnable() {
                                            public void run() {
                                                Toast.makeText(Cart.this, data, Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
