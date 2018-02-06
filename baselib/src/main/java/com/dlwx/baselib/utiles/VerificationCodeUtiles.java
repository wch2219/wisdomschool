package com.dlwx.baselib.utiles;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.dlwx.baselib.model.SmsAuthBean;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

/**
 * @作者 wch
 * @create at 2017/4/7/007.
 * @name
 */
public class VerificationCodeUtiles {
    private Context ctx;
    private String telepho;
    private String imgname;
    private String imgcode;
    private int isregister;
    private CountDownUtiles utiles;
    public SmsAuthBean smsAuthBean;

    public  VerificationCodeUtiles(Context ctx, String telepho, int isregister, CountDownUtiles utiles,String imgname,String imgcode) {
        super();
        this.ctx = ctx;
        this.telepho = telepho;
        this.isregister = isregister;
        this.utiles = utiles;
        this.imgname = imgname;
        this.imgcode = imgcode;
    }

    public void sendVerificationCode(String url, final MyProgressLoading loading){
        OkGo.<String>post(url)
                .params("user_phone",telepho)
                .params("send_type",isregister)
                .params("imgname",imgname)
                .params("imgcode",imgcode)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        Log.i("wch",response.body());
                        loading.dismiss();
                        Gson gson = new Gson();
                        smsAuthBean = gson.fromJson(response.body(),SmsAuthBean.class);
                        if (smsAuthBean.getCode() == 200) {
                            String code = smsAuthBean.getBody().getCode();
                            utiles.startCountDown();
                            if (smsCodeBackListener != null) {
                                smsCodeBackListener.backCode(code);
                            }
                        }
                        Toast.makeText(ctx, smsAuthBean.getResult(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        Toast.makeText(ctx, "网络连接失败", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                        super.onError(response);
                    }
                });

        }
    private SmsCodeBackListener smsCodeBackListener;
    public interface SmsCodeBackListener{
        void backCode(String code);
    }

    public void setSmsCodeBackListener(SmsCodeBackListener smsCodeBackListener) {
        this.smsCodeBackListener = smsCodeBackListener;
    }
}
