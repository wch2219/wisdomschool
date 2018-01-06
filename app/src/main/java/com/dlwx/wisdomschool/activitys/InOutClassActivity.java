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
import com.dlwx.wisdomschool.bean.BackResultBean;
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
    private String isadd = "1";
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

        getData(isadd);
    }

    private void getData(String isAdd) {
        HttpType = 1;
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        map.put("classid",classid);
        map.put("isadd",isAdd);
        mPreenter.fetch(map,true, HttpUrl.Classapplylist,HttpUrl.Classapplylist+Token+classid+isAdd);
    }

    @Override
    protected void initListener() {
        cbAll.setOnCheckedChangeListener(this);
        lvList.setOnItemClickListener(this);

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
                isadd = "1";
                getData(isadd);
                break;
            case R.id.tv_out:
                isadd = "2";
                getData(isadd);
                tvOut.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                tvInclass.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//加粗
                break;
            case R.id.tv_allaggress:

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ClassAppliListeBean.BodyBean bodyBean = body.get(i);
        boolean check = bodyBean.isCheck();
            bodyBean.setCheck(!check);
            int totalnum = 0;
        for (int j = 0; j < body.size(); j++) {
            ClassAppliListeBean.BodyBean bodyBean1 = body.get(j);
            if (bodyBean1.isCheck()) {
                totalnum++;
            }
        }
        if (totalnum == body.size()) {
            cbAll.setChecked(true);
        }else{
            cbAll.setChecked(false);
        }
            applyListAdapter.notifyDataSetChanged();
    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            for (int j = 0; j < body.size(); j++) {
                ClassAppliListeBean.BodyBean bodyBean1 = body.get(j);
                    bodyBean1.setCheck(true);
            }
            applyListAdapter.notifyDataSetChanged();
        }else{
            for (int j = 0; j < body.size(); j++) {
                ClassAppliListeBean.BodyBean bodyBean1 = body.get(j);
                bodyBean1.setCheck(false);
            }
            applyListAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {
            applyList(s, gson);
        }else{
            BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
            if (backResultBean.getCode() == 200) {
                getData(isadd);
            }
            Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 申请列表
     * @param s
     * @param gson
     */
    private void applyList(String s, Gson gson) {
        ClassAppliListeBean classAppliListeBean = gson.fromJson(s, ClassAppliListeBean.class);
        if (classAppliListeBean.getCode() == 200) {
            body = classAppliListeBean.getBody();
            if (body == null) {
                return;
            }
            applyListAdapter = new ApplyListAdapter(ctx,body);
            lvList.setAdapter(applyListAdapter);
            applyListAdapter.setCloseAndAgressOnclicklistener(onclicklistener);
        }else{
            Toast.makeText(ctx, classAppliListeBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    private ApplyListAdapter.CloseAndAgressOnclicklistener onclicklistener =  new ApplyListAdapter.CloseAndAgressOnclicklistener() {
        @Override
        public void closes(int postion) {//忽略
            Map<String,String> map = new HashMap<>();
            map.put("classid",classid);
            map.put("ischeck","2");
            map.put("jcid",body.get(postion).getJcid());
            closeAndAggress(map);
        }

        @Override
        public void aggress(int postion) {//同意
            Map<String,String> map = new HashMap<>();
            map.put("classid",classid);
            map.put("ischeck","1");
            map.put("jcid",body.get(postion).getJcid());
            closeAndAggress(map);
        }
    };
        private void closeAndAggress(Map <String,String> map){
            map.put("token",Token);
            map.put("isadd",isadd);
            HttpType = 2;
            mPreenter.fetch(map,true,HttpUrl.Edit_Apply,"");
        }


}
