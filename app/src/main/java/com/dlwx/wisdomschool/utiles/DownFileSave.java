package com.dlwx.wisdomschool.utiles;

import android.content.Context;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Response;

import java.io.File;

/**
 * Created by Administrator on 2018/1/6/006.
 */

public class DownFileSave {

    public static void down(final Context ctx, String path){
        OkGo.<File>get(path)
                .execute(new FileCallback() {
                    @Override
                    public void onSuccess(Response<File> response) {
                        File body = response.body();
                        downFIle.back(body);
                    }

                    @Override
                    public void onError(Response<File> response) {
                        Toast.makeText(ctx, "网络中断，请重新加载", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private static DownFIleBack downFIle;
    public interface DownFIleBack{
        void back(File file);
    }

    public static void setDownFIleBack(DownFIleBack downFIleBack) {
       downFIle = downFIleBack;
    }
}
