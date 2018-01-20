package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.EightSignBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/23/023.
 */

public class SynthesizeFullTopAdapter extends BaseFastAdapter {

    private  String[] stringArray;
    private List<EightSignBean.BodyBean> bod;
    public SynthesizeFullTopAdapter(Context ctx,List<EightSignBean.BodyBean> bod,String[] stringArray) {
        super(ctx);
        this.stringArray = stringArray;
        this.bod = bod;
    }
    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_synthesizetop, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_name.setText(stringArray[position]);
        if (bod.size() == 0) {
            vh.tv_num.setText("0");
        }else{
            vh.tv_num.setText(bod.get(position).getNum()+"");
        }

        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public TextView tv_name;
        public TextView tv_num;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_num = (TextView) rootView.findViewById(R.id.tv_num);
        }

    }
}
