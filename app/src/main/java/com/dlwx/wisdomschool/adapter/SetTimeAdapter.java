package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.SetWorkTimeBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/19/019.
 */

public class SetTimeAdapter extends BaseFastAdapter {
    private List<SetWorkTimeBean> alltimes;
    public SetTimeAdapter(Context ctx,List<SetWorkTimeBean> alltimes) {
        super(ctx);
        this.alltimes = alltimes;
    }

    @Override
    public int getCount() {
        return alltimes.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_time, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        if (position>0&&position<alltimes.size()-1) {
            vh.tv_time.setChecked(true);
        }else{
            vh.tv_time.setChecked(false);
        }
        final SetWorkTimeBean timeBean = alltimes.get(position);
        vh.tv_time.setText(timeBean.getDay());
        vh.tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = vh.tv_time.isChecked();
                timeBean.setCheck(checked);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public CheckBox tv_time;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_time = (CheckBox) rootView.findViewById(R.id.tv_time);
        }
    }
}
