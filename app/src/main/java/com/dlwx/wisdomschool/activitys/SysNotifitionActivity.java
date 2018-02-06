package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.SysyNotiAdapter;
import com.dlwx.wisdomschool.bean.SysMessBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 系统通知
 */
public class SysNotifitionActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_clean)
    TextView tvClean;
    @BindView(R.id.lv_list)
    ListView lvList;
    private List<SysMessBean.BodyBean> body = new ArrayList<>();

    @Override
    protected void initView() {
        setContentView(R.layout.activity_sys_notifition);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("系统通知");
        initTabBar(toolBar);

        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        map.put("type","1");
        mPreenter.fetch(map,true, HttpUrl.SysMess,"");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick(R.id.tv_clean)
    public void onViewClicked() {
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        map.put("type","2");
        mPreenter.fetch(map,true, HttpUrl.SysMess,"");
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        SysMessBean sysMessBean = gson.fromJson(s, SysMessBean.class);
        if (sysMessBean.getCode() == 200) {
            if (sysMessBean.getBody() != null) {
                body = sysMessBean.getBody();
                lvList.setAdapter(new SysyNotiAdapter(ctx,body));
            }else{
                body.clear();
                lvList.setAdapter(new SysyNotiAdapter(ctx,body));
                Toast.makeText(ctx, sysMessBean.getResult(), Toast.LENGTH_SHORT).show();
            }
        }
        Toast.makeText(ctx, sysMessBean.getResult(), Toast.LENGTH_SHORT).show();
    }
}
