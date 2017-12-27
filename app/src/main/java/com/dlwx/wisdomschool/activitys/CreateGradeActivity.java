package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyListView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.CreateGradeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建成绩
 */
public class CreateGradeActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.lv_list)
    MyListView lvList;
    @BindView(R.id.tv_addgrade)
    TextView tvAddgrade;
    @BindView(R.id.btn_save)
    Button btnSave;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_create_grade);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("创建考试成绩");
        initTabBar(toolBar);
        lvList.setAdapter(new CreateGradeAdapter(ctx));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.tv_addgrade, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_addgrade:
                break;
            case R.id.btn_save:
                finish();
                break;
        }
    }
}
