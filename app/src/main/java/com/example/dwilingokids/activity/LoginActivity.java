package com.example.dwilingokids.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dwilingokids.R;
import com.example.dwilingokids.api.HTTP;
import com.example.dwilingokids.api.LocalStorage;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    TextView klikRegister;
    EditText edtEmail, edtPassword;
    Button btnLogin;
    String email, password;
    LocalStorage localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        localStorage = new LocalStorage(LoginActivity.this);

        klikRegister = findViewById(R.id.txtKlikregister);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        klikRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

                Toast.makeText(LoginActivity.this, "now in page register", Toast.LENGTH_LONG).show();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekLogin();
            }
        });
    }
    private void cekLogin() {
        email = edtEmail.getText().toString();
        password = edtPassword.getText().toString();
        if (email.isEmpty() || password.isEmpty()){
            alertFail("Email dan Password Harus di isi");
        }else{
            sendLogin();
        }
    }

    private void sendLogin() {
        //Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        //startActivity(intent);
        //finish();
        //Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();


        JSONObject params = new JSONObject();
        try {
            params.put("email", email);
            params.put("password", password);
        }catch (JSONException e){
            e.printStackTrace();
        }
        String data = params.toString();
        String url = getString(R.string.api_server)+"/login";

        new Thread(new Runnable() {
            @Override
            public void run() {
                HTTP Http = new HTTP(LoginActivity.this, url);
                Http.setMethod("post");
                Http.setData(data);
                Http.send();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Integer code = Http.getStatusCode();
                        if (code == 200) {
                            try {
                                JSONObject response = new JSONObject(Http.getResponse());
                                String token = response.getString("access_token");
                                localStorage.setToken(token);
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else if (code == 422){
                            try {
                                    JSONObject response = new JSONObject(Http.getResponse());
                                    String msg = response.getString("message");
                                    alertFail(msg);
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                        }else if (code == 401){
                                try {
                                    JSONObject response = new JSONObject(Http.getResponse());
                                    String msg = response.getString("message");
                                    alertFail(msg);
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                        }else{
                            Toast.makeText(LoginActivity.this, "Error"+code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();

    }

    private void alertFail(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Failed")
                .setIcon(R.drawable.ic_warning)
                .setMessage(s)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}