package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlwx.wisdomschool.R;

import java.util.List;

/**
 * Created by Administrator on 2017/12/23/023.
 */

public class PublishUpPicPageAdapter extends PagerAdapter {
    private Context ctx;
    private List<String> titleList ;
    private List<Integer> contentList;
    private List<Integer> intteArrList;

    public PublishUpPicPageAdapter(Context ctx,List<String> titleList,List<Integer> contentList,List<Integer> intteArrList) {
        super();
        this.ctx = ctx;
        this.titleList = titleList;
        this.contentList = contentList;
        this.intteArrList = intteArrList;
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_publishpage, null);
            ViewHolder vh = new ViewHolder(view);
            vh.tv_title.setText(titleList.get(position));
            vh.tv_content.setText(contentList.get(position));
            vh.ll_bg.setBackgroundResource(intteArrList.get(position));
        container.addView(view);
        vh.btn_uppic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skiAddPicBackListener.back(titleList.get(position),position);
            }
        });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (disspopuResultListener != null) {
                        disspopuResultListener.diss();
                    }
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

    public interface SkiAddPicBackListener{
        void back(String tagname,int position);
    }
    public SkiAddPicBackListener skiAddPicBackListener;

    public void setSkiAddPicBackListener(SkiAddPicBackListener skiAddPicBackListener) {
        this.skiAddPicBackListener = skiAddPicBackListener;
    }
    public interface DisspopuResultListener{
        void diss();
    }
    public DisspopuResultListener disspopuResultListener;

    public void setDisspopuResultListener(DisspopuResultListener disspopuResultListener) {
        this.disspopuResultListener = disspopuResultListener;
    }
}

