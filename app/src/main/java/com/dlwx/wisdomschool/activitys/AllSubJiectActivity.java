package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.AllSubJiectAdapter;
import com.dlwx.wisdomschool.bean.AllSubjectBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 全部学科成绩排名
 */
public class AllSubJiectActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_list)
    ListView lvList;
    private String irid;
    private List<AllSubjectBean.BodyBean> body;
    private String classid;

    @Override
    protected void initView() {
        irid = getIntent().getStringExtra("irid");
        classid = getIntent().getStringExtra("classid");
        setContentView(R.layout.activity_all_sub_kiect);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("成绩排名");
        initTabBar(toolBar);

        getData();
    }



    @Override
    protected void initListener() {
        lvList.setOnItemClickListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(ctx, ClassAllStuActivity.class);
        if (i == 0) {//总成绩排名

            intent.putExtra("xuekeid","999");
        }else{
            intent.putExtra("xuekeid",body.get(i-1).getXuekeid());
        }
        intent.putExtra("irid",irid);
        intent.putExtra("classid",classid);
        startActivity(intent);
    }

    /**
     * 获取该考试下所有学科
     */
    private void getData() {
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        map.put("irid",irid);
        mPreenter.fetch(map,true, HttpUrl.get_xueke,HttpUrl.get_xueke+Token+irid);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        AllSubjectBean allSubjectBean = gson.fromJson(s, AllSubjectBean.class);
        if (allSubjectBean.getCode() == 200) {
            body = allSubjectBean.getBody();
            lvList.setAdapter(new AllSubJiectAdapter(ctx,body));
        }else{

        }
    }
}
