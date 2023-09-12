package com.login.tally.login_ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.android.application.R;
import com.login.tally.login_data.DBOpenHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginUi extends AppCompatActivity {
    private EditText et_account;
    private EditText et_password;
    private CheckBox cb_memorial;
    private Button btn_login;
    private Button btn_register;
    private Button btn_forgetPassword;
    private ImageButton btn_visible;
    private ImageButton btn_visible2;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginUi.this,RegisterUi.class);
                startActivity(intent);
            }
        });

        btn_forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_visible.isShown()){
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    String str=et_password.getText().toString();
                    et_password.setSelection(str.length());
                    btn_visible.setVisibility(View.GONE);
                    btn_visible2.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_visible2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_visible2.isShown()){
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    String str=et_password.getText().toString();
                    et_password.setSelection(str.length());
                    btn_visible2.setVisibility(View.GONE);
                    btn_visible.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account=et_account.getText().toString();
                String password=et_password.getText().toString();
                Log.i("LoginUi",account);
                Log.i("LoginUi",password);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Connection conn = null;
                        String sql="select * from user";
                            conn = (Connection) DBOpenHelper.getConn();
                            try{
                                Statement st=conn.createStatement();
                                ResultSet rs=st.executeQuery(sql);
                            while(rs.next()){
                                String db_account=rs.getString(1);
                                String db_password=rs.getString(2);
                                Log.i("LoginUi","success");
                            }
                            conn.close();
                            st.close();
                            rs.close();
                            return;
                        }catch(SQLException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    void initView(){
        et_account=findViewById(R.id.et_account);

        et_password=findViewById(R.id.et_password);

        cb_memorial=findViewById(R.id.rememberPassword);

        btn_login=findViewById(R.id.login);

        btn_register=findViewById(R.id.register);

        btn_forgetPassword=findViewById(R.id.forgetPassword);

        btn_visible=findViewById(R.id.visible);

        btn_visible2=findViewById(R.id.visible2);
    }
}
