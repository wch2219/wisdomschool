package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;

/**
 * Created by Administrator on 2017/12/23/023.
 */

public class SynthesizeFullBottomAdapter extends BaseFastAdapter {
    public SynthesizeFullBottomAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_synthesizebottom, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        if (position == 8) {
            vh.cv_sports.setVisibility(View.GONE);
            vh.rl_other.setVisibility(View.VISIBLE);
        }else{
            vh.cv_sports.setVisibility(View.VISIBLE);
            vh.rl_other.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public TextView tv_num;
        public TextView tv_name;
        public CardView cv_sports;
        public RelativeLayout rl_other;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_num = (TextView) rootView.findViewById(R.id.tv_num);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.cv_sports = (CardView) rootView.findViewById(R.id.cv_sports);
            this.rl_other = (RelativeLayout) rootView.findViewById(R.id.rl_other);
        }

    }
}
