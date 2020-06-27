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

public class TambahMinuman extends AppCompatActivity {
    final int REQUEST_CODE_GALERY = 999;
    Button btnChoose, btnAdd1;
    EditText namaMinuman, hargaMinuman1, hargaMinuman2;
    DataHelper dataHelper;
    ImageView myImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_minuman);
        dataHelper = new DataHelper(this);
        namaMinuman = (EditText) findViewById(R.id.inNamaMinuman);
        hargaMinuman1 = (EditText) findViewById(R.id.inHarga1);
        hargaMinuman2 = (EditText) findViewById(R.id.inHarga2);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnAdd1 = (Button) findViewById(R.id.btnTmbhMinum);
        myImg = (ImageView) findViewById(R.id.myImg);
        btnChoose.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(
                                TambahMinuman.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_CODE_GALERY
                        );
                    }
                });
        btnAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaminum = namaMinuman.getText().toString();
                String hargahot = hargaMinuman1.getText().toString();
                String hargaice = hargaMinuman2.getText().toString();
                byte[] image = imageViewTobyte(myImg);
                addData(namaminum, hargahot,hargaice, image);
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
                myImg.setImageBitmap(bitmap.createScaledBitmap(bitmap, 150, 150, true));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void addData(String namaminum, String hargahot, String hargaice, byte[] image) {
        boolean insertData = dataHelper.addMinum(namaminum,hargahot,hargaice,image);
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

