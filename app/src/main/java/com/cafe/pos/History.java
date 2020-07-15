package com.cafe.pos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.cafe.pos.Login.MyPREFERENCES;

public class History extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<HistoryMdl> cartMdlList;
    private HistoryAdapter adapter;
    HistoryAdapter.RecyclerViewListener listener;
    DataHelper dataHelper;
    TextView total, kembali, namaKasir, txtTglAwal, txtTglAkhir;
    private int hari, bulan, tahun;
    Button bayar, tglAwal, tglAkhir, buatExcel;
    SharedPreferences sharedPreferences;
    Button btnOpenDatePicker;
    Calendar myCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        buatExcel = findViewById(R.id.buatExcel);
        tglAwal = findViewById(R.id.tglAwal);
        tglAkhir = findViewById(R.id.tglAkhir);
        buatExcel = findViewById(R.id.buatExcel);
        txtTglAwal = findViewById(R.id.txtTglAwal);
        txtTglAkhir = findViewById(R.id.txtTglAkhir);
        cartMdlList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.rv_history);
        dataHelper = new DataHelper(this);
        addData();
        adapter = new HistoryAdapter(getApplicationContext(),cartMdlList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(History.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        namaKasir = (TextView) findViewById(R.id.txtNamaKasir);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        myCalendar = Calendar.getInstance();
        tglAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    new DatePickerDialog(History.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String formatTanggal = "dd-MM-yyyy";
                        SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal);
                        txtTglAwal.setText(sdf.format(myCalendar.getTime()));
                    }
                },
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        tglAkhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(History.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String formatTanggal = "dd-MM-yyyy";
                        SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal);
                        txtTglAkhir.setText(sdf.format(myCalendar.getTime()));
                    }
                },
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        buatExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tglawal = txtTglAwal.getText().toString();
                String tglakhir = txtTglAkhir.getText().toString();
                Cursor cursor = dataHelper.getExcelData(tglawal,tglakhir);
                Workbook wb = new HSSFWorkbook();
                Cell cell = null;
                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);

                Sheet sheet=null;
                sheet = wb.createSheet("Laporan Transaksi");

                Row row = sheet.createRow(0);
                cell = row.createCell(0);
                cell.setCellValue("No");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(1);
                cell.setCellValue("ID Transaksi");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(2);
                cell.setCellValue("Nama Kasir");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(3);
                cell.setCellValue("Tanggal");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4);
                cell.setCellValue("Total");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(5);
                cell.setCellValue("Bayar");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(6);
                cell.setCellValue("Kembalian");
                cell.setCellStyle(cellStyle);

                int no=1;
                while (cursor.moveToNext()){
                    Row isi = sheet.createRow(no);
                    cell = isi.createCell(0);
                    cell.setCellValue(no);
                    cell.setCellStyle(cellStyle);

                    cell = isi.createCell(1);
                    cell.setCellValue(cursor.getString(0));
                    cell.setCellStyle(cellStyle);

                    cell = isi.createCell(2);
                    cell.setCellValue(cursor.getString(1));
                    cell.setCellStyle(cellStyle);

                    cell = isi.createCell(3);
                    cell.setCellValue(cursor.getString(2));
                    cell.setCellStyle(cellStyle);

                    cell = isi.createCell(4);
                    cell.setCellValue(Integer.parseInt(cursor.getString(3)));
                    cell.setCellStyle(cellStyle);

                    cell = isi.createCell(5);
                    cell.setCellValue(Integer.parseInt(cursor.getString(4)));
                    cell.setCellStyle(cellStyle);

                    cell = isi.createCell(6);
                    cell.setCellValue(Integer.parseInt(cursor.getString(5)));
                    cell.setCellStyle(cellStyle);

                    no++;
                }
                int baru = no++;
                Row total = sheet.createRow(baru);
//                sheet.addMergedRegion(CellRangeAddress.valueOf("A"+baru+":D"+baru));
//                cell.setCellValue("GRAND TOTAL");
//                cell.setCellStyle(cellStyle);

                cell = total.createCell(4);
                cell.setCellFormula("SUM(E2:E"+(no-1)+")");
                cell.setCellStyle(cellStyle);

                cell = total.createCell(5);
                cell.setCellFormula("SUM(F2:F"+(no-1)+")");
                cell.setCellStyle(cellStyle);

                cell = total.createCell(6
                );
                cell.setCellFormula("SUM(G2:G"+(no-1)+")");
                cell.setCellStyle(cellStyle);


                File file = new File(getExternalFilesDir(null), "Laporan.xls");
                FileOutputStream outputStream = null;

                    try {
                        outputStream=new FileOutputStream(file);
                        wb.write(outputStream);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
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
//    private void setOnClickListener() {
//        listener = new CartAdapter.RecyclerViewListener() {
//            @Override
//            public void onClick(View v, final int position) {
//                //Toast.makeText(getApplicationContext(), (CharSequence) foodMdlArrayList.get(position).getNama(),Toast.LENGTH_LONG).show();
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
//
//
//
//            }
//        };
//    }

    void addData(){

        Cursor cursor = dataHelper.showTransaksi();
        if (cursor.getCount()==0){
            Toast.makeText(getApplicationContext(), "NO DATA", Toast.LENGTH_LONG).show();
        }else {
            int total = 0;
            while (cursor.moveToNext()){
                HistoryMdl historyMdl = new HistoryMdl();
                historyMdl.setIdtrx(cursor.getString(0));
                historyMdl.setNama(cursor.getString(1));
                historyMdl.setTanggal(cursor.getString(2));
                historyMdl.setJumlah(cursor.getString(3));
                //historyMdl.setJumlah(cursor.getString(5));

                cartMdlList.add(historyMdl);
            }
//            setOnClickListener();
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adapter = new HistoryAdapter(getApplicationContext(), cartMdlList, listener);
            recyclerView.setAdapter(adapter);
        }

//        cartMdlList.add(new CartMdl("Dimas Maulana", "1414370309", "123456789","aaaaa"));
//        cartMdlList.add(new CartMdl("Fadly Yonk", "1214234560", "987654321","aasasas"));
//        cartMdlList.add(new CartMdl("Ariyandi Nugraha", "1214230345", "987648765","asasas"));
//        cartMdlList.add(new CartMdl("Aham Siswana", "1214378098", "098758124","asasa"));
    }

}
