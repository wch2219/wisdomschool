package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.bean.ConnonTagBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 新添加的标签
 */
public class NewAddLableActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.flowlayout)
    TagFlowLayout flowlayout;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.et_tag)
    EditText et_tag;
    private List<String> mVals = new ArrayList<>();
    private List<ConnonTagBean.BodyBean> body;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_new_add_lable);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText("新增标签");
        HttpType = 1;
        HttpType =1;
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        mPreenter.fetch(map, true, HttpUrl.common_sign, HttpUrl.common_sign + Token);


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
            getcommonTag(s, gson);
        }else{
            BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
            if (backResultBean.getCode() == 200) {
                finish();
            }
            Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 常用标签
     * @param s
     * @param gson
     */
    private void getcommonTag(String s, Gson gson) {
        ConnonTagBean connonTagBean = gson.fromJson(s, ConnonTagBean.class);
        if (connonTagBean.getCode() == 200) {
            body = connonTagBean.getBody();
            for (int i = 0; i < body.size(); i++) {
                mVals.add(body.get(i).getCommon_sign());
            }
            flowlayout.setAdapter(new TagAdapter<String>(mVals) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) LayoutInflater.from(ctx).inflate(R.layout.tv,
                            flowlayout, false);
                    tv.setText(s);
                    return tv;
                }
            });
        } else {
            Toast.makeText(ctx, connonTagBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.tv_add)
    public void onViewClicked() {
        String seleteTag = null;
        Set<Integer> selectedList = flowlayout.getSelectedList();
        Iterator<Integer> iterator = selectedList.iterator();
        while (iterator.hasNext()){
            Integer next = iterator.next();
            wch(next);
            ConnonTagBean.BodyBean bodyBean = body.get(next);
            if (TextUtils.isEmpty(seleteTag)) {
                seleteTag = bodyBean.getCommon_sign();
            }else{
                seleteTag = seleteTag+","+ bodyBean.getCommon_sign();
            }
        }
        String tag = et_tag.getText().toString().trim();
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        HttpType = 2;
        map.put("signame",seleteTag+tag);
        mPreenter.fetch(map,false,HttpUrl.AddTag,"");

    }
}
