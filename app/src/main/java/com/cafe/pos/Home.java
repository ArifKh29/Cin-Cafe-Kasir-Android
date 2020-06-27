package com.cafe.pos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {
    private TextView mTextMessage;
    private Toolbar toolbar;
    private CardView btnFood;
    DataHelper dataHelper;
    public static Home ma;
    protected Cursor cursor;
    String[] daftar;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Toast toast = Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_LONG);
                    toast.show();
                    return true;
                case R.id.management:
                    Intent intent = new Intent(Home.this, Management.class);
                    startActivity(intent);
                case R.id.history:
                    Toast toast3 = Toast.makeText(getApplicationContext(), "History", Toast.LENGTH_LONG);
                    toast3.show();
                    return true;
                case R.id.setting:
                   Intent i = new Intent (Home.this, Setting.class);
                   startActivity(i);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        btnFood = findViewById(R.id.btnFood);
        mTextMessage = findViewById(R.id.message);
//        dataHelper =new DataHelper(this);
//        SQLiteDatabase sqLiteDatabase = dataHelper.getWritableDatabase();
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //init();
    }

//    private void init() {
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("Welcome");
//    }

    public void foodactidity(View v) {
        Intent intent = new Intent(Home.this, Food.class);
        startActivity(intent);
    }

    public void coffeactivity(View v){
        Intent coffe = new Intent(Home.this, Coffe.class);
        startActivity(coffe);
    }

    public void RefreshList() {
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM cart", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(1).toString();
        }
        Log.d("DB", String.valueOf(daftar));

    }

    public void btnCart(View view) {
        Intent intent = new Intent(Home.this,Cart.class);
        startActivity(intent);
    }
}
