package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.SeleteOtherIdAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选则其他身份
 */
public class SeleteOtherIdActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.gv_list)
    GridView gvList;
    private SeleteOtherIdAdapter seleteOtherIdAdapter;
    private String named;//称呼

    @Override
    protected void initView() {
        setContentView(R.layout.activity_selete_other_id);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            tvTitle.setText("保存");
            initTabBar(toolBar);
        seleteOtherIdAdapter = new SeleteOtherIdAdapter(ctx);
        gvList.setAdapter(seleteOtherIdAdapter);
        named = seleteOtherIdAdapter.strs[0];
    }

    @Override
    protected void initListener() {
        gvList.setOnItemClickListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick(R.id.tv_save)
    public void onViewClicked() {
        String name = etName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(ctx, "请输入学生姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent =  new Intent();
        intent.putExtra("name",name);
        intent.putExtra("named",named);
        setResult(1,intent);
        finish();
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        named = seleteOtherIdAdapter.strs[i];
        seleteOtherIdAdapter.setCheck(i);
    }
}
