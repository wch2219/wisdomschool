package com.dlwx.wisdomschool.activitys;

import android.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyGridView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.DisCussSettAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 讨论设置
 */
public class DisCussSettActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.gv_list)
    MyGridView gvList;
    @BindView(R.id.tv_notspeck)
    TextView tvNotspeck;
    @BindView(R.id.ll_allpersion)
    LinearLayout llAllpersion;
    @BindView(R.id.tv_endtime)
    TextView tvEndtime;
    @BindView(R.id.nodisturbing)
    Switch nodisturbing;
    @BindView(R.id.ll_stop)
    LinearLayout llStop;
    @BindView(R.id.ll_settendtime)
    LinearLayout ll_settendtime;
    private AlertDialog diashow;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_dis_cuss_sett);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("智慧讨论设置");
        initTabBar(toolBar);
        gvList.setAdapter(new DisCussSettAdapter(ctx));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.ll_allpersion, R.id.ll_stop, R.id.ll_settendtime})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_allpersion:
                break;
            case R.id.ll_stop:
                showDia();
                break;
            case R.id.ll_settendtime:
                showPopu();
                break;
        }
    }

    private void showDia() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_discuss, null);
        ViewHolderDia vhdia = new ViewHolderDia(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(view);
        vhdia.tv_close.setOnClickListener(this);
        vhdia.btn_aff.setOnClickListener(this);
        diashow = builder.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_close:
                diashow.dismiss();
                break;
            case R.id.btn_aff:
                diashow.dismiss();
                break;
        }
    }

    /**
     * 显示时间窗体
     */
    private void showPopu() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_settentime, null);
        ViewHolderPopu vh = new ViewHolderPopu(view);

        PopupWindow popu = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popu.setFocusable(true);
        popu.setOutsideTouchable(true);
        popu.showAtLocation(tvEndtime, Gravity.BOTTOM, 0, 0);
    }

    private class ViewHolderPopu {
        public View rootView;
        public TextView tv_nottime;
        public TextView tv_sett;
        public DatePicker datePicker;
        public TimePicker timepicker;

        public ViewHolderPopu(View rootView) {
            this.rootView = rootView;
            this.tv_nottime = (TextView) rootView.findViewById(R.id.tv_nottime);
            this.tv_sett = (TextView) rootView.findViewById(R.id.tv_sett);
            this.datePicker = (DatePicker) rootView.findViewById(R.id.datePicker);
            this.timepicker = (TimePicker) rootView.findViewById(R.id.timepicker);
        }

    }

    private class ViewHolderDia {
        public View rootView;
        public TextView tv_close;
        public Button btn_aff;

        public ViewHolderDia(View rootView) {
            this.rootView = rootView;
            this.tv_close = (TextView) rootView.findViewById(R.id.tv_close);
            this.btn_aff = (Button) rootView.findViewById(R.id.btn_aff);
        }

    }
}
