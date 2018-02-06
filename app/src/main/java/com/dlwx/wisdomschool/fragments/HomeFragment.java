package com.dlwx.wisdomschool.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.base.BaseRecrviewAdapter;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.SetBanner;
import com.dlwx.baselib.view.MyGridView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.AgeWeeklyActivity;
import com.dlwx.wisdomschool.activitys.PatriarchExamActivity;
import com.dlwx.wisdomschool.activitys.WebUrlActivity;
import com.dlwx.wisdomschool.adapter.HomeItemAdapter;
import com.dlwx.wisdomschool.adapter.HomeTitleAdapter;
import com.dlwx.wisdomschool.bean.HomelistBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.dlwx.wisdomschool.utiles.SpUtiles;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment implements BaseRecrviewAdapter.OnItemClickListener {
    @BindView(R.id.rv_recyclerview)
    RecyclerView rvRecyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    @BindView(R.id.banner)
    Banner banner;
    Unbinder unbinder1;
    @BindView(R.id.gv_list)
    MyGridView gvList;
    @BindView(R.id.et_seach)
    EditText etSeach;
    @BindView(R.id.btn_seach)
    Button btnSeach;
    Unbinder unbinder2;
    @BindView(R.id.ll_type1)
    LinearLayout llType1;
    @BindView(R.id.ll_type2)
    LinearLayout llType2;
    @BindView(R.id.ll_age)
    LinearLayout llAge;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    Unbinder unbinder3;
    private String[] strs;
    private HomeTitleAdapter titleAdapter;
    private List<HomelistBean.BodyBean.ListBean> list;
    private Map<String, String> map;
    private PopupWindow popupWindow;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initDate() {
        initrefresh(refreshLayout, true);
        strs = ctx.getResources().getStringArray(R.array.hometitle);
        int [] pics = {R.mipmap.icon_home1,R.mipmap.icon_home2,R.mipmap.icon_home3,R.mipmap.icon_home4,R.mipmap.icon_home5
            ,R.mipmap.icon_home6,R.mipmap.icon_home7,R.mipmap.icon_home8};
        titleAdapter = new HomeTitleAdapter(ctx, strs,pics);
        gvList.setAdapter(titleAdapter);
        map = new HashMap<>();
        getData(map);
    }

    @Override
    public void downOnRefresh() {
        map = new HashMap<>();
        getData(map);
    }

    private void getData(Map<String, String> map) {
        map.put("token", Token);
        mPreenter.fetch(map, false, HttpUrl.HomeList, HttpUrl.HomeList + Token);
    }

    @Override
    public void loadmore() {
//        getData(map);
    }

    @Override
    protected void initListener() {
//        titleAdapter.setOnItemClickListener(titAdapter);
        gvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0://父母自修
                        startActivity(new Intent(ctx, WebUrlActivity.class)
                                .putExtra("unTitle",true)
                                .putExtra("url","http://39.107.74.235/school/web/weekly_classify.html?age=1&token="+sp.getString(SpUtiles.Token,"")));
                        break;
                    case 1://亲子专栏
                        startActivity(new Intent(ctx, WebUrlActivity.class)
                                .putExtra("unTitle",true)
                                .putExtra("url","http://39.107.74.235/school/web/weekly_classify.html?age=2&token="+sp.getString(SpUtiles.Token,"")));
                        break;
                    case 2://育兒課堂

                        startActivity(new Intent(ctx, WebUrlActivity.class)
                                .putExtra("unTitle",true)
                                .putExtra("url","http://39.107.74.235/school/web/raise_class.html?token="+sp.getString(SpUtiles.Token,"")));
                        break;
                    case 3://书城

                        startActivity(new Intent(ctx, WebUrlActivity.class)
                                .putExtra("unTitle",true)
                                .putExtra("url","http://39.107.74.235/school/web/book_list.html?token="+sp.getString(SpUtiles.Token,"")));
                        break;
                    case 4://在线教育
                        Toast.makeText(ctx, "功能正在开发，敬请期待...", Toast.LENGTH_SHORT).show();
                        break;
                    case 5://视频
                        Toast.makeText(ctx, "功能正在开发，敬请期待...", Toast.LENGTH_SHORT).show();
                        break;
                    case 6://问答
                        Toast.makeText(ctx, "功能正在开发，敬请期待...", Toast.LENGTH_SHORT).show();
                        break;
                    case 7://星级考试
                        int anInt = sp.getInt(SpUtiles.TeacherOrPatriarch, 1);
                        if (anInt == 1) {//老师
                            Toast.makeText(ctx, "您的角色是老师，不用参加考试", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        startActivity(new Intent(ctx, PatriarchExamActivity.class));
                        break;
                }
            }
        });
    }
    
    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    /**
     * 条目点击事件
     */
    private BaseRecrviewAdapter.OnItemClickListener titAdapter = new BaseRecrviewAdapter.OnItemClickListener() {
        @Override
        public void setOnClick(int position) {
            //TODO
            Intent intent = new Intent(ctx, AgeWeeklyActivity.class);
            intent.putExtra("age", position + 1 + "");
            startActivity(intent);
        }
    };

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        HomelistBean homelistBean = gson.fromJson(s, HomelistBean.class);
        if (homelistBean.getCode() == 200) {
            HomelistBean.BodyBean body = homelistBean.getBody();
            List<HomelistBean.BodyBean.BannerBean> bannerBeans = body.getBanner();
            List<String> pics = new ArrayList<>();
            for (int i = 0; i < bannerBeans.size(); i++) {
                pics.add(bannerBeans.get(i).getImgid());
            }
            //设置轮播
            SetBanner.startBanner(ctx, banner, pics);

            list = body.getList();
            LinearLayoutManager manager = new LinearLayoutManager(ctx);
            manager.setOrientation(LinearLayout.VERTICAL);
            rvRecyclerview.setLayoutManager(manager);
            HomeItemAdapter homeItemAdapter = new HomeItemAdapter(ctx, list, refreshLayout);
            rvRecyclerview.setAdapter(homeItemAdapter);
            homeItemAdapter.setOnItemClickListener(this);
        } else {
            Toast.makeText(ctx, homelistBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setOnClick(int position) {
        //todo
        HomelistBean.BodyBean.ListBean listBean = list.get(position);
        Intent intent = new Intent(ctx, AgeWeeklyActivity.class);
        intent.putExtra("url", listBean.getUrl_detail());
        startActivity(intent);
    }


    @OnClick({R.id.ll_type1, R.id.ll_type2, R.id.ll_age, R.id.ll_time, R.id.btn_seach})
    public void onViewClicked(View view) {
        String[] strs;
        switch (view.getId()) {
            case R.id.ll_type1://1图文，2视频
                strs = new String[]{"图文", "视频"};
                showPopu(strs, 1);
                break;
            case R.id.ll_type2://类别（1情商2智商3简单4教育5成长）
                strs = new String[]{"情商", "智商", "简单", "教育", "成长"};
                showPopu(strs, 2);
                break;
            case R.id.ll_age://	年龄段（1-11）
                strs = new String[]{"父母自修", "婚恋", "0岁", "1岁", "幼儿园上", "幼儿园下", "小学上", "小学下", "初中", "高中", "大学"};
                showPopu(strs, 3);
                break;
            case R.id.ll_time://发布时间【1倒叙（默认）2正序】
                strs = new String[]{"倒叙（默认）", "正序"};
                showPopu(strs, 4);
                break;
            case R.id.btn_seach://搜索的内容
                String seach = etSeach.getText().toString().trim();
                if (TextUtils.isEmpty(seach)) {
                    Toast.makeText(ctx, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                map.put("seach", seach);
                getData(map);
                break;
        }
    }
    private int oldtype;
    private void showPopu(String[] strs, final int type) {
        ListView listView = new ListView(ctx);
        listView.setBackgroundColor(Color.WHITE);
        if (popupWindow != null) {
            if (oldtype == type) {
                popupWindow.dismiss();
                oldtype = 0;
                return;
            }
        }
        oldtype = type;
        popupWindow = new PopupWindow(listView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        listView.setAdapter(new ArrayAdapter<String>(ctx, R.layout.simple_list_item, strs));
        popupWindow.showAsDropDown(llType1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();
                switch (type) {
                    case 1://1图文，2视频
                        map.put("type", position + 1 + "");
                        getData(map);
                        break;
                    case 2://类别（1情商2智商3简单4教育5成长）
                        map.put("classid", position + 1 + "");
                        getData(map);
                        break;
                    case 3://	年龄段（1-11）
                        map.put("age", position + 1 + "");
                        getData(map);
                        break;
                    case 4://发布时间【1倒叙（默认）2正序】
                        map.put("time", position + 1 + "");
                        getData(map);
                        break;

                }
            }
        });
    }

}
