package com.cafe.pos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.spec.ECField;

public class EditFood extends AppCompatActivity {
    private String id_food;
    private String ID_MENU = "idmenu";
    private EditText namaMenu, hargaMenu;
    private ImageView imageView;
    Button btnChoose, btnTmbhMenu;
    DataHelper dataHelper;
    Spinner spinner;
    final int REQUEST_CODE_GALERY = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);
        namaMenu = (EditText) findViewById(R.id.inNamaMenu);
        hargaMenu = (EditText) findViewById(R.id.inHarga);
        imageView = (ImageView) findViewById(R.id.myImg);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnTmbhMenu = (Button) findViewById(R.id.btnTmbhMenu);
        spinner = (Spinner) findViewById(R.id.listFood);
        Bundle extra = getIntent().getExtras();
        id_food = extra.getString(ID_MENU);
        Toast.makeText(EditFood.this,id_food,Toast.LENGTH_SHORT).show();
        dataHelper = new DataHelper(this);
        Cursor cursor = dataHelper.getfood(id_food);
        while (cursor.moveToNext()){
            namaMenu.setText(cursor.getString(1));
            hargaMenu.setText(cursor.getString(2));
            byte[] image = cursor.getBlob(5);
            try {
                Bitmap bmp= BitmapFactory.decodeByteArray(image, 0 , image.length);
                imageView.setImageBitmap(bmp);
            }catch (Exception ex){
                imageView.setImageResource(R.drawable.def_menu);
            }
        }
        btnChoose.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(
                                EditFood.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_CODE_GALERY
                        );
                    }
                });

        btnTmbhMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jenis = String.valueOf(spinner.getSelectedItem());
//                Toast(jenis);
                if(jenis.equals("Pilih Tipe Makanan")){
                    Toast.makeText(EditFood.this,"Pilih Tipe Makanan", Toast.LENGTH_SHORT).show();
                }else {
                    String nama = namaMenu.getText().toString();
                    String harga = hargaMenu.getText().toString();
                    byte[] image = imageViewTobyte(imageView);
                    boolean update = dataHelper.updateMenu(id_food, nama, harga, image);
                    if(update){
                        Toast("Data Masuk");
                    }
                    else {
                        Toast("Data Gagal");
                    }
                }

//                try {
//                    updateData(id_food, nama, harga, image);
//                    Toast("Update Berhasil");
//                }catch (Exception ex){
//                    Toast("Update Gagal");
//                }

            }
        });

    }
    public byte[] imageViewTobyte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permission, @NonNull int[] grantResult){
        if(requestCode == REQUEST_CODE_GALERY){
            if(grantResult.length>0 && grantResult[0]== PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GALERY);
            }
            else {
                Toast("Akses tidak diberikan");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode,permission,grantResult);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_GALERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap.createScaledBitmap(bitmap, 150, 150, true));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void Toast(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

//    public void updateData(String id_food, String nama, String harga, byte[] image){
//        dataHelper.updateMenu(id_food, nama,harga,image);
//    }
}
