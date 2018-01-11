package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyListView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.RecordScreenAddAdapter;
import com.dlwx.wisdomschool.adapter.RecordScreenCreateAdapter;
import com.dlwx.wisdomschool.bean.MyAllClassBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.dlwx.wisdomschool.utiles.SpUtiles;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 选择发布的班级
 */
public class SeletePublishClassActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    //确认
    @BindView(R.id.tv_sendsms)
    TextView tvSendsms;
    @BindView(R.id.lv_listcreate)
    MyListView lvListcreate;
    @BindView(R.id.tv_mycreatenum)
    TextView tvMycreatenum;
    @BindView(R.id.tv_myaddnum)
    TextView tv_myaddnum;
    @BindView(R.id.lv_listadd)
    MyListView lvListadd;
    @BindView(R.id.ll_listprivacy)
    LinearLayout ll_listprivacy;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.cb_myself)
    CheckBox cb_myself;
    private List<MyAllClassBean.BodyBean.CreateListBean> create_list;
    private List<MyAllClassBean.BodyBean.JoinListBean> join_list;
    private RecordScreenCreateAdapter createAdapter;
    private RecordScreenAddAdapter addAdapter;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_selete_publish_class);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("选择要接收的班级");
        initTabBar(toolBar);
        String headpic = sp.getString(SpUtiles.Header_pic, "");
        Glide.with(ctx).load(headpic).into(ivPic);
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        mPreenter.fetch(map, true, HttpUrl.Classroom, HttpUrl.Classroom + Token);
    }

    @Override
    protected void initListener() {
        lvListcreate.setOnItemClickListener(createListener);
        lvListadd.setOnItemClickListener(addListener);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    private boolean isMyself;

    @OnClick({R.id.tv_sendsms, R.id.ll_listprivacy, R.id.tv_mycreatenum, R.id.tv_myaddnum})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sendsms:
                submit();
                break;
            case R.id.ll_listprivacy:

                change();
                break;

            case R.id.tv_mycreatenum:

                createNum = create_list.size();
                tvMycreatenum.setVisibility(View.GONE);
                createAdapter = new RecordScreenCreateAdapter(ctx, create_list, createNum);
                lvListcreate.setAdapter(createAdapter);
                break;

            case R.id.tv_myaddnum:

                addNum = join_list.size();
                tv_myaddnum.setVisibility(View.GONE);
                addAdapter = new RecordScreenAddAdapter(ctx, join_list, addNum);
                lvListadd.setAdapter(addAdapter);
                break;


        }
    }
    private String class_nos;
    /**
     * 提交选择的结果
     */
    private void submit() {

        if (cb_myself.isChecked()) {//选择了自己

        }else{//选择了班级
            for (int i = 0; i < create_list.size(); i++) {
                MyAllClassBean.BodyBean.CreateListBean createListBean = create_list.get(i);
                if (createListBean.isCheck()) {
                    if (TextUtils.isEmpty(class_nos)) {
                        class_nos = createListBean.getClass_no();
                    }else{
                        class_nos = class_nos + ","+createListBean.getClass_no();
                    }

                }
            }
            for (int i = 0; i < join_list.size(); i++) {
                MyAllClassBean.BodyBean.JoinListBean joinListBean = join_list.get(i);
                if (joinListBean.isCheck()) {
                    if (TextUtils.isEmpty(class_nos)) {
                        class_nos = joinListBean.getClass_no();
                    }else{
                        class_nos = class_nos + ","+joinListBean.getClass_no();
                    }

                }
            }

        }
        Intent intent = new Intent();
        intent.putExtra("class_nos",class_nos);
        setResult(101,intent);
        finish();
    }


    /**
     * 创建的班级条目点击监听
     */
    private AdapterView.OnItemClickListener createListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            MyAllClassBean.BodyBean.CreateListBean createListBean = create_list.get(i);
            boolean check = createListBean.isCheck();
            createListBean.setCheck(!check);
            createAdapter.notifyDataSetChanged();
            cb_myself.setChecked(false);
        }
    };
    /**
     * 加入的班级条目点击监听
     */
    private AdapterView.OnItemClickListener addListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            MyAllClassBean.BodyBean.JoinListBean joinListBean = join_list.get(i);
            boolean check = joinListBean.isCheck();
            joinListBean.setCheck(!check);
            addAdapter.notifyDataSetChanged();
            cb_myself.setChecked(false);
        }
    };

    /**
     * 选择自己改变状态
     */
    private void change() {
        for (int i = 0; i < create_list.size(); i++) {
            MyAllClassBean.BodyBean.CreateListBean createListBean = create_list.get(i);
            createListBean.setCheck(false);
        }
        for (int i = 0; i < join_list.size(); i++) {
            MyAllClassBean.BodyBean.JoinListBean joinListBean = join_list.get(i);
            joinListBean.setCheck(false);
        }
        createAdapter.notifyDataSetChanged();
        addAdapter.notifyDataSetChanged();
        if (!cb_myself.isChecked()) {
            cb_myself.setChecked(true);
        }
    }

    private int createNum;
    private int addNum;

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        MyAllClassBean myAllClassBean = gson.fromJson(s, MyAllClassBean.class);
        if (myAllClassBean.getCode() == 200) {
            MyAllClassBean.BodyBean body = myAllClassBean.getBody();
            create_list = body.getCreate_list();
            join_list = body.getJoin_list();

            if (create_list.size() > 2) {
                createNum = 2;
                tvMycreatenum.setVisibility(View.VISIBLE);
                tvMycreatenum.setText("显示全部" + create_list.size() + "个班级");
                createAdapter = new RecordScreenCreateAdapter(ctx, create_list, createNum);

            } else {
                createNum = create_list.size();
                tvMycreatenum.setVisibility(View.GONE);
                createAdapter = new RecordScreenCreateAdapter(ctx, create_list, createNum);
            }
            lvListcreate.setAdapter(createAdapter);

            if (join_list.size() > 2) {
                addNum = 2;
                tv_myaddnum.setVisibility(View.VISIBLE);
                tv_myaddnum.setText("显示全部" + join_list.size() + "个班级");
                addAdapter = new RecordScreenAddAdapter(ctx, join_list, addNum);
            } else {
                addNum = join_list.size();
                tv_myaddnum.setVisibility(View.GONE);
                addAdapter = new RecordScreenAddAdapter(ctx, join_list, addNum);
            }
            lvListadd.setAdapter(addAdapter);
        } else {
            Toast.makeText(ctx, myAllClassBean.getResult(), Toast.LENGTH_SHORT).show();
        }

    }
}
