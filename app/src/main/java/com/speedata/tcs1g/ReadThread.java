package com.speedata.tcs1g;

import android.graphics.Bitmap;

import com.digitalpersona.uareu.Fid;
import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.UareUException;

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
 * @author :Reginer in  2017/7/6 7:11.
 *         联系方式:QQ:282921012
 *         功能描述:指纹读取线程
 */
public class ReadThread extends Thread {
    private boolean isContinue = true;
    private Reader mReader;
    private int mDpi;
    private FingerInter mInter;

    ReadThread(Reader reader, int dpi, FingerInter inter) {
        mReader = reader;
        mDpi = dpi;
        mInter = inter;
    }

    @Override
    public void run() {
        while (isContinue) {
            try {
                Reader.CaptureResult capResult = mReader.Capture(Fid.Format.ANSI_381_2004, Globals.DefaultImageProcessing, mDpi, -1);
                if (capResult == null || capResult.image == null)
                    continue;
                Bitmap bitmap = Globals.GetBitmapFromRaw(capResult.image.getViews()[0].getImageData(),
                        capResult.image.getViews()[0].getWidth(), capResult.image.getViews()[0].getHeight());
                String conclusionString = Globals.QualityToString(capResult);
                mInter.complete(bitmap, conclusionString);
            } catch (UareUException e) {
                e.printStackTrace();
                setContinue(false);
            }
        }
    }

    void setContinue(boolean aContinue) {
        isContinue = aContinue;
    }
}
