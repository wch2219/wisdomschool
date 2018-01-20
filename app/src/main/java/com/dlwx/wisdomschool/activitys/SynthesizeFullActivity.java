package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
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
import com.dlwx.wisdomschool.bean.EightSignBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

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
    private List<EightSignBean.BodyBean> body = new ArrayList<>();
    private SynthesizeFullBottomAdapter synthesizeFullBottomAdapter;
    private SynthesizeFullTopAdapter synthesizeFullTopAdapter;
    private String[] stringArray;
    private int[] contents = {R.string.publishcontent1, R.string.publishcontent2, R.string.publishcontent3, R.string.publishcontent4,
            R.string.publishcontent1, R.string.publishcontent2, R.string.publishcontent3, R.string.publishcontent4};
    private final int[] intArray = {R.color.sign1, R.color.sign2, R.color.sign3, R.color.sign4,R.color.sign5, R.color.sign6, R.color.sign7, R.color.sign8};
    private List<String> titleList = new ArrayList<>();
    private List<Integer> contentList;
    private List<Integer> intteArrList;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_synthesize_full);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("综合素质完整版");
        initTabBar(toolBar);
        stringArray = ctx.getResources().getStringArray(R.array.eightsynther);
        contentList = new ArrayList<>();
        intteArrList = new ArrayList<>();
        for (int i = 0; i < stringArray.length; i++) {
            titleList.add(stringArray[i]);
            contentList.add(contents[i]);
            intteArrList.add(intArray[i]);
        }
        synthesizeFullTopAdapter = new SynthesizeFullTopAdapter(ctx, body,stringArray);
        lvList.setAdapter(synthesizeFullTopAdapter);
        synthesizeFullBottomAdapter = new SynthesizeFullBottomAdapter(ctx, body,stringArray);
        gvList.setAdapter(synthesizeFullBottomAdapter);
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        mPreenter.fetch(map,true, HttpUrl.EightSign,HttpUrl.EightSign+Token);
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
            showPopu(i);
        }else{
            //todo
        }
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        EightSignBean eightSignBean = gson.fromJson(s, EightSignBean.class);
        if (eightSignBean.getCode() == 200) {
            body.addAll(eightSignBean.getBody());
            synthesizeFullBottomAdapter.notifyDataSetChanged();
            synthesizeFullTopAdapter.notifyDataSetChanged();
        }
    }

    private void showPopu(int curr) {
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
        PublishUpPicPageAdapter publishUpPicPageAdapter = new PublishUpPicPageAdapter(ctx, titleList, contentList, intteArrList);
        popovh.vp_view.setAdapter(publishUpPicPageAdapter);
        popovh.vp_view.setCurrentItem(curr);
        popupWindow.showAtLocation(tvTitle, Gravity.CENTER, 0, 0);
        publishUpPicPageAdapter.setSkiAddPicBackListener(new PublishUpPicPageAdapter.SkiAddPicBackListener() {
            @Override
            public void back(String tagName, int position) {
                tagname = tagName;
                tagId = body.get(position)+"";
                Intent intent = new Intent(ctx, AllPicActivity.class);

                startActivityForResult(intent, 2);
            }
        });
        publishUpPicPageAdapter.setDisspopuResultListener(new PublishUpPicPageAdapter.DisspopuResultListener() {
            @Override
            public void diss() {
                popupWindow.dismiss();
            }
        });
    }
    private String tagname,tagId;
    private class ViewHolder {
        public View rootView;
        public ViewPager vp_view;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.vp_view = (ViewPager) rootView.findViewById(R.id.vp_view);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {

            return;
        }
        switch (requestCode) {
            case 2:
                ArrayList<String> images = data.getStringArrayListExtra("images");
                Intent intent = new Intent(ctx, PublishGroupUpActivity.class);
                intent.putStringArrayListExtra("images", images);
                intent.putExtra("tagname", tagname);
                intent.putExtra("tagId", tagId+"");
                startActivity(intent);
                finish();
                break;
        }
    }
}
