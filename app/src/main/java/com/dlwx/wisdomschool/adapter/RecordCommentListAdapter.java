package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.RecordListBean;
import com.dlwx.wisdomschool.utiles.EmoSwichUtiles;

import java.util.List;

/**
 * Created by Administrator on 2018/1/17/017.
 */

public class RecordCommentListAdapter extends BaseFastAdapter {
    private List<RecordListBean.BodyBean.PinglunHuifu> pinglun_huifu;
    public RecordCommentListAdapter(Context ctx,List<RecordListBean.BodyBean.PinglunHuifu> pinglun_huifu) {
        super(ctx);
        this.pinglun_huifu = pinglun_huifu;
    }

    @Override
    public int getCount() {
        return pinglun_huifu.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_comment, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        RecordListBean.BodyBean.PinglunHuifu pinglunHuifu = pinglun_huifu.get(position);
        if (TextUtils.isEmpty(pinglunHuifu.getHf_name())) {
            vh.ll_hf.setVisibility(View.GONE);

        }else{
            vh.ll_hf.setVisibility(View.VISIBLE);
        }
        vh.tv_hf_name.setText(pinglunHuifu.getHf_name());
        vh.plname.setText(pinglunHuifu.getPl_name());
        EmoSwichUtiles.toSwich(ctx,vh.tv_connect,pinglunHuifu.getContent());
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public TextView tv_hf_name;
        public LinearLayout ll_hf;
        public TextView plname;
        public TextView tv_connect;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_hf_name = (TextView) rootView.findViewById(R.id.tv_hf_name);
            this.ll_hf = (LinearLayout) rootView.findViewById(R.id.ll_hf);
            this.plname = (TextView) rootView.findViewById(R.id.plname);
            this.tv_connect = (TextView) rootView.findViewById(R.id.tv_connect);
        }

    }



}
