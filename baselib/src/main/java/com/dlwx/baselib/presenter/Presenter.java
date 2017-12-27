package com.dlwx.baselib.presenter;

import android.util.Log;

import com.dlwx.baselib.model.ModeImpl;
import com.dlwx.baselib.model.ModeInterface;
import com.dlwx.baselib.view.ViewInterface;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/12/012.
 */

public class Presenter <V>extends BasePresenter{

        ViewInterface viewInterface;
    ModeImpl mode = new ModeImpl();

    public Presenter(ViewInterface viewInterface) {
        super();
        this.viewInterface = viewInterface;
    }
    //绑定view和mode
    public void fetch(Map<String,String> map,Boolean isget,String url,String cachKey){
        viewInterface.showLoading();

        if (mode != null) mode.loadData(new ModeInterface.LoadListener() {
            @Override
            public void complete(String s) {
                try {
                    viewInterface.showData(s);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("erro",e.getMessage());
                }
            }

            @Override
            public void onError() {
                viewInterface.onError();
            }
        }, map, isget, url, cachKey);

    }
}
