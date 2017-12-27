package com.dlwx.wisdomschool.activitys;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyPopuWindow;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.SeleteFileAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择文件
 */
public class SeleteFileActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_allfile)
    TextView tvAllfile;
    @BindView(R.id.lv_list)
    ListView lvList;
    private MyPopuWindow popuWindow;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_selete_file);
        ButterKnife.bind(this);
        View headView = LayoutInflater.from(ctx).inflate(R.layout.head_seletefile, null);
        lvList.addHeaderView(headView);
    }

    @Override
    protected void initData() {
        tvTitle.setText("选择文件");
        initTabBar(toolBar);
        lvList.setAdapter(new SeleteFileAdapter(ctx));

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.tv_allfile)
    public void onViewClicked() {
        showPopu();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showPopu() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_seletefileadd, null);
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
        popuWindow.showAsDropDown(tvAllfile, -10, 10, Gravity.RIGHT);

    }

    private class ViewHolderPopu implements View.OnClickListener {
        public View rootView;
        public TextView tv_allfile;
        public TextView tv_documentfile;
        public TextView tv_picfile;
        public TextView tv_voicefile;

        public ViewHolderPopu(View rootView) {
            this.rootView = rootView;
            this.tv_allfile = (TextView) rootView.findViewById(R.id.tv_allfile);
            this.tv_documentfile = (TextView) rootView.findViewById(R.id.tv_documentfile);
            this.tv_picfile = (TextView) rootView.findViewById(R.id.tv_picfile);
            this.tv_voicefile = (TextView) rootView.findViewById(R.id.tv_voicefile);
            tv_allfile.setOnClickListener(this);
            tv_documentfile.setOnClickListener(this);
            tv_picfile.setOnClickListener(this);
            tv_voicefile.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            popuWindow.dismiss();
            switch (view.getId()) {
                case R.id.tv_allfile:

                    break;
                case R.id.tv_documentfile:

                    break;
                case R.id.tv_picfile:
                    break;
                case R.id.tv_voicefile:
                    break;

            }
        }
    }
}
