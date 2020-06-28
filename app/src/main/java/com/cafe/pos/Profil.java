package com.cafe.pos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.cafe.pos.Login.MyPREFERENCES;

public class Profil extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText tUsername, tNama, tStatus;
    Button button;
    DataHelper dataHelper;
    public static final String NAMA_USER = "nama";
    public static final String USERNAME = "username";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        tUsername = (EditText) findViewById(R.id.username);
        tNama = (EditText) findViewById(R.id.nama);
        tStatus = (EditText) findViewById(R.id.status);
        button = (Button) findViewById(R.id.button);
        tUsername.setText(sharedPreferences.getString("username", null));
        tNama.setText(sharedPreferences.getString("nama",null));
        tStatus.setText(sharedPreferences.getString("status", null));
        tUsername.setEnabled(false);
        tNama.setEnabled(false);
        dataHelper = new DataHelper(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getBtn = button.getText().toString();
                if(getBtn.equals("UBAH PROFIL")){
                    String user = tUsername.getText().toString();
                    Toast(user);
                    tUsername.setEnabled(true);
                    tNama.setEnabled(true);
                    button.setText("SIMPAN");
                }else{
                    String user = tUsername.getText().toString();
//                    Toast(user);
                    String nama = tNama.getText().toString();
                    String id = sharedPreferences.getString("id_user", null);
                    boolean updateprofil = dataHelper.updateProfil(id, user, nama);
                    if(updateprofil){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(USERNAME, user);
                        editor.putString(NAMA_USER, nama);
                        editor.commit();
                        Toast("Perubahan Data Berhasil");
                        tUsername.setEnabled(false);
                        tNama.setEnabled(false);
                        button.setText("UBAH PROFIL");
                    }
                    else {
                        Toast("Data Gagal Dirubah");
                    }

                }
            }
        });

    }
    private void Toast(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
}
