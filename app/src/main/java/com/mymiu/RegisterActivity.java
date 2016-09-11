package com.mymiu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mymiu.utils.FindPhoneTimeCount;

public class RegisterActivity extends AppCompatActivity implements TextWatcher{

    private Toolbar toolbar;
    private EditText rigster_phone;
    private EditText vct_code_edit;
    private Button get_vct_btn;
    private Button register_btn;
    private FindPhoneTimeCount timeCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findView();
        toolbar.setNavigationIcon(R.drawable.back_vector);
        toolbar.setTitle("注册");
        toolbar.setTitleTextColor(getResources().getColor(R.color.home_bar_text_push));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        rigster_phone.addTextChangedListener(this);
        vct_code_edit.addTextChangedListener(this);
        timeCount=new FindPhoneTimeCount(60000,1000);
        timeCount.setView(get_vct_btn);
        get_vct_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeCount.start();
            }
        });
    }

    private void findView(){
        toolbar=(Toolbar)findViewById(R.id.register_toolbar);
        rigster_phone=(EditText)findViewById(R.id.rigster_phone);
        vct_code_edit=(EditText)findViewById(R.id.vct_code_edit);
        get_vct_btn=(Button)findViewById(R.id.get_vct_btn);
        register_btn=(Button)findViewById(R.id.register_btn);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(rigster_phone.length()!=0&&vct_code_edit.length()!=0){
            register_btn.setEnabled(true);
            register_btn.setBackgroundResource(R.drawable.default_btn);
        }else {
            register_btn.setEnabled(false);
            register_btn.setBackgroundResource(R.color.colorGray);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
