package com.dlwx.wisdomschool.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.base.BaseRecrviewAdapter;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.NewAddLableActivity;
import com.dlwx.wisdomschool.adapter.AddLableAdapter;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.bean.TagListBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 个人标签
 */
public class PersionLableFragment extends BaseFragment {
    @BindView(R.id.rev_view)
    SwipeMenuRecyclerView revView;
    Unbinder unbinder;
    private List<TagListBean.BodyBean> body;
    private AddLableAdapter addLableAdapter;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_persion_lable;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        View foodView = LayoutInflater.from(ctx).inflate(R.layout.food_persionlable,null);
        foodView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ctx,NewAddLableActivity.class));
            }
        });
        revView.addFooterView(foodView);
    }

    @Override
    protected void initDate() {
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        revView.setLayoutManager(manager);


    }

    @Override
    public void onResume() {

        getPersionTag();
        super.onResume();
    }

    /**
     * 获取个人标签列表
     */
    private void getPersionTag() {
        Httptype =1;
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        map.put("type","2");
        mPreenter.fetch(map,true, HttpUrl.Signlist,HttpUrl.Signlist+Token+"1");
    }

    @Override
    protected void initListener() {
        revView.setSwipeMenuCreator(swipeMenuCreator);
        revView.setSwipeMenuItemClickListener(mMenuItemClickListener);


    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (Httptype == 1) {

            TagListBean tagListBean = gson.fromJson(s, TagListBean.class);
            if (tagListBean.getCode() == 200) {
                body = tagListBean.getBody();
                addLableAdapter = new AddLableAdapter(ctx, body);
                revView.setAdapter(addLableAdapter);
                addLableAdapter.setOnItemClickListener(new BaseRecrviewAdapter.OnItemClickListener() {
                    @Override
                    public void setOnClick(int position) {
                        Intent intent = new Intent();
                        intent.putExtra("tag",body.get(position).getSigname());
                        intent.putExtra("tagid",body.get(position).getId());
                        getActivity().setResult(10,intent);
                        getActivity().finish();
                    }
                });
            }else{
                Toast.makeText(ctx, tagListBean.getResult(), Toast.LENGTH_SHORT).show();
            }
        }else{
            BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
            if (backResultBean.getCode() == 200) {
                getPersionTag();
            }
            Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.delete);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            int height = DensityUtil.dip2px(ctx,130);


            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(ctx)
                        .setBackground(R.color.red)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。

//                SwipeMenuItem addItem = new SwipeMenuItem(ctx)
//                        .setBackground(R.color.red)
//                        .setText("添加")
//                        .setTextColor(Color.WHITE)
//                        .setWidth(width)
//                        .setHeight(height);
//                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
            }
        }
    };


    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            // 菜单在RecyclerView的Item中的Position。
            int menuPosition = menuBridge.getPosition();

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                Map<String,String> map = new HashMap<>();
                map.put("token",Token);
                Httptype = 2;
                map.put("id",body.get(menuPosition).getId());
                mPreenter.fetch(map,true,HttpUrl.DelteSign,"");
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(ctx, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };
    private int Httptype;

    public static class DensityUtil {

        /**
         * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
         */
        public static int dip2px(Context context, float dpValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
         */
        public static int px2dip(Context context, float pxValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }
    }
}
