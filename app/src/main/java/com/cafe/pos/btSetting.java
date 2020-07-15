package com.cafe.pos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class btSetting extends AppCompatActivity {
    public static final String NAMABT = "namabt";
    public static final String BTPREFERENCES = "btSetting" ;
    TextView txtNamabt;
    EditText btNew;
    Button simpan;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt_setting);
        txtNamabt = findViewById(R.id.txtNamaBt);
        btNew = findViewById(R.id.btNew);
        simpan = findViewById(R.id.buttonSimpan);
        sharedpreferences = getSharedPreferences(BTPREFERENCES, Context.MODE_PRIVATE);
        txtNamabt.setText(sharedpreferences.getString("namabt",null));
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(NAMABT, btNew.getText().toString());
                    editor.commit();
                    Toast.makeText(btSetting.this, "Printer sudah diperbarui", Toast.LENGTH_SHORT).show();
                    finish();
                }catch (Exception ex){
                    Toast.makeText(btSetting.this, "Cek kembali nama bluetooth", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
