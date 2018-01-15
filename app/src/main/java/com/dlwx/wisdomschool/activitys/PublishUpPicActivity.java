package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.PublishUpPicPageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 发布综合素质
 */
public class PublishUpPicActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.vp_view)
    ViewPager vpView;
    private int[] contents = {R.string.publishcontent1, R.string.publishcontent2, R.string.publishcontent3, R.string.publishcontent4};
    private String[] titles = {"品质发展", "动手能力", "热爱学习", "体育活动"};
    private final int[] intArray = {R.color.publist1, R.color.publist2, R.color.publist3, R.color.publist4};
    private ArrayList<Integer> body;

    @Override
    protected void initView() {
        body = getIntent().getIntegerArrayListExtra("body");
        setContentView(R.layout.activity_publish_up_pic);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("发布综合素质");
        initTabBar(toolBar);

        List<String> titleList = new ArrayList<>();
        List<Integer> contentList = new ArrayList<>();
        List<Integer> intteArrList = new ArrayList<>();
        for (int i = 0; i < body.size(); i++) {
            titleList.add(titles[body.get(i) - 1]);
            contentList.add(contents[body.get(i) - 1]);
            intteArrList.add(intArray[body.get(i) - 1]);
        }
        PublishUpPicPageAdapter publishUpPicPageAdapter = new PublishUpPicPageAdapter(ctx, titleList, contentList, intteArrList);
        vpView.setAdapter(publishUpPicPageAdapter);
        publishUpPicPageAdapter.setSkiAddPicBackListener(new PublishUpPicPageAdapter.SkiAddPicBackListener() {
            @Override
            public void back(String tagName, int position) {
                tagname = tagName;
                tagId = body.get(position);
                Intent intent = new Intent(ctx, AllPicActivity.class);
                intent.putExtra("MAXNUM", 3);
                startActivityForResult(intent, 2);
            }
        });
    }


    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }



    private String tagname;
    private Integer tagId;//发布的标签

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
