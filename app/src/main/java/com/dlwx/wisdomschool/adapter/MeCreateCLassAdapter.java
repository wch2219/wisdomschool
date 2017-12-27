package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.ClassHistoryNewsActivitry;
import com.ruffian.library.RTextView;

/**
 * Created by Administrator on 2017/12/15/015.
 */

public class MeCreateCLassAdapter extends BaseFastAdapter {
    public MeCreateCLassAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_create_class, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "dsad", Toast.LENGTH_SHORT).show();
            }
        });
        if (position == 2) {
            vh.ll_apply.setVisibility(View.VISIBLE);
        }else{
            vh.ll_apply.setVisibility(View.GONE);
        }
        vh.tv_mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx, ClassHistoryNewsActivitry.class));
            }
        });
        return convertView;
    }

   private class ViewHolder {
        public View rootView;
        public TextView tv_classnumber;
        public TextView tv_classmember;
        public RTextView tv_file;
        public RTextView tv_mess;
        public RTextView tv_invite;
        public LinearLayout ll_apply;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_classnumber = (TextView) rootView.findViewById(R.id.tv_classnumber);
            this.tv_classmember = (TextView) rootView.findViewById(R.id.tv_classmember);
            this.tv_file = (RTextView) rootView.findViewById(R.id.tv_file);
            this.tv_mess = (RTextView) rootView.findViewById(R.id.tv_mess);
            this.tv_invite = (RTextView) rootView.findViewById(R.id.tv_invite);
            this.ll_apply = rootView.findViewById(R.id.ll_apply);
        }

    }
}
