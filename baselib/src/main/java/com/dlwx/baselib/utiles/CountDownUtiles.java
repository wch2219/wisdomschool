package com.dlwx.baselib.utiles;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

/**
 * 60s倒计时
 */
public class CountDownUtiles {
    private TextView tv_time;
    public CountDownUtiles(TextView tv_time) {
        super();
        this.tv_time = tv_time;
    }
    public   int time =0;

    public  void startCountDown() {
        new Thread(){
            @Override
            public void run() {
                try {

                    for (int i = 60;i>=0 ;i--){

                        Thread.sleep(1000);

                        time = i;
                        handler.sendEmptyMessage(0);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();



    }
    private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        if (time ==0) {
                            tv_time.setText("重新发送");
                        }else{

                            tv_time.setText(time+"后重发");
                        }
                        break;
                }

            }
        };
}
