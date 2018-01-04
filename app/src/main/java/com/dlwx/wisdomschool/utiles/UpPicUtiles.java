package com.dlwx.wisdomschool.utiles;

import android.content.Context;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.File;

/**
 * Created by Administrator on 2018/1/2/002.
 */

public class UpPicUtiles {
    private static String PicUrl = HttpUrl.UploadFile;
    private static String VideoFile = "";
    public static void start(final Context ctx, File file){
        String picid = null;
        OkGo.<String>post(HttpUrl.UploadFile)
                .params("file",file)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        backInterface.success(response);
                    }

                    @Override
                    public void onError(Response<String> response) {

                        Toast.makeText(ctx, "网络连接失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private static BackInterface backInterface;
    public interface BackInterface{
        void success(Response<String> response);
//        void onError();
    }
    public static void  setBackInterface(BackInterface backInter){
        backInterface = backInter;
    }
}
