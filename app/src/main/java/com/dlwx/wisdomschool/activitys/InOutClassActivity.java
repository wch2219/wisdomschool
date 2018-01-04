package com.dlwx.wisdomschool.activitys;

import android.graphics.Typeface;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.ApplyListAdapter;
import com.dlwx.wisdomschool.bean.ClassAppliListeBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 入退班申请
 */
public class InOutClassActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener,AdapterView.OnItemClickListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_inclass)
    TextView tvInclass;
    @BindView(R.id.tv_out)
    TextView tvOut;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.cb_all)
    CheckBox cbAll;
    @BindView(R.id.tv_allaggress)
    TextView tvAllaggress;
    private ApplyListAdapter applyListAdapter;
    private String classid;
    private List<ClassAppliListeBean.BodyBean> body;

    @Override
    protected void initView() {
        classid = getIntent().getStringExtra("classid");
        setContentView(R.layout.activity_in_out_class);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("一年级八班");
        initTabBar(toolBar);

        getData("1");
    }

    private void getData(String isAdd) {
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        map.put("classid",classid);
        map.put("isadd",isAdd);
        mPreenter.fetch(map,true, HttpUrl.Classapplylist,HttpUrl.Classapplylist+Token+classid+isAdd);
    }

    @Override
    protected void initListener() {
        cbAll.setOnCheckedChangeListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.tv_inclass, R.id.tv_out,R.id.tv_allaggress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_inclass:
                tvInclass.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                tvOut.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//加粗
                getData("1");
                break;
            case R.id.tv_out:
                getData("2");
                tvOut.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                tvInclass.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//加粗
                break;
            case R.id.tv_allaggress:

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        applyListAdapter.setCheck(i);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {

        }

    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        ClassAppliListeBean classAppliListeBean = gson.fromJson(s, ClassAppliListeBean.class);
        if (classAppliListeBean.getCode() == 200) {
            body = classAppliListeBean.getBody();
            if (body == null) {

                return;
            }
            applyListAdapter = new ApplyListAdapter(ctx,body);
            lvList.setAdapter(applyListAdapter);
        }else{
            Toast.makeText(ctx, classAppliListeBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }
}
