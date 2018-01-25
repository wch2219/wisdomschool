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
import com.dlwx.wisdomschool.adapter.DifficultyExamAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择考试难度
 */
public class ChoiceDifficultyExamActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_list)
    ListView lvList;
    private String age;
    private String name;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        age = intent.getStringExtra("age");
        name = intent.getStringExtra("name");
        setContentView(R.layout.activity_choice_difficulty_exam);
        ButterKnife.bind(this);
    }
    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText("考试难度");
        lvList.setAdapter(new DifficultyExamAdapter(ctx,name));
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
        Intent intent = new Intent(ctx, StartExamActivity.class);
        intent.putExtra("age",age);
        intent.putExtra("rank",i);
        startActivity(intent);
    }
}
