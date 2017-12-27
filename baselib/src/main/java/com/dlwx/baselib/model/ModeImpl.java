package com.dlwx.baselib.model;

import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import java.util.Iterator;
import java.util.Map;

/**
 * 数据加载
 */

public class ModeImpl implements ModeInterface {
    @Override
    public void loadData(final LoadListener loadListener, Map<String,String> map, boolean isget,String url,String cachKey) {

        if (isget) {
            GetRequest<String> stringGetRequest = OkGo.<String>get(url);


            if (TextUtils.isEmpty(cachKey)) {
                stringGetRequest.cacheMode(CacheMode.NO_CACHE);
            }else{

                stringGetRequest.cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                        .cacheKey(cachKey);

            }
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, String> next = iterator.next();
                String key = next.getKey();
                String value = next.getValue();
                stringGetRequest.params(key,value);
            }
            stringGetRequest.execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                //加载完成回调
                    loadListener.complete(response.body());
                }

                @Override
                public void onError(Response<String> response) {
                    loadListener.onError();
                }

                @Override
                public void onCacheSuccess(Response<String> response) {
                    onSuccess(response);
                }
            });

        }else{
            PostRequest<String> stringpostRequest = OkGo.<String>post(url);

            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, String> next = iterator.next();
                String key = next.getKey();
                String value = next.getValue();
                stringpostRequest.params(key,value);
            }
            stringpostRequest.execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    //加载完成回调
                    loadListener.complete(response.body());
                }

                @Override
                public void onError(Response<String> response) {
                    loadListener.onError();
                }
                @Override
                public void onCacheSuccess(Response<String> response) {
                    onSuccess(response);
                }
            });
        }
    }
}
