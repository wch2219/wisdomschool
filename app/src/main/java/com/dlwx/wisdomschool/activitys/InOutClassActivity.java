package com.dlwx.wisdomschool.activitys;

import android.graphics.Typeface;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.ApplyListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void initView() {
        setContentView(R.layout.activity_in_out_class);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("一年级八班");
        initTabBar(toolBar);
        applyListAdapter = new ApplyListAdapter(ctx);
        lvList.setAdapter(applyListAdapter);
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
                break;
            case R.id.tv_out:
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
}
