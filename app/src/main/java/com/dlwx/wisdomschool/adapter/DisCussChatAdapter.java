package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;
/**
 * Created by Administrator on 2017/12/30/030.
 */

public class DisCussChatAdapter extends BaseFastAdapter {
    public DisCussChatAdapter(Context ctx) {
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_discuss_chat, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        if (position == 0) {
           vh.rl_left.setVisibility(View.VISIBLE);
           vh.rl_right.setVisibility(View.GONE);
           vh.ll_viceoleft.setVisibility(View.GONE);
           vh.ll_text.setVisibility(View.VISIBLE);
        }else if (position == 1) {
           vh.rl_left.setVisibility(View.VISIBLE);
           vh.rl_right.setVisibility(View.GONE);
           vh.ll_viceoleft.setVisibility(View.VISIBLE);
           vh.ll_text.setVisibility(View.GONE);
        }else if (position == 2) {
           vh.rl_left.setVisibility(View.GONE);
           vh.rl_right.setVisibility(View.VISIBLE);
           vh.ll_rightviceo.setVisibility(View.GONE);
           vh.ll_righttext.setVisibility(View.VISIBLE);
        }else if (position == 3) {
           vh.rl_left.setVisibility(View.GONE);
           vh.rl_right.setVisibility(View.VISIBLE);
           vh.ll_rightviceo.setVisibility(View.VISIBLE);
           vh.ll_righttext.setVisibility(View.GONE);
        }
        return convertView;
    }

//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(ctx).inflate(R.layout.item_discuss_chat, null);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (position == 0) {
//           vh.rl_left.setVisibility(View.VISIBLE);
//           vh.rl_right.setVisibility(View.GONE);
//           vh.ll_viceoleft.setVisibility(View.GONE);
//           vh.ll_text.setVisibility(View.VISIBLE);
//        }else if (position == 1) {
//           vh.rl_left.setVisibility(View.VISIBLE);
//           vh.rl_right.setVisibility(View.GONE);
//           vh.ll_viceoleft.setVisibility(View.VISIBLE);
//           vh.ll_text.setVisibility(View.GONE);
//        }else if (position == 2) {
//           vh.rl_left.setVisibility(View.GONE);
//           vh.rl_right.setVisibility(View.VISIBLE);
//           vh.ll_rightviceo.setVisibility(View.GONE);
//           vh.ll_righttext.setVisibility(View.VISIBLE);
//        }else if (position == 3) {
//           vh.rl_left.setVisibility(View.GONE);
//           vh.rl_right.setVisibility(View.VISIBLE);
//           vh.ll_rightviceo.setVisibility(View.VISIBLE);
//           vh.ll_righttext.setVisibility(View.GONE);
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 10;
//    }


    private class ViewHolder{
        public View rootView;
        public LinearLayout ll_viceoleft;
        public TextView tv_content;
        public LinearLayout ll_text;
        public TextView tv_leftname;
        public RelativeLayout rl_left;
        public LinearLayout ll_rightviceo;
        public TextView tv_rightcontent;
        public LinearLayout ll_righttext;
        public TextView tv_rightname;
        public RelativeLayout rl_right;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ll_viceoleft = (LinearLayout) rootView.findViewById(R.id.ll_viceoleft);
            this.tv_content = (TextView) rootView.findViewById(R.id.tv_content);
            this.ll_text = (LinearLayout) rootView.findViewById(R.id.ll_text);
            this.tv_leftname = (TextView) rootView.findViewById(R.id.tv_leftname);
            this.rl_left = (RelativeLayout) rootView.findViewById(R.id.rl_left);
            this.ll_rightviceo = (LinearLayout) rootView.findViewById(R.id.ll_rightviceo);
            this.tv_rightcontent = (TextView) rootView.findViewById(R.id.tv_rightcontent);
            this.ll_righttext = (LinearLayout) rootView.findViewById(R.id.ll_righttext);
            this.tv_rightname = (TextView) rootView.findViewById(R.id.tv_rightname);
            this.rl_right = (RelativeLayout) rootView.findViewById(R.id.rl_right);
        }

    }
}
