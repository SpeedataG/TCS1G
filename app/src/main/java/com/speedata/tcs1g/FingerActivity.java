package com.speedata.tcs1g;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

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
 * @author :Reginer in  2017/7/6 6:32.
 *         联系方式:QQ:282921012
 *         功能描述:指纹界面
 */
public class FingerActivity extends AppCompatActivity  {
    private GpioControl mMainControl;
    private ImageView mImgFinger;
    private static final String TAG = "Reginer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger);
        initView();
        initControl();
        initRealize();
    }

    private void initRealize() {
        Realize mRealize = new Realize(this);
        mRealize.initReader();
    }


    /**
     * 初始化gpio控制器.
     */
    private void initControl() {
        try {
            mMainControl = new GpioControl(GpioControl.MAIN_GPIO);
            mMainControl.powerOnDevice(63, 128);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainControl.powerOffDevice(63, 128);
    }
    private void initView() {
        mImgFinger = (ImageView) findViewById(R.id.img_finger);
    }

}
