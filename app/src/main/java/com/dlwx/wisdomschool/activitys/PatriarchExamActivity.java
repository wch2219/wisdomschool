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
import com.dlwx.wisdomschool.adapter.PatriarchExamStageAdapter;
import com.dlwx.wisdomschool.bean.ExamageListBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 家长考试
 */
public class PatriarchExamActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_certificate)
    TextView tvCertificate;
    @BindView(R.id.lv_list)
    ListView lvList;
    private List<ExamageListBean.BodyBean> body;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_patriarch_exam);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("阶段考试");
        initTabBar(toolBar);
        Map<String,String> map = new HashMap<>();
        mPreenter.fetch(map, true,HttpUrl.Examagelist,HttpUrl.Examagelist);

    }

    @Override
    protected void initListener() {
        lvList.setOnItemClickListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick(R.id.tv_certificate)
    public void onViewClicked() {
        startActivity(new Intent(ctx,MyCertificateActivity.class));

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ExamageListBean.BodyBean bodyBean = body.get(i);
        Intent intent = new Intent(ctx, ChoiceDifficultyExamActivity.class);
        intent.putExtra("age",bodyBean.getEnid());
        intent.putExtra("name",bodyBean.getName());

        startActivity(intent);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        ExamageListBean examageListBean = gson.fromJson(s, ExamageListBean.class);
        if (examageListBean.getCode() == 200) {
            body = examageListBean.getBody();
            lvList.setAdapter(new PatriarchExamStageAdapter(ctx, body));
        }
    }
}
