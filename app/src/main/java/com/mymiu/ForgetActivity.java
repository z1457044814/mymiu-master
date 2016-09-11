package com.mymiu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mymiu.utils.FindPhoneTimeCount;

public class ForgetActivity extends AppCompatActivity implements TextWatcher{

    private Toolbar toolbar;
    private EditText rigster_phone;
    private EditText vct_code_edit;
    private Button get_vct_btn_1;
    private Button reset_psd_btn;
    private FindPhoneTimeCount timeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        findView();
        toolbar.setNavigationIcon(R.drawable.back_vector);
        toolbar.setTitle("重置密码");
        toolbar.setTitleTextColor(getResources().getColor(R.color.home_bar_text_push));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        reset_psd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ForgetActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
            }
        });
        rigster_phone.addTextChangedListener(this);
        vct_code_edit.addTextChangedListener(this);
        timeCount=new FindPhoneTimeCount(60000,1000);
        timeCount.setView(get_vct_btn_1);
        get_vct_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeCount.start();
            }
        });
    }

    private void findView(){
        toolbar=(Toolbar)findViewById(R.id.reset_psd_toolbar);
        rigster_phone=(EditText)findViewById(R.id.rigster_phone);
        vct_code_edit=(EditText)findViewById(R.id.vct_code_edit);
        get_vct_btn_1=(Button)findViewById(R.id.get_vct_btn_1);
        reset_psd_btn=(Button)findViewById(R.id.reset_psd_btn);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(rigster_phone.length()!=0&&vct_code_edit.length()!=0){
            reset_psd_btn.setEnabled(true);
            reset_psd_btn.setBackgroundResource(R.drawable.default_btn);
        }else {
            reset_psd_btn.setEnabled(false);
            reset_psd_btn.setBackgroundResource(R.color.colorGray);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
