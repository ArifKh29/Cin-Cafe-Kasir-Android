package com.cafe.pos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Management extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

    }

    public void tambahMenu(View view) {
        Dialog dialog = new Dialog(Management.this);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.show();

    }

    public void tambahMakanan(View view) {
        Intent intent = new Intent(Management.this,AddMenu.class);
        startActivity(intent);
    }
    public void tambahMinuman(View view){
        Intent intent = new Intent(Management.this, TambahMinuman.class );
        startActivity(intent);
    }
}
