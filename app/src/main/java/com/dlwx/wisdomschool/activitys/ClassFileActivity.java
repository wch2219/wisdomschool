package com.dlwx.wisdomschool.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyPopuWindow;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.ClassFileAdapter;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.bean.ClassFileBean;
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
    ListView ablvList;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv_size)
    TextView tvSize;
    @BindView(R.id.ll_noentry)
    LinearLayout llNoentry;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    private MyPopuWindow popuWindow;
    private String classid;
    private ViewHolderDia vhDia;
    private AlertDialog diaShow;

    @Override
    protected void initView() {
        classid = getIntent().getStringExtra("classid");
        setContentView(R.layout.activity_class_file);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("班级文件");
        initTabBar(toolBar);

        getFileList();
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

    /**
     * 获得文件列表
     */
    private void getFileList() {
        HttpType = 1;
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("classid", classid);
//        map.put("type",type);
//        map.put("name",name);
        mPreenter.fetch(map, true, HttpUrl.getClassFile, HttpUrl.getClassFile + Token + classid);

    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {

            fileList(s, gson);
        }else if (HttpType == 2) {
            BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
            if (backResultBean.getCode() == 200) {
                getFileList();
            }
            Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 文件列表
     * @param s
     * @param gson
     */
    private void fileList(String s, Gson gson) {
        ClassFileBean classFileBean = gson.fromJson(s, ClassFileBean.class);
        if (classFileBean.getCode() == 200) {
            long totalsize = 1073741824;
            ClassFileBean.BodyBean body = classFileBean.getBody();
            long use_size = body.getUse_size();
            progressbar.setMax((int) totalsize);
            progressbar.setProgress((int) (use_size));
            long m = use_size / 1024;
            long mm = use_size % 1024;
            wch(mm);
            long g = m / 1024;
            long gg = m % 1024;
            if (g > 0) {
                tvSize.setText(g + "." + gg + "G/1G");
            } else if (m > 0) {
                tvSize.setText(m + "." + mm + "M/1G");
            } else {
                tvSize.setText(use_size + "KB/1G");
            }
            List<ClassFileBean.BodyBean.ListBean> list = body.getList();
            ablvList.setAdapter(new ClassFileAdapter(ctx, list));
        } else {
            Toast.makeText(ctx, classFileBean.getResult(), Toast.LENGTH_SHORT).show();
        }
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
                    startActivity(new Intent(ctx,AllPicActivity.class));

                    break;
                case R.id.tv_upfile:
                    startActivity(new Intent(ctx, SeleteFileActivity.class));
                    break;
                case R.id.tv_addfile:
                    showDia();
                    break;

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_close:
                diaShow.dismiss();
                break;
            case R.id.tv_create:
                String filename = vhDia.et_filename.getText().toString().trim();
                if (TextUtils.isEmpty(filename)) {
                    Toast.makeText(ctx, "请填写文件名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("token", Token);
                HttpType = 2;
                map.put("classid", classid);
                map.put("name",filename);
                mPreenter.fetch(map,false,HttpUrl.CreateClassFile,"");
                diaShow.dismiss();
                break;

        }
    }

    private void showDia() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_wishbag, null);
        vhDia = new ViewHolderDia(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(view);
        builder.setCancelable(false);
        diaShow = builder.show();
        vhDia.tv_close.setOnClickListener(this);
        vhDia.tv_create.setOnClickListener(this);
    }

    private class ViewHolderDia {
        public View rootView;
        public EditText et_filename;
        public TextView tv_close;
        public TextView tv_create;

        public ViewHolderDia(View rootView) {
            this.rootView = rootView;
            this.et_filename = (EditText) rootView.findViewById(R.id.et_filename);
            this.tv_close = (TextView) rootView.findViewById(R.id.tv_close);
            this.tv_create = (TextView) rootView.findViewById(R.id.tv_create);
        }

    }
}




