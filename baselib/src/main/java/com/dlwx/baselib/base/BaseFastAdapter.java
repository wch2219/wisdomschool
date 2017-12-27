package com.dlwx.baselib.base;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

/**
 * @作者 wch
 * @create at 2017/1/12 0012 下午 4:24
 * @name 普通适配器父类
 */
public abstract class BaseFastAdapter extends BaseAdapter {
    public Context ctx;
    public BaseFastAdapter(Context ctx) {
        super();
        this.ctx = ctx;
    }

    @Override
    public abstract int getCount();

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
    public void LogW(Object o){
        Log.i("wch",o+"");
    }
    public void LogX(Object o){
        Log.i("xys",o+"");
    }
    public void toast(Object o){
        Toast.makeText(ctx,o+"",Toast.LENGTH_SHORT).show();
    }
}
