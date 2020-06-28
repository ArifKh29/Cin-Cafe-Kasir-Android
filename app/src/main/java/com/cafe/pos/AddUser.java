package com.cafe.pos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddUser extends AppCompatActivity {
    EditText tUsername, tNama, tPassword;
    Button btnTbh;
    DataHelper dataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        tUsername = (EditText) findViewById(R.id.username);
        tNama = findViewById(R.id.nama);
        tPassword = findViewById(R.id.tPassword);
        btnTbh = findViewById(R.id.button);
        dataHelper = new DataHelper(this);
        btnTbh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = tNama.getText().toString();
                String username = tUsername.getText().toString();
                String pass = tPassword.getText().toString();
                dataHelper.addUser(nama,username,pass);
                Intent intent = new Intent(AddUser.this, ManageUser.class);
                startActivity(intent);
                finish();
                Toast.makeText(AddUser.this,"Tambah User Berhasil", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
