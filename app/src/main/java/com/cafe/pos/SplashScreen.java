package com.cafe.pos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreen extends AppCompatActivity {
    private int waktu_loading=4000;
    DataHelper dataHelper;
    String url = "http://192.168.137.1/cinamon/getfood.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dataHelper = new DataHelper(SplashScreen.this);
//                dataHelper = new DataHelper(getApplicationContext());
                //SQLiteDatabase sqLiteDatabase = dataHelper.getWritableDatabase();
                if(dataHelper.getReadableDatabase()==null){
                    dataHelper = new DataHelper(SplashScreen.this);
                    Toast.makeText(getApplicationContext(),"Setting up complete", Toast.LENGTH_LONG).show();
                    Intent home=new Intent(SplashScreen.this, Home.class);
                    startActivity(home);
                    finish();
                }else {
                    //Toast.makeText(getApplicationContext(),"Database Sudah Ada", Toast.LENGTH_LONG).show();
//                    String sql = "INSERT INTO menu (nama_menu, harga, jenis) VALUES ('Nasi Goreng', '15000', 'food')";
//                    SQLiteDatabase insert = dataHelper.getWritableDatabase();
//                    insert.execSQL(sql);
                    int menu_count = dataHelper.getMenu();
                    dataHelper.close();
                    if(menu_count==0){
                        dataHelper.defaultMenu();
                    }
                    Intent home=new Intent(SplashScreen.this, Login.class);
                    startActivity(home);
                    finish();
                }
            }
        },waktu_loading);
    }
}
//    String sql = "INSERT INTO menu (nama_menu, harga, jenis) VALUES ('Nasi Goreng', '15000', 'food')";
//            db.execSQL(sql);