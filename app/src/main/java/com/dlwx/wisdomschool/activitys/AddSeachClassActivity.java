package com.dlwx.wisdomschool.activitys;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 加入班级
 */
public class AddSeachClassActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.btn_cheack)
    Button btnCheack;
    @BindView(R.id.et_class)
    EditText et_class;
    @BindView(R.id.tv_classnum)
    TextView tvClassnum;
    @BindView(R.id.tv_teach)
    TextView tvTeach;
    @BindView(R.id.tv_classcode)
    TextView tvClasscode;
    @BindView(R.id.btn_addclass)
    Button btnAddclass;
    @BindView(R.id.ll_seachresult)
    LinearLayout llSeachresult;
    @BindView(R.id.tv_kefu)
    TextView tv_kefu;
    private PopupWindow popupWindow;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_add_seach_class);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            tvTitle.setText("加入一个班级");
            initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.btn_cheack, R.id.btn_addclass, R.id.tv_kefu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cheack:
                String classcode = et_class.getText().toString().trim();
                if (TextUtils.isEmpty(classcode)) {
                    Toast.makeText(ctx, "班级号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                inputshoworhind();
                llSeachresult.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_addclass:
                showPopu();
                break;
            case R.id.tv_kefu:

                break;

        }
    }

    private void showPopu() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_addclass, null);
        ViewHolder vh = new ViewHolder(view);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        backgroundAlpha(0.5f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
                backgroundAlpha(1);
            }
        });
        popupWindow.showAtLocation(tv_kefu, Gravity.BOTTOM, 0, 0);

    }

    private class ViewHolder implements View.OnClickListener {
        public View rootView;
        public ImageView iv_close;
        public TextView tv_oneself;
        public TextView tv_patriarch;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_close = (ImageView) rootView.findViewById(R.id.iv_close);
            this.tv_oneself = (TextView) rootView.findViewById(R.id.tv_oneself);
            this.tv_patriarch = (TextView) rootView.findViewById(R.id.tv_patriarch);
            this.iv_close.setOnClickListener(this);
            this.tv_oneself.setOnClickListener(this);
            this.tv_patriarch.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            popupWindow.dismiss();
            finish();
            switch (view.getId()) {
                case R.id.iv_close:

                    break;
                case R.id.tv_oneself:
                    break;
                case R.id.tv_patriarch:
                    break;

            }
        }
    }
}
