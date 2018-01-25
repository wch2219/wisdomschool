package com.dlwx.wisdomschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFastAdapter;
import com.dlwx.baselib.view.MyListView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.TestListBean;
import com.dlwx.wisdomschool.bean.TestSelteBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/25/025.
 */

public class ExamAdapter extends BaseFastAdapter {
    private List<TestListBean.BodyBean> body;
    private Map<Integer,Integer> map = new HashMap<>();
    public ExamAdapter(Context ctx,List<TestListBean.BodyBean> body) {
        super(ctx);
        this.body = body;
    }

    @Override
    public int getCount() {
        return body.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_exam, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        final TestListBean.BodyBean bodyBean = body.get(position);
        vh.tv_title.setText((position+1)+"、"+bodyBean.getTitle());
        List<String> option_list = bodyBean.getOption_list();
        final List<TestSelteBean> selteBeans = new ArrayList<>();
        for (int i = 0; i < option_list.size(); i++) {
            String s = option_list.get(i);
            TestSelteBean selteBean =  new TestSelteBean();
            selteBean.setName(s);
            selteBeans.add(selteBean);

        }
        final ExamItemAdapter examItemAdapter = new ExamItemAdapter(ctx,selteBeans);
        vh.lv_list.setAdapter(examItemAdapter);
        vh.lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                for (int j = 0; j < selteBeans.size(); j++) {
                    TestSelteBean selteBean = selteBeans.get(j);
                    if (i == j) {
                        selteBean.setIsselete(true);
                    }else{
                        selteBean.setIsselete(false);
                    }
                }
                examItemAdapter.setposition(i);
                bodyBean.setSelete_answer(i+1);
                map.put(position,i+1);
                resultListener.result(map);
            }
        });
        return convertView;
    }
    public interface SeleteResultListener{
        void result(Map<Integer,Integer> map);
    }
    private SeleteResultListener resultListener;

    public void setResultListener(SeleteResultListener resultListener) {
        this.resultListener = resultListener;
    }

    private class ViewHolder {
        public View rootView;
        public MyListView lv_list;
        public TextView tv_title;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.lv_list = (MyListView) rootView.findViewById(R.id.lv_list);
            this.tv_title = rootView.findViewById(R.id.tv_title);
        }

    }
}
