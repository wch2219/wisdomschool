package com.dlwx.wisdomschool.activitys;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyGridView;
import com.dlwx.baselib.view.MyListView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.PublishUpPicPageAdapter;
import com.dlwx.wisdomschool.adapter.SynthesizeFullBottomAdapter;
import com.dlwx.wisdomschool.adapter.SynthesizeFullTopAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 综合素质完整版
 */
public class SynthesizeFullActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_list)
    MyListView lvList;
    @BindView(R.id.gv_list)
    MyGridView gvList;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_synthesize_full);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("综合素质完整版");
        initTabBar(toolBar);
        lvList.setAdapter(new SynthesizeFullTopAdapter(ctx));
        gvList.setAdapter(new SynthesizeFullBottomAdapter(ctx));
    }

    @Override
    protected void initListener() {
        gvList.setOnItemClickListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i != 8) {
            showPopu();
        }
    }

    private void showPopu() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_synthesizefull, null);
        ViewHolder popovh = new ViewHolder(view);
        final PopupWindow popupWindow = new PopupWindow(view, w_screen - 40, h_screen - 80);
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
        popovh.vp_view.setAdapter(new PublishUpPicPageAdapter(ctx));
        popupWindow.showAtLocation(tvTitle, Gravity.CENTER, 0, 0);

    }

    private class ViewHolder {
        public View rootView;
        public ViewPager vp_view;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.vp_view = (ViewPager) rootView.findViewById(R.id.vp_view);
        }

    }
}
