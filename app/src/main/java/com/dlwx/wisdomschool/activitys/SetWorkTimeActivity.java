package com.dlwx.wisdomschool.activitys;

import android.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.SetTimeAdapter;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.bean.SetWorkTimeBean;
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
 * 设置工作时间
 */
public class SetWorkTimeActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.swich)
    Switch swich;
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_end)
    TextView tvEnd;
    @BindView(R.id.gv_list)
    GridView gvList;
    @BindView(R.id.btn_saveset)
    Button btnSaveset;
    private List<SetWorkTimeBean> alltimes = new ArrayList<>();
    private View view;
    private String hour;
    private String min;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_set_work_time);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("设置工作时间");
        initTabBar(toolBar);
        String[] strs = {"日", "一", "二", "三", "四", "五", "六"};
        for (int i = 0; i < strs.length; i++) {
            SetWorkTimeBean timeBean = new SetWorkTimeBean();
            timeBean.setDay(strs[i]);
            alltimes.add(timeBean);
        }

        gvList.setAdapter(new SetTimeAdapter(ctx, alltimes));
    }

    @Override
    protected void initListener() {


    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.tv_start, R.id.tv_end, R.id.btn_saveset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start:
                startorend = 0;
                showPopu();
                break;
            case R.id.tv_end:
                startorend = 1;
                showPopu();
                break;
            case R.id.btn_saveset:
                submit();

                break;
        }
    }


    private void submit() {
        String startTime = tvStart.getText().toString().trim();
        String endTime = tvEnd.getText().toString().trim();
        String work_weekday = null;
        for (int i = 0; i < alltimes.size(); i++) {
            SetWorkTimeBean timeBean = alltimes.get(i);
            if (timeBean.isCheck()) {
                if (TextUtils.isEmpty(work_weekday)) {
                    work_weekday = timeBean.getDay();
                } else {
                    work_weekday = work_weekday + "," + timeBean.getDay();
                }
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("work_starttime", startTime);
        map.put("work_endtime", endTime);
        map.put("work_weekday", work_weekday);
        mPreenter.fetch(map, false, HttpUrl.setWorkTime, "");

    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
        if (backResultBean.getCode() == 200) {
            finish();
        }
        Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
    }

    private int startorend = 0;//0是开始1是结束
    private String[] hours = new String[25];
    private String[] mins = new String[61];

    private void showPopu() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_selete_time, null);
        ViewHolder diaVh = new ViewHolder(view);
        for (int i = 0; i <= 24; i++) {
            if (i < 10) {
                hours[i] = "0" + i;
            } else {
                hours[i] = i + "";
            }
        }
        for (int i = 0; i <= 60; i++) {
            if (i < 10) {
                mins[i] = "0" + i;
            } else {
                mins[i] = i + "";
            }
        }
        diaVh.numPickerhour.setMaxValue(hours.length - 1);
        diaVh.numPickerhour.setMinValue(0);
        diaVh.numPickerhour.setDisplayedValues(hours);

        diaVh.numPickermin.setMaxValue(mins.length - 1);
        diaVh.numPickermin.setMinValue(0);
        diaVh.numPickermin.setDisplayedValues(mins);

        diaVh.tv_comple.setOnClickListener(this);
        diaVh.tv_close.setOnClickListener(this);

        diaVh.numPickerhour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                wch(hours[i] + ":" + hours[i1]);
                hour = hours[i1];
            }

        });

        diaVh.numPickerhour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                wch(mins[i] + ":" + mins[i1]);
                min = mins[i1];
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(view);
        show = builder.show();

    }

    @Override
    public void onClick(View v) {
        switch (view.getId()) {
            case R.id.tv_comple:
                show.dismiss();
                if (startorend == 0) {//开始时间
                    tvStart.setText(hour + ":" + min);
                } else {
                    tvEnd.setText(hour + ":" + min);
                }
                break;
            case R.id.tv_close:
                show.dismiss();
                break;
        }
    }

    private AlertDialog show;

    private class ViewHolder {
        public View rootView;
        public NumberPicker numPickerhour;
        public NumberPicker numPickermin;
        public TextView tv_comple;
        public TextView tv_close;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.numPickerhour = (NumberPicker) rootView.findViewById(R.id.numPickerhour);
            this.numPickermin = (NumberPicker) rootView.findViewById(R.id.numPickermin);
            this.tv_comple = rootView.findViewById(R.id.tv_comple);
            this.tv_close = rootView.findViewById(R.id.tv_close);

        }
    }
}
