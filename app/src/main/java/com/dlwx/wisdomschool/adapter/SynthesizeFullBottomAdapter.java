package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.EightSignBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/23/023.
 */

public class SynthesizeFullBottomAdapter extends BaseFastAdapter {
    private  String[] stringArray;
    private List<EightSignBean.BodyBean> bod;
    private final int[] intArray = {R.color.sign1, R.color.sign2, R.color.sign3, R.color.sign4,R.color.sign5, R.color.sign6, R.color.sign7, R.color.sign8};
    public SynthesizeFullBottomAdapter(Context ctx,List<EightSignBean.BodyBean> bod,String[] stringArray) {
        super(ctx);
        this.stringArray = stringArray;
        this.bod = bod;
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
            vh.tv_name.setText(stringArray[position]);
            vh.ll_bg.setBackgroundResource(intArray[position]);
            if (bod.size() == 0) {
                vh.tv_num.setText("0");
            }else{
                vh.tv_num.setText(bod.get(position).getNum()+"");
            }


        }
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public TextView tv_num;
        public TextView tv_name;
        public CardView cv_sports;
        public RelativeLayout rl_other;
        public LinearLayout ll_bg;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_num = (TextView) rootView.findViewById(R.id.tv_num);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.cv_sports = (CardView) rootView.findViewById(R.id.cv_sports);
            this.rl_other = (RelativeLayout) rootView.findViewById(R.id.rl_other);
            this.ll_bg = rootView.findViewById(R.id.ll_bg);
        }

    }
}
