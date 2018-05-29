package com.yaheen.pdaapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.magicrf.uhfreaderlib.reader.Tools;
import com.magicrf.uhfreaderlib.reader.UhfReader;
import com.yaheen.pdaapp.R;
import com.yaheen.pdaapp.rfid.ScreenStateReceiver;
import com.yaheen.pdaapp.rfid.UhfReaderDevice;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RFIDBaseActivity extends BaseActivity {

    private final int msg_get_read_rfid_success = 1;

    //RFID
    public UhfReader reader; //超高频读写器
    public UhfReaderDevice readerDevice; // 读写器设备，抓哟操作读写器电源
    private ScreenStateReceiver screenReceiver;

    private Timer timer;

    private List<byte[]> epcList;
    private List<byte[]> epcTestList;
    private int membank = 3;//数据区
    private int addr = 0;//起始地址
    private int length = 5;//读取数据的长度

    private boolean canRead = false;

    /**
     * pda是否开启读取
     */
    private boolean isTurnOn = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRFID();

        timer = new Timer();
        timer.schedule(new rTask(), 100, 700);
    }

    private void initRFID() {
        reader = UhfReader.getInstance();
        readerDevice = UhfReaderDevice.getInstance();

        if (reader != null && readerDevice != null) {
            //设置功率,在16到26之间
            reader.setOutputPower(26);

            //添加广播，默认屏灭时休眠，屏亮时唤醒
            screenReceiver = new ScreenStateReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            registerReceiver(screenReceiver, filter);

            reader.getFirmware();
        }
    }

    public void readRFID() {

        if (epcList == null || epcList.size() == 0) {
            return;
        }

        String epcStr = Tools.Bytes2HexString(epcList.get(0), epcList.get(0).length);
        reader.selectEpc(Tools.HexString2Bytes(epcStr));

        byte[] accessPassword = Tools.HexString2Bytes("00000000");

        if (accessPassword.length != 4) {
            Toast.makeText(getApplicationContext(), "密码为4个字节", Toast.LENGTH_SHORT).show();
            return;
        }

        //情况EPC表，为下次读取做准备
        epcList.clear();

        //读取数据区数据
        byte[] data = reader.readFrom6C(membank, addr, length, accessPassword);
        Message message = new Message();
        message.what = msg_get_read_rfid_success;
        if (data != null && data.length > 1) {
            String dataStr = Tools.Bytes2HexString(data, data.length);
            message.obj = dataStr;
        } else {
            message.obj = "";
        }
        canRead = false;
        mHandler.sendMessage(message);
    }

    public void setCanRead(boolean canRead) {
        this.canRead = canRead;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msg_get_read_rfid_success:
                    String dataStr = (String) msg.obj;
                    setNoteBody(dataStr);
                    break;
                default:
            }
        }
    };

    class rTask extends TimerTask {

        @Override
        public void run() {
            if (!isTurnOn) {
                turnOnRFID();
            }

            if (canRead) {
                epcList = reader.inventoryRealTime();
                if (epcList != null && epcList.size() != 0) {
//                    epcList = epcTestList;
                    readRFID();
                }
            }
        }
    }

    public void turnOnRFID() {
        try {
            FileWriter localFileWriterOn = new FileWriter(new File(
                    "/proc/gpiocontrol/set_uhf"));
            localFileWriterOn.write("1");
            localFileWriterOn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            isTurnOn = true;
        }
    }

    public void turnOffRFID() {
        try {
            FileWriter localFileWriterOff = new FileWriter(new File(
                    "/proc/gpiocontrol/set_uhf"));
            localFileWriterOff.write("0");
            localFileWriterOff.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            isTurnOn = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        canRead = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        turnOffRFID();

        if (screenReceiver != null) {
            unregisterReceiver(screenReceiver);
        }

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
