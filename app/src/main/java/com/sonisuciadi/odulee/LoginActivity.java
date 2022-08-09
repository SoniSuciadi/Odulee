package com.sonisuciadi.odulee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sonisuciadi.odulee.Model.Users;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    List<Users> listUser=new ArrayList<>();
    EditText etUsername, etPassword;
    Button btnLogin, btnReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dummyUser();
        btnLogin=findViewById(R.id.btn_login);
        btnReset=findViewById(R.id.btn_reset);
        etUsername=findViewById(R.id.et_username);
        etPassword=findViewById(R.id.et_password);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUsername.setText("");
                etPassword.setText("");
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checkLogin=false;
                for (int i=0; i<listUser.size();i++){
                    Users users=listUser.get(i);
                    if (users.getNama().equals(etUsername.getText().toString())
                            && users.getPassword().equals(etPassword.getText().toString())){
                        checkLogin=true;
                        break;
                    }
                }
                if (checkLogin==true){
                    Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("username",etUsername.getText());
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "LOGIN BERHASIL YEEEE !!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "Username atau Password Salah :(", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void dummyUser(){
        Users users=new Users();
        users.setId(1);
        users.setNama("Soni Suciadi");
        users.setAlamat("11 Ulu");
        users.setPassword("1");
        listUser.add(users);

        users=new Users();
        users.setId(2);
        users.setNama("Juan");
        users.setAlamat("");
        users.setPassword("2");
        listUser.add(users);

        users=new Users();
        users.setId(3);
        users.setNama("Jesicca");
        users.setAlamat("");
        users.setPassword("3");
        listUser.add(users);

        users=new Users();
        users.setId(4);
        users.setNama("Ricky");
        users.setAlamat("");
        users.setPassword("4");
        listUser.add(users);
    }
}