package com.speedata.tcs1g;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Button mBtnPower = (Button) findViewById(R.id.btn_power);
        Button mBtnFinger = (Button) findViewById(R.id.btn_finger);

        mBtnPower.setOnClickListener(this);
        mBtnFinger.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_power:
                startActivity(new Intent(this, OnOffActivity.class));
                break;
            case R.id.btn_finger:
                startActivity(new Intent(this, FingerActivity.class));
                break;
        }
    }
}
