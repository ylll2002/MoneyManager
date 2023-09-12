package com.login.tally.login_ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.android.application.R;

public class RegisterUi extends AppCompatActivity {
    private EditText et_account;
    private EditText et_authentication;
    private EditText et_password;
    private EditText et_password2;
    private Button btn_send;
    private Button btn_register;
    private Button btn_login;
    private Button btn_protocol;
    private ImageButton btn_invisible1;
    private ImageButton btn_visible1;
    private ImageButton btn_invisible2;
    private ImageButton btn_visible2;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterUi.this,LoginUi.class);
                startActivity(intent);
            }
        });

        btn_protocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_invisible1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_invisible1.isShown()){
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    String str=et_password.getText().toString();
                    et_password.setSelection(str.length());
                    btn_invisible1.setVisibility(View.GONE);
                    btn_visible1.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_visible1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_visible1.isShown()){
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    String str=et_password.getText().toString();
                    et_password.setSelection(str.length());
                    btn_visible1.setVisibility(View.GONE);
                    btn_invisible1.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_invisible2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_invisible2.isShown()){
                    et_password2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    String str=et_password2.getText().toString();
                    et_password2.setSelection(str.length());
                    btn_invisible2.setVisibility(View.GONE);
                    btn_visible2.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_visible2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_visible2.isShown()){
                    et_password2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    String str=et_password2.getText().toString();
                    et_password2.setSelection(str.length());
                    btn_visible2.setVisibility(View.GONE);
                    btn_invisible2.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    void initView(){
        et_account=findViewById(R.id.et_account);

        et_authentication=findViewById(R.id.et_authentication);

        et_password=findViewById(R.id.et_password);

        et_password2=findViewById(R.id.et_password2);

        btn_send=findViewById(R.id.send);

        btn_register=findViewById(R.id.register);

        btn_login=findViewById(R.id.btn_login);

        btn_protocol=findViewById(R.id.btn_protocol);

        btn_invisible1=findViewById(R.id.invisible1);

        btn_visible1=findViewById(R.id.visible1);

        btn_invisible2=findViewById(R.id.invisible2);

        btn_visible2=findViewById(R.id.visible2);
    }
}
