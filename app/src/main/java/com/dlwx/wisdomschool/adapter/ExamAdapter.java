package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.baselib.view.MyListView;
import com.dlwx.wisdomschool.R;

/**
 * Created by Administrator on 2017/12/25/025.
 */

public class ExamAdapter extends BaseFastAdapter {
    public ExamAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_exam, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        final ExamItemAdapter examItemAdapter = new ExamItemAdapter(ctx);
        vh.lv_list.setAdapter(examItemAdapter);
        vh.lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                examItemAdapter.setposition(i);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public MyListView lv_list;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.lv_list = (MyListView) rootView.findViewById(R.id.lv_list);
        }

    }
}
