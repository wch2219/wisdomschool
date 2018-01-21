package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.webkit.WebView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.LoadWEBUtiles;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.AgeWeekBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 0到19岁周刊
 */
public class AgeWeeklyActivity extends BaseActivity {
    private String age;
    //    @BindView(R.id.tv_title)
//    TextView tvTitle;
//    @BindView(R.id.tool_bar)
//    Toolbar toolBar;
//    @BindView(R.id.et_seach)
//    EditText etSeach;
//    @BindView(R.id.lv_list)
//    ListView lvList;
//    @BindView(R.id.refreshLayout)
//    SmartRefreshLayout refreshLayout;
//    private ViewHolderHead vhHead;
//    @BindView(R.id.iv_shape)
//    ImageView iv_shape;
    @BindView(R.id.webview)
    WebView webView;
    @Override
    protected void initView() {
        Intent intent = getIntent();
        age = intent.getStringExtra("age");
        setContentView(R.layout.activity_age_weekly);
        ButterKnife.bind(this);
//       initrefresh(refreshLayout,true);
//        View headView = LayoutInflater.from(ctx).inflate(R.layout.head_ageweekly, null);
//        vhHead = new ViewHolderHead(headView);
//        lvList.addHeaderView(headView);
    }

    @Override
    protected void initData() {
//        initTabBar(toolBar);
//        vhHead.gv_list.setAdapter(new AgeWeekHeadAdapter(ctx));
//        lvList.setAdapter(new AgeWeeklyAdapter(ctx));

        Map<String,String> map =  new HashMap<>();
        map.put("token",Token);
        map.put("age",age);
        mPreenter.fetch(map,false, HttpUrl.AgeWeek,"");
    }

    @Override
    protected void initListener() {
//        refreshLayout.setRefreshHeader(new FalsifyHeader(ctx));
//        //设置 Footer 为 球脉冲
//        refreshLayout.setRefreshFooter(new FalsifyFooter(ctx));
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000);
//
//            }
//        });
//        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadmore();
//
//            }
//        });
//        lvList.setOnItemClickListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

//
//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        Intent intent = new Intent(ctx, WeeklyDescActivity.class);
////        new PopuShareUtiles(ctx,etSeach);
//    }


//    @OnClick(R.id.iv_shape)
//    public void onViewClicked() {
//
//    }
//
//    private class ViewHolderHead {
//        public View rootView;
//        public MyGridView gv_list;
//
//        public ViewHolderHead(View rootView) {
//            this.rootView = rootView;
//            this.gv_list = (MyGridView) rootView.findViewById(R.id.gv_list);
//        }
//
//    }


    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        AgeWeekBean ageWeekBean = gson.fromJson(s, AgeWeekBean.class);
        if (ageWeekBean.getCode() == 200) {
            List<AgeWeekBean.BodyBean> body = ageWeekBean.getBody();
            if (body != null) {
                if (body.size() != 0) {

                    AgeWeekBean.BodyBean bodyBean = body.get(0);
                    LoadWEBUtiles webUtiles = new LoadWEBUtiles(ctx);
                    webUtiles.setListViewData(bodyBean.getUrl(),webView,null);
                }
            }else{

            }
        }
    }
}
