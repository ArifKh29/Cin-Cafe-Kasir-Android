package com.cafe.pos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.cafe.pos.Login.MyPREFERENCES;

public class UbahPassword extends AppCompatActivity {
    EditText pass, konf;
    Button simpan;
    DataHelper dataHelper;
    SharedPreferences sharedPreferences;
    public static final String PASSWORD = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        pass = findViewById(R.id.passnew);
        konf = findViewById(R.id.konfirm);
        simpan = findViewById(R.id.buttonSimpan);
        dataHelper = new DataHelper(this);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwd = pass.getText().toString();
//                Toast("passwd"+passwd);
                String konfPass = konf.getText().toString();
//                Toast("konf"+konfPass);
                if (!passwd.equals(konfPass)){
                    Toast("Password tidak sama");
                }else {
                    String id = sharedPreferences.getString("id_user", null);
                    boolean update = dataHelper.updatePass(id, passwd);
                    if(update){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(PASSWORD, passwd);
                        editor.commit();
                        Toast("Perubahan Data Berhasil");

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
