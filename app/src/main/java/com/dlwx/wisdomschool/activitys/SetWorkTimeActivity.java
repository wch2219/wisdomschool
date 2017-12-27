package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.SetTimeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置工作时间
 */
public class SetWorkTimeActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.swich)
    Switch swich;
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_end)
    TextView tvEnd;
    @BindView(R.id.gv_list)
    GridView gvList;
    @BindView(R.id.btn_saveset)
    Button btnSaveset;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_set_work_time);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            tvTitle.setText("设置工作时间");
            initTabBar(toolBar);
            String [] strs = {"日","一","二","三","四","五","六"};
            gvList.setAdapter(new SetTimeAdapter(ctx,strs));
    }

    @Override
    protected void initListener() {


    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.tv_start, R.id.tv_end, R.id.btn_saveset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start:
                break;
            case R.id.tv_end:
                break;
            case R.id.btn_saveset:
                break;
        }
    }
}
