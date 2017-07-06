package com.speedata.tcs1g;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.text.TextUtils;
import android.util.Log;

import com.digitalpersona.uareu.Engine;
import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.ReaderCollection;
import com.digitalpersona.uareu.UareUException;
import com.digitalpersona.uareu.UareUGlobal;
import com.digitalpersona.uareu.dpfpddusbhost.DPFPDDUsbException;
import com.digitalpersona.uareu.dpfpddusbhost.DPFPDDUsbHost;

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
 * @author :Reginer in  2017/7/7 2:43.
 *         联系方式:QQ:282921012
 *         功能描述:
 */
public class Realize implements IFingerRealize, FingerInter {

    private static final String ACTION_USB_PERMISSION = "com.digitalpersona.uareu.dpfpddusbhost.USB_PERMISSION";
    private Context mContext;
    private Reader mReader;
    private ReadThread mThread;
    private int mDPI;
    private String deviceName = null;
    private Engine mEngine = null;

    private static final String TAG = "Reginer";


    public Realize(Context context) {
        mContext = context;
    }

    @Override
    public void initReader() {
        ReaderCollection readers = null;
        try {
            readers = Globals.getInstance().getReaders(mContext);
            Log.d(TAG, "initReader: 1111111111111111111111");
        } catch (UareUException e) {
            displayReaderNotFound();
            Log.d(TAG, "initReader: 2222222222222222222222");
        }
        int nSize = readers != null ? readers.size() : 0;
        if (nSize > 0) {
            deviceName = readers.get(0).GetDescription().name;
            if (!TextUtils.isEmpty(deviceName)) {
                Log.w(TAG, deviceName);
                try {
                    mReader = Globals.getInstance().getReader(deviceName, mContext);
                    PendingIntent mPermissionIntent;
                    mPermissionIntent = PendingIntent.getBroadcast(mContext, 0, new Intent(ACTION_USB_PERMISSION), 0);
                    IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
                    mContext.registerReceiver(mUsbReceiver, filter);
                    if (DPFPDDUsbHost.DPFPDDUsbCheckAndRequestPermissions(mContext, mPermissionIntent, deviceName)) {
                        openReader();
                    }
                } catch (UareUException | DPFPDDUsbException e1) {
                    displayReaderNotFound();
                }
            } else {
                displayReaderNotFound();
            }
        }
    }

    @Override
    public void startRead() {
        Log.d(TAG, "startRead: 11111111111111111111111111");
        mThread = new ReadThread(mReader, mDPI, this);
        mThread.start();
    }

    @Override
    public void openReader() {
        Log.d(TAG, "openReader: 111111111111111111111111");
        try {
            mReader = Globals.getInstance().getReader(deviceName, mContext);
            mReader.Open(Reader.Priority.EXCLUSIVE);
            mDPI = Globals.GetFirstDPI(mReader);
            mEngine = UareUGlobal.GetEngine();
            startRead();
        } catch (Exception e) {
            Log.d(TAG, "error during init of reader");
        }
    }

    @Override
    public void stopRead() {
        mThread.setContinue(false);
    }


    private void displayReaderNotFound() {
        //没上电
        Log.d(TAG, "displayReaderNotFound: 000000000000000000000000");
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            openReader();
                        }
                    } else {
                        displayReaderNotFound();
                    }
                }
            }
        }
    };

    @Override
    public void complete(Bitmap bitmap, String content) {
        Log.d(TAG, "complete: 11111111111111111111111111111");
    }
}
