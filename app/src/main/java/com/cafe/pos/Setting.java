package com.cafe.pos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
    public void profil(View view) {
        Intent i = new Intent(Setting.this, Profil.class);
        startActivity(i);
    }
    public void pass(View view) {
        Intent i = new Intent(Setting.this, UbahPassword.class);
        startActivity(i);
    }
    public void logout(View view) {
        Intent i = new Intent(Setting.this, Login.class);
        startActivity(i);
    }
}

