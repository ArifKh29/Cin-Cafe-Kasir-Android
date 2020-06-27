package com.cafe.pos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {
    DataHelper dataHelper;
    public static final String ID = "id_user";
    public static final String NAMA_USER = "nama";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String STATUS = "status";
    public static final String KET = "loggedin";
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dataHelper = new DataHelper(this);
        final Button btnLogin = findViewById(R.id.btnLogin);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        checkSession();
        final TextInputEditText txtUsername = findViewById(R.id.txtUsername);
        final TextInputEditText txtPassword = findViewById(R.id.txtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                User currentUser = dataHelper.Authenticate(new User(null,username,null,password,null));
                if(currentUser != null){
                    Cursor userdata = dataHelper.getUserdata(username);
//                    String id;
                    while (userdata.moveToNext()){
                        String id = userdata.getString(0);
                        String userName = userdata.getString(1);
                        String nama = userdata.getString(2);
                        String pass = userdata.getString(3);
                        String status = userdata.getString(4);

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(ID, id);
                        editor.putString(USERNAME, userName);
                        editor.putString(NAMA_USER, nama);
                        editor.putString(PASSWORD, pass);
                        editor.putString(STATUS, status);
                        editor.commit();
                    }

                    Intent intent = new Intent(Login.this, Home.class);
                    startActivity(intent);
                    finish();
                }
                else {

                    //User Logged in Failed
                    Snackbar.make(btnLogin, "Failed to log in , please try again", Snackbar.LENGTH_LONG).show();
//                Intent intent = new Intent(Login.this, Home.class);
//                startActivity(intent);
                }
            }
        });
    }



}
