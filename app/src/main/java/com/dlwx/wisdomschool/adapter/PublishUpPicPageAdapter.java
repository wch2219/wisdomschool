package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.PublishCompleteActivity;

/**
 * Created by Administrator on 2017/12/23/023.
 */

public class PublishUpPicPageAdapter extends PagerAdapter {
    private Context ctx;
    private final int[] intArray = {R.color.publist1,R.color.publist2,R.color.publist3,R.color.publist4};
    private int [] contents = {R.string.publishcontent1,R.string.publishcontent2,R.string.publishcontent3,R.string.publishcontent4};
    private String [] titles  = {"品质发展","动手能力","热爱学习","体育活动"};
    public PublishUpPicPageAdapter(Context ctx) {
        super();
        this.ctx = ctx;

    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_publishpage, null);
            ViewHolder vh = new ViewHolder(view);
            vh.tv_title.setText(titles[position]);
            vh.tv_content.setText(contents[position]);
            vh.ll_bg.setBackgroundResource(intArray[position]);
        container.addView(view);
        vh.btn_uppic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctx.startActivity(new Intent(ctx,PublishCompleteActivity.class));
            }
        });
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
    private class ViewHolder {
        public View rootView;
        public TextView tv_title;
        public TextView tv_content;
        public RelativeLayout btn_uppic;
        private LinearLayout ll_bg;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_content = (TextView) rootView.findViewById(R.id.tv_content);
            this.btn_uppic = (RelativeLayout) rootView.findViewById(R.id.btn_uppic);
            this.ll_bg = (LinearLayout) rootView.findViewById(R.id.ll_bg);
        }

    }
}
