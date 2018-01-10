package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.ConnonTagBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private List<String> mVals = new ArrayList<>();
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
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        mPreenter.fetch(map,true, HttpUrl.common_sign,HttpUrl.common_sign+Token);


    }

    @Override
    protected void initListener() {
    flowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
        @Override
        public void onSelected(Set<Integer> selectPosSet) {
            wch("sdadasdasda");
        }
    });
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
        ConnonTagBean connonTagBean = gson.fromJson(s, ConnonTagBean.class);
        if (connonTagBean.getCode() == 200) {
            List<ConnonTagBean.BodyBean> body = connonTagBean.getBody();
            for (int i = 0; i < body.size(); i++) {
                mVals.add(body.get(i).getCommon_sign());

            }
            flowlayout.setAdapter(new TagAdapter<String>(mVals) {
                @Override
                public View getView(FlowLayout parent, int position, String s)
                {
                    TextView tv = (TextView) LayoutInflater.from(ctx).inflate(R.layout.tv,
                            flowlayout, false);
                    tv.setText(s);
                    return tv;
                }
            });
        }else{
            Toast.makeText(ctx, connonTagBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }
}
