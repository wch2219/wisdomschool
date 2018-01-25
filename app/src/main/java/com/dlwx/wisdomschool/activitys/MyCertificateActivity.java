package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.MyCertificateAdapter;
import com.dlwx.wisdomschool.bean.ZhengshuListBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 我的证书
 */
public class MyCertificateActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_list)
    ListView lvList;
    private List<ZhengshuListBean.BodyBean> body;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_certificate);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("我的证书");
        initTabBar(toolBar);
        getData();

    }

    private void getData() {
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        HttpType =1;
        mPreenter.fetch(map,true, HttpUrl.Zhengshu,HttpUrl.Zhengshu+Token);
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
        Intent intent = new Intent(ctx, MyCertificateDescActivity.class);
        ZhengshuListBean.BodyBean bodyBean = body.get(i);
        intent.putExtra("bodybean",bodyBean);
        startActivity(intent);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        ZhengshuListBean zhengshuListBean = gson.fromJson(s, ZhengshuListBean.class);
        if (zhengshuListBean.getCode() == 200) {
            body = zhengshuListBean.getBody();
            lvList.setAdapter(new MyCertificateAdapter(ctx, body));
        }else{
            Toast.makeText(ctx, zhengshuListBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }
}
