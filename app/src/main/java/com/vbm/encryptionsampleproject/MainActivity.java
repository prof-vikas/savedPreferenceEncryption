package com.vbm.encryptionsampleproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vbm.encryptionsampleproject.helper.EncryptionHelper;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    EditText edtSpNonEn, edtSpEn;
    Button btnSpNonSaved, btnSpNonGet, btnSpSaved, btnSpGet;
    TextView txtSpNonValue, txtSpValue, txtSpEncryptedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtSpNonEn = findViewById(R.id.edt_sp_value);
        edtSpEn = findViewById(R.id.edt_sp_enc_value);

        btnSpNonSaved = findViewById(R.id.btn_sp_saved);
        btnSpNonGet = findViewById(R.id.btn_sp_get);

        btnSpSaved = findViewById(R.id.btn_sp_enc_saved);
        btnSpGet = findViewById(R.id.btn_sp_enc_get);

        txtSpNonValue = findViewById(R.id.txt_get);
        txtSpValue = findViewById(R.id.txt_get_enc);
        txtSpEncryptedValue = findViewById(R.id.txt_get_encc);

        btnSpNonSaved.setOnClickListener(view -> {
            String text = edtSpNonEn.getText().toString();
            if (text.isEmpty()) {
                Toast.makeText(MainActivity.this, "AtLeast one value is required", Toast.LENGTH_SHORT).show();
                return;
            }
            noEncryptionSaved(text);
        });

        btnSpNonGet.setOnClickListener(view -> txtSpNonValue.setText(getSpNonEncrypted()));

        btnSpSaved.setOnClickListener(view -> {
            String text = edtSpEn.getText().toString();
            if (text.isEmpty()) {
                Toast.makeText(MainActivity.this, "AtLeast one value is required", Toast.LENGTH_SHORT).show();
                return;
            }
            encryptionSaved(text);
        });


        btnSpGet.setOnClickListener(view -> {
            SampleModel sampleModel = EncryptionHelper.getDecryptedData(MainActivity.this, "userIdSPK");
            if (sampleModel != null) {
                txtSpValue.setText(sampleModel.getValue());
                txtSpEncryptedValue.setText(sampleModel.getEncryptedValue());
            }
        });
    }

    /*
     * No encryption get
     * */
    public String getSpNonEncrypted() {
        SharedPreferences sp = getSharedPreferences("WithoutEncryptionFile", MODE_PRIVATE);
        return sp.getString("tokenSPKLG", null);
    }


    /*
     * No encryption saved
     * */
    private void noEncryptionSaved(String txt) {
        SharedPreferences sp = getSharedPreferences("WithoutEncryptionFile", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("tokenSPKLG", txt).apply();
        Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
    }


    private void encryptionSaved(String text) {
        EncryptionHelper.saveEncryptedData(MainActivity.this, "userIdSPK", text);
    }
}