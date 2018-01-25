package com.dlwx.wisdomschool.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.ExamAdapter;
import com.dlwx.wisdomschool.bean.CardBean;
import com.dlwx.wisdomschool.bean.TestListBean;
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
 * 开始考试
 */
public class StartExamActivity extends BaseActivity implements ExamAdapter.SeleteResultListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    private String[] strs = {"一", "二", "三", "四", "五"};
    private String age;
    private int rank;
    private List<TestListBean.BodyBean> body;
    private ExamAdapter examAdapter;
    private TestListBean testListBean;
    private AlertDialog show;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        age = intent.getStringExtra("age");
        rank = intent.getIntExtra("rank", 0);
        setContentView(R.layout.activity_start_exam);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText(strs[rank] + "星考试");
        initTabBar(toolBar);
        HttpType = 1;
        Map<String, String> map = new HashMap<>();
        map.put("age", age);
        map.put("vip", rank + 1 + "");
        mPreenter.fetch(map, true, HttpUrl.Examexamlist, HttpUrl.Examexamlist + age + rank);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {
            testlist(s, gson);
        } else {
            CardBean cardBean = gson.fromJson(s, CardBean.class);
            if (cardBean.getCode() == 200) {

                Intent intent = new Intent(ctx, MyCertificateDescActivity.class);
                intent.putExtra("json", s);
                startActivity(intent);
            } else {
                Toast.makeText(ctx, cardBean.getResult(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void testlist(String s, Gson gson) {
        testListBean = gson.fromJson(s, TestListBean.class);
        if (testListBean.getCode() == 200) {
            body = testListBean.getBody();
            examAdapter = new ExamAdapter(ctx, body);
            lvList.setAdapter(examAdapter);
            examAdapter.setResultListener(this);
            tv_num.setText("0/" + body.size());
        } else {
            Toast.makeText(ctx, testListBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param map 答题的个数   正确答案所在选项
     */
    @Override
    public void result(Map<Integer, Integer> map) {
        tv_num.setText(map.size() + "/" + body.size());
        curr_num = map.size();
        if (map.size() == body.size()) {//全部答完显示提交按钮
            correct_answernum = 0;
            for (int i = 0; i < body.size(); i++) {
                TestListBean.BodyBean bodyBean = body.get(i);
                if (bodyBean.getDui_answer() == bodyBean.getSelete_answer()) {
                    correct_answernum++;
                }
            }
            wch("正确答案个数：" + correct_answernum);
        }
    }

    private int correct_answernum;//答对数量
    private int curr_num;//当前答题数

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_page, null);
        ViewHolder vh = new ViewHolder(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(view);
        if (curr_num == body.size()) {
            vh.tv_content.setText("试卷已经全部答完，是否继续提交");
            vh.btn_close.setText("检查试卷");
        } else {
            vh.tv_content.setText("试卷还有" + (body.size() - curr_num) + "题未做，提交还是继续答题？");
            vh.btn_close.setText("继续答题");
        }

        vh.btn_close.setOnClickListener(this);
        vh.btn_submit.setOnClickListener(this);
        show = builder.show();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                show.dismiss();
                break;
            case R.id.btn_submit:
                HttpType = 2;
                Map<String, String> map = new HashMap<>();
                map.put("token", Token);
                map.put("enid", age);
                map.put("vip", rank + 1 + "");
                map.put("dui_num", correct_answernum + "");
                mPreenter.fetch(map, true, HttpUrl.ExamSubmit, "");
                break;

        }
    }

    private class ViewHolder {
        public View rootView;
        public TextView tv_content;
        public Button btn_close;
        public Button btn_submit;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_content = (TextView) rootView.findViewById(R.id.tv_content);
            this.btn_close = (Button) rootView.findViewById(R.id.btn_close);
            this.btn_submit = (Button) rootView.findViewById(R.id.btn_submit);
        }

    }
}
