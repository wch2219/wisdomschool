package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.SeleteClassAdapter;
import com.dlwx.wisdomschool.base.MyApplication;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.bean.ClassListBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择班级
 */
public class SeleteClassActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.ll_createclass)
    LinearLayout llCreateclass;
    @BindView(R.id.lv_list)
    ListView lvList;
    private ClassListBean classListBean;
    private String cnid;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        classListBean = (ClassListBean) intent.getSerializableExtra("classlist");
        setContentView(R.layout.activity_selete_class);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("选择班级");
        initTabBar(toolBar);
        if (classListBean.getCode() == 200) {
            List<ClassListBean.BodyBean> body = classListBean.getBody();
            if (body != null | body.size() != 0) {
                lvList.setAdapter(new SeleteClassAdapter(ctx,body));

            }
        }
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
        List<ClassListBean.BodyBean> body = classListBean.getBody();
        ClassListBean.BodyBean bodyBean = body.get(i);
        cnid = bodyBean.getCnid();
        startActivityForResult(new Intent(ctx,CreateGradeActivity.class),1);
    }

    @OnClick(R.id.ll_createclass)
    public void onViewClicked() {
//        startActivityForResult(new Intent(ctx,CreateClassActivity.class),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
         switch (requestCode){
                    case 1:
                        String file = data.getStringExtra("file");
                        wch(file);
                        upExcle(file);
                        break;
                }
    }

    private void upExcle(String file) {
        OkGo.<String>post(HttpUrl.UpExcle)
                .params("token", MyApplication.Token)
                .params("classid",cnid)
                .params("file_stu",new File(file))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        Gson gson = new Gson();
                        BackResultBean backResultBean = gson.fromJson(body, BackResultBean.class);
                        if (backResultBean.getCode() == 200) {
                           finish();
                        }
                        Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(ctx, "网络连接失败", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
