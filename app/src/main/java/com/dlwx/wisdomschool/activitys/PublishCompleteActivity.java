package com.dlwx.wisdomschool.activitys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.PublishUpPiccheckBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 发布信息完成
 */
public class PublishCompleteActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.cv_morality)
    CardView cvMorality;
    @BindView(R.id.cv_learn)
    CardView cvLearn;
    @BindView(R.id.cv_sports)
    CardView cvSports;
    @BindView(R.id.cv_startwork)
    CardView cvStartwork;
    @BindView(R.id.tv_successNum)
    TextView tvSuccessNum;
    @BindView(R.id.iv_complete1)
    ImageView ivComplete1;
    @BindView(R.id.iv_complete2)
    ImageView ivComplete2;
    @BindView(R.id.iv_complete3)
    ImageView ivComplete3;
    @BindView(R.id.iv_complete4)
    ImageView ivComplete4;
    private List<Integer> body;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_publish_complete);
        ButterKnife.bind(this);
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();

        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) cvMorality.getLayoutParams();
        layoutParams1.height = width/2-120;
        cvMorality.setLayoutParams(layoutParams1);

        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) cvLearn.getLayoutParams();
        layoutParams2.height = width/2-120;
        cvLearn.setLayoutParams(layoutParams2);

        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) cvSports.getLayoutParams();
        layoutParams3.height = width/2-120;
        cvSports.setLayoutParams(layoutParams3);

        LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) cvStartwork.getLayoutParams();
        layoutParams4.height = width/2-120;
        cvStartwork.setLayoutParams(layoutParams4);
    }

    @Override
    protected void initData() {
        tvTitle.setText("综合素质纪录");
        initTabBar(toolBar);
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        mPreenter.fetch(map, true, HttpUrl.CheckSign, HttpUrl.CheckSign + Token);
    }

    @Override
    protected void initListener() {

    }
    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.cv_morality, R.id.cv_learn, R.id.cv_sports, R.id.cv_startwork})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cv_morality:
                skipActivity(1);
                break;
            case R.id.cv_learn:
                skipActivity(2);
                break;
            case R.id.cv_sports:
                skipActivity(3);
                break;
            case R.id.cv_startwork:
                skipActivity(4);
                break;
        }
    }
    private int seleteTagId;
    private String seleteTagName;
    private void skipActivity(int tagid) {
        Intent intent = new Intent(ctx,AllPicActivity.class);
        if (body != null) {
            for (int i = 0; i < body.size(); i++) {
                if (tagid == body.get(i)) {
                    seleteTagId = tagid;
                    switch (tagid) {
                        case 1:
                            seleteTagName = "品德少年";
                            startActivityForResult(intent, 2);
                            break;
                        case 2:
                            seleteTagName = "热爱学习";
                            startActivityForResult(intent, 2);
                            break;
                        case 3:
                            seleteTagName = "体育活动";
                            startActivityForResult(intent, 2);
                            break;
                        case 4:
                            seleteTagName = "动手能力";
                            startActivityForResult(intent, 2);
                            break;
                    }
                }
            }
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        PublishUpPiccheckBean publishUpPiccheckBean = gson.fromJson(s, PublishUpPiccheckBean.class);
        if (publishUpPiccheckBean.getCode() == 200) {
            body = publishUpPiccheckBean.getBody();
            if (body != null) {
                for (int i = 0; i < body.size(); i++) {
                    for (int j = 1; j < 5; j++) {
                        Integer integer = body.get(i);
                        if (j == i) {
                            switch (i) {
                                case 1:
                                    ivComplete1.setVisibility(View.GONE);
                                    break;
                                case 2:
                                    ivComplete2.setVisibility(View.GONE);
                                    break;
                                case 3:
                                    ivComplete3.setVisibility(View.GONE);
                                    break;
                                case 4:
                                    ivComplete4.setVisibility(View.GONE);
                                    break;
                                default:

                                    break;
                            }
                        } else {
                            int v;
                            switch (j) {
                                case 1:
                                    v = ivComplete1.getVisibility() == 0?0:8;
                                    ivComplete1.setVisibility(v);
                                    break;
                                case 2:
                                    v = ivComplete2.getVisibility() == 0?0:8;
                                    ivComplete2.setVisibility(v);
                                    break;
                                case 3:
                                    v = ivComplete3.getVisibility() == 0?0:8;
                                    ivComplete3.setVisibility(v);
                                    break;
                                case 4:
                                    v = ivComplete4.getVisibility() == 0?0:8;
                                    ivComplete4.setVisibility(v);
                                    break;
                                default:

                                    break;
                            }
                        }
                    }
                }
            } else {
                startActivity(new Intent(ctx, SynthesizeFullActivity.class));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
         switch (requestCode){
                    case 2:
                        ArrayList<String> images = data.getStringArrayListExtra("images");
                        Intent intent = new Intent(ctx,PublishGroupUpActivity.class);
                        intent.putExtra("tagId",seleteTagId+"");
                        intent.putExtra("tagname",seleteTagName);
                        startActivity(intent);
                        finish();
                        break;
                }
    }
}
