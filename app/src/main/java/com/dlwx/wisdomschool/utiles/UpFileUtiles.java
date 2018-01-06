package com.dlwx.wisdomschool.utiles;

import android.content.Context;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/2/002.
 */

public class UpFileUtiles {
    private static String PicUrl = HttpUrl.UploadFile;
    private static String otherFile = "";
    public static int TYPE = 0;
    public static void start(final Context ctx, File file,String filetype,int size){
        String url = null;

            url = HttpUrl.UploadFile;

        OkGo.<String>post(url)
                .params("file",file)
                .params("type",filetype)
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
    public static void addfile(final Context ctx, Map<String,String> map){
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        PostRequest<String> post = OkGo.<String>post(HttpUrl.AddFile);
        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            post.params(next.getKey(), next.getValue());
        }
        post.execute(new StringCallback() {
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
