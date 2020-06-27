package com.cafe.pos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextPaint;
import android.widget.Toast;

public class EditFood extends AppCompatActivity {
    private String id_cart;
    private String ID_MENU = "idmenu";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);

        Bundle extra = getIntent().getExtras();
        id_cart = extra.getString(ID_MENU);
        Toast.makeText(EditFood.this,id_cart,Toast.LENGTH_SHORT).show();
    }
}
