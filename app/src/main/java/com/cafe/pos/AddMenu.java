package com.cafe.pos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddMenu extends AppCompatActivity {
    final int REQUEST_CODE_GALERY = 999;
    Button btnChoose, btnAdd, dropdownmenu;

    EditText namaMenu, hargaMenu;
    DataHelper dataHelper;
    ImageView myImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);
        dataHelper = new DataHelper(this);
        namaMenu = (EditText) findViewById(R.id.inNamaMenu);
        hargaMenu = (EditText) findViewById(R.id.inHarga);
        dropdownmenu = findViewById(R.id.dropdown_menu);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnAdd = (Button) findViewById(R.id.btnTmbhMenu);
        myImg = (ImageView) findViewById(R.id.myImg);
        btnChoose.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(
                                AddMenu.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_CODE_GALERY
                        );
                    }
                });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = namaMenu.getText().toString();
                String harga = hargaMenu.getText().toString();
                byte[] image = imageViewTobyte(myImg);
                addData(nama, harga, image);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permission,@NonNull int[] grantResult){
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
                myImg.setImageBitmap(bitmap.createScaledBitmap(bitmap, 150, 150, true));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void addData(String nama, String harga, byte[] image) {
        boolean insertData = dataHelper.addMenu(nama,harga,image);
        if(insertData){
            Toast("Data Masuk");
        }
        else {
            Toast("Data Gagal");
        }
    }
    private void Toast(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

}
