package com.example.xiao.doublecolor;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private TextView blueOne,blueTwo,blueThree,blueFour,blueFive,blueSix,redOne;
    private TextView addTV,deleteTV,updateTV,selectTV;
    private Button addBT,deleteBT,updateBT,selectBT;
    private List<Integer> numberList = null;
    private List<TextView> tvList = null;
    private Button mBtRadom,mShowAlertDialog,mShowProgressDialog;
    private ProgressDialog mProgressDialog;
    private static final int CACULATE_NUM = 0;
    public static final int SHOW_PROGRESS_DIALOG = 1;
    int progress = 0;

    private MyDBDao dao;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CACULATE_NUM:
                    numberList.clear();
                    setTV();
                    break;
                case SHOW_PROGRESS_DIALOG:
                    if(progress <= 100) {
                        mProgressDialog.setProgress(progress);
                        progress++;
                        Log.i(TAG, "handleMessage: progress " + progress);
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(SHOW_PROGRESS_DIALOG),1000);
                    }
                    if (progress == 100) {
                        mProgressDialog.dismiss();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = new MyDBDao(this);
        initView();
        setTV();
    }

    private void initView() {
        blueOne = (TextView) findViewById(R.id.iv_blue_one);
        blueTwo = (TextView) findViewById(R.id.iv_blue_two);
        blueThree = (TextView) findViewById(R.id.iv_blue_three);
        blueFour = (TextView) findViewById(R.id.iv_blue_four);
        blueFive = (TextView) findViewById(R.id.iv_blue_five);
        blueSix = (TextView) findViewById(R.id.iv_blue_six);
        redOne = (TextView) findViewById(R.id.iv_red_one);
        mBtRadom = (Button) findViewById(R.id.bt_setradom);

        addBT = (Button) findViewById(R.id.add);
        addTV = (TextView) findViewById(R.id.add_tv);
        deleteBT = (Button) findViewById(R.id.delete);
        deleteTV = (TextView) findViewById(R.id.delete_tv);
        updateBT = (Button) findViewById(R.id.update);
        updateTV = (TextView) findViewById(R.id.update_tv);
        selectBT = (Button) findViewById(R.id.select);
        selectTV = (TextView) findViewById(R.id.select_tv);

        mBtRadom.setOnClickListener(this);
        mShowAlertDialog.setOnClickListener(this);
        mShowProgressDialog.setOnClickListener(this);
        numberList = new ArrayList<>();
        tvList = new ArrayList<>();
        tvList.add(blueOne);
        tvList.add(blueTwo);
        tvList.add(blueThree);
        tvList.add(blueFour);
        tvList.add(blueFive);
        tvList.add(blueSix);
    }
    private void cacuBlue(List<TextView> list) {
        Log.i(TAG, "cacuBlue: list.size " + list.size());
        for (int i = 0; i < list.size(); i++) {
                int num = (int)(33 * Math.random()+1);
                Log.i(TAG, "cacuBlue: num " + num);
            if (!numberList.contains(num)) {
                numberList.add(num);
                Log.i(TAG, "cacuBlue: numberList.size() " + numberList.size());
                Collections.sort(numberList);
            } else {
                i--;
            }
        }
    }
    private void cacuRed(TextView textView) {
        int num = (int)(16 * Math.random()+1);
        Log.i(TAG, "cacuRed: num" + num);
        textView.setText(num + "");
    }
    private void setTV() {
        cacuBlue(tvList);
        cacuRed(redOne);
        for (int i = 0; i < tvList.size(); i++) {
            if (numberList.get(i) < 10) {
                tvList.get(i).setText("0" + numberList.get(i));
            } else {
                tvList.get(i).setText(numberList.get(i) + "");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_setradom:
                mHandler.sendMessage(mHandler.obtainMessage(CACULATE_NUM));
                break;
            case R.id.add:
                addTV.setText(dao.insert("zhangsao","male") + "");
                break;
            case R.id.delete:
                addTV.setText(dao.delete("zhangsao") + "");
                break;
            case R.id.update:
                addTV.setText(dao.update("zhangsao","female") + "");
                break;
            case R.id.select:
                addTV.setText(dao.select("zhangsao") + "");
                break;
        }
    }

    private void showProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("progressDialog");
        mProgressDialog.setMessage("showProgressDialog");
        mProgressDialog.setCancelable(true);
        mProgressDialog.setMax(100);
        mProgressDialog.setCancelable(false);
        mHandler.sendMessage(mHandler.obtainMessage(SHOW_PROGRESS_DIALOG));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.show();
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("my title");
        builder.setMessage("show my dialog");
        builder.setCancelable(false);
        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "show confirm", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "show cancel", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Toast.makeText(MainActivity.this, "onDismiss", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }


}
