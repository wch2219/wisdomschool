package com.dlwx.wisdomschool.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyPopuWindow;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.ClassFileAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 班级文件
 */
public class ClassFileActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rl_entry)
    RelativeLayout rlEntry;
    @BindView(R.id.ablv_list)
    ExpandableListView ablvList;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv_size)
    TextView tvSize;
    @BindView(R.id.ll_noentry)
    LinearLayout llNoentry;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    private MyPopuWindow popuWindow;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_class_file);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("班级文件");
        initTabBar(toolBar);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        ablvList.setIndicatorBounds(width - 150, width - 40);
        ablvList.setAdapter(new ClassFileAdapter(ctx));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.iv_add)
    public void onViewClicked() {
        showPopu();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showPopu() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_fileadd, null);
        ViewHolderPopu vhpopu = new ViewHolderPopu(view);
        popuWindow = new MyPopuWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popuWindow.setOutsideTouchable(true);
        popuWindow.setFocusable(true);
        backgroundAlpha(0.5f);
        popuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popuWindow.dismiss();
                backgroundAlpha(1);
            }
        });
        popuWindow.showAsDropDown(ivAdd, -10, 10, Gravity.RIGHT);
    }

    private class ViewHolderPopu implements View.OnClickListener {
        public View rootView;
        public TextView tv_uppic;
        public TextView tv_upfile;
        public TextView tv_addfile;

        public ViewHolderPopu(View rootView) {
            this.rootView = rootView;
            this.tv_uppic = (TextView) rootView.findViewById(R.id.tv_uppic);
            this.tv_upfile = (TextView) rootView.findViewById(R.id.tv_upfile);
            this.tv_addfile = (TextView) rootView.findViewById(R.id.tv_addfile);
            tv_uppic.setOnClickListener(this);
            tv_upfile.setOnClickListener(this);
            tv_addfile.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            popuWindow.dismiss();
            switch (view.getId()) {
                case R.id.tv_uppic:


                    break;
                case R.id.tv_upfile:
                    startActivity(new Intent(ctx,SeleteFileActivity.class));
                    break;
                case R.id.tv_addfile:
                    break;

            }
        }
    }
}




