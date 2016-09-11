package com.mymiu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements TextWatcher{

    TextView register_btn;
    TextView forget_btn;
    EditText username;
    EditText password;
    Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register_btn=(TextView)findViewById(R.id.register_text);
        forget_btn=(TextView)findViewById(R.id.forget_text);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        login_btn=(Button)findViewById(R.id.login_button);
        if(username.getText().toString().equals("")&&password.getText().toString().equals("")){
            login_btn.setEnabled(false);
        }
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        forget_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity.this,ForgetActivity.class);
                startActivity(intent);
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"暂时无法登录",Toast.LENGTH_SHORT).show();
            }
        });
        username.addTextChangedListener(this);
        password.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(username.length()!=0&&password.length()!=0){
            login_btn.setEnabled(true);
            login_btn.setBackgroundResource(R.drawable.default_btn);
        }else {
            login_btn.setEnabled(false);
            login_btn.setBackgroundResource(R.color.colorGray);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
