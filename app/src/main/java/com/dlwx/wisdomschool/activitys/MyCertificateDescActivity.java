package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.CardBean;
import com.dlwx.wisdomschool.bean.ZhengshuListBean;
import com.dlwx.wisdomschool.utiles.PopuShareUtiles;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的证书展示
 */
public class MyCertificateDescActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rl_share)
    RelativeLayout rlShare;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_xing)
    TextView tvXing;
    @BindView(R.id.rl_pic)
    RelativeLayout rlPic;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_duinum)
    TextView tvDuinum;
    @BindView(R.id.tv_grade)
    TextView tvGrade;
    private String json;
    private ZhengshuListBean.BodyBean bodyBean;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        json = intent.getStringExtra("json");
        bodyBean = (ZhengshuListBean.BodyBean) intent.getSerializableExtra("bodybean");
        setContentView(R.layout.activity_my_certificate_desc);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("我的证书");
        initTabBar(toolBar);
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            CardBean cardBean = gson.fromJson(json, CardBean.class);
            if (cardBean.getCode() == 200) {
                CardBean.BodyBean body = cardBean.getBody();
                tvName.setText(body.getNickname());
                tvXing.setText(body.getVip());
                tvNumber.setText("编号：" + body.getZhengshu_cardno());
                tvDuinum.setText(body.getDui_num()+"题");
                tvGrade.setText(body.getTotal_score()+"");
            } else {
            }
        }else{
            tvName.setText(bodyBean.getNickname());
            tvXing.setText(bodyBean.getVip());
            tvNumber.setText("编号：" + bodyBean.getZhengshu_cardno());
            tvDuinum.setText(bodyBean.getDui_num()+"题");
            tvGrade.setText(bodyBean.getTotal_score());
        }
    }
    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick(R.id.rl_share)
    public void onViewClicked() {
        PopuShareUtiles shareUtiles = new PopuShareUtiles(ctx,rlPic);

    }

    /**
     * 截取当前图片分享
     * @param v
     * @return
     */
    private static Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }
}
