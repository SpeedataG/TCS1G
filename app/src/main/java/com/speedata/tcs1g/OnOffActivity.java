package com.speedata.tcs1g;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.io.IOException;

import win.reginer.gpio.GpioControl;

/**
 * ----------Dragon be here!----------/
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 *
 * @author :Reginer in  2017/7/6 2:45.
 *         联系方式:QQ:282921012
 *         功能描述:上下电
 */
public class OnOffActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private GpioControl mMainControl;
    private GpioControl mExpandControl;
    private ToggleButton mTbMain;
    private static final String TAG = "Reginer";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_off);
        initView();
    }

    private void initView() {
        mTbMain = findViewById(R.id.tb_main);
        mTbMain.setOnCheckedChangeListener(this);
        ToggleButton mTbExpand = findViewById(R.id.tb_expand);
        mTbExpand.setOnCheckedChangeListener(this);
        try {
            mMainControl = new GpioControl(GpioControl.MAIN_GPIO);
            mExpandControl = new GpioControl(GpioControl.OUT_GPIO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        boolean result = false;
        switch (compoundButton.getId()) {
            case R.id.tb_main:
                if (b) {
                    result = mMainControl.powerOnDevice(63, 128);
                } else {
                    result = mMainControl.powerOffDevice(63, 128);
                }
                break;
            case R.id.tb_expand:
                if (b) {
                    result = mExpandControl.powerOnDeviceOut(63, 128);
                } else {
                    result = mExpandControl.powerOffDeviceOut(63, 128);
                }
                break;

            default:
                break;
        }
        Log.d(TAG, "result is::: " + result);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTbMain.isChecked()) {
            mMainControl.powerOffDevice(63, 128);
        }
    }
}
