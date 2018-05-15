package com.yaheen.pdaapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.yaheen.pdaapp.R;
import com.yaheen.pdaapp.util.scan.ScanUtils;

public class BaseActivity extends AppCompatActivity {

    private TextView tvContent;

    protected ScanUtils scanUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        scanUtils = new ScanUtils();
    }

    protected void setTitleContent(int content){
        tvContent = findViewById(R.id.tv_title_content);
        if(tvContent!=null){
            tvContent.setText(content);
        }
    }

}
