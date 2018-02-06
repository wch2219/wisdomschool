package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.GroupAddMemberAdapter;
import com.dlwx.wisdomschool.bean.AllClassMemberBean;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.bean.GroupMemberListBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 添加群成员
 */
public class AddGroupMemberActivity extends BaseActivity implements ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rl_aff)
    RelativeLayout rlAff;
    @BindView(R.id.lv_list)
    ExpandableListView lvList;
    private String group_id;
    private List<AllClassMemberBean.BodyBean> body;
    private GroupAddMemberAdapter groupAddMemberAdapter;
    private GroupMemberListBean groupMemberListBean;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        group_id = intent.getStringExtra("group_id");
        groupMemberListBean = (GroupMemberListBean) intent.getSerializableExtra("body");
        setContentView(R.layout.activity_add_group_member);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText("添加群成员");
        int width = getWindowManager().getDefaultDisplay().getWidth();
        lvList.setIndicatorBounds(width - 100, width - 10);

        HttpType = 1;
        String classid = getIntent().getStringExtra("classid");
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        map.put("group_id",group_id);
        if (!classid.equals("0")) {
            map.put("classid",classid);
        }
       mPreenter.fetch(map,true,HttpUrl.getAllClassMember,"");
    }

    @Override
    protected void initListener() {
        lvList.setOnGroupClickListener(this);
        lvList.setOnChildClickListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick(R.id.rl_aff)
    public void onViewClicked() {
        classid = "";
        userids = "";
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("group_id", group_id);

        for (int i = 0; i < body.size(); i++) {
            AllClassMemberBean.BodyBean bodyBean = body.get(i);
            if (bodyBean.isCheck()) {
                classid = bodyBean.getCnid();
                map.put("classid",classid);
            }
        }
        if (TextUtils.isEmpty(classid)) {
            for (int i = 0; i < body.size(); i++) {
                List<AllClassMemberBean.BodyBean.AddUserBean> add_user = body.get(i).getAdd_user();
                for (int j = 0; j < add_user.size(); j++) {
                    AllClassMemberBean.BodyBean.AddUserBean addUserBean = add_user.get(j);
                    if (addUserBean.isCheck()) {
                        if (TextUtils.isEmpty(userids)) {
                            userids = addUserBean.getUserid();
                            classid = body.get(i).getCnid();
                        }else{
                            userids = userids + ","+addUserBean.getUserid();
                        }
                    }
                }
            }
            map.put("userids", userids);
            map.put("classid",classid);
        }
        HttpType = 2;
        mPreenter.fetch(map, true, HttpUrl.Group_addUser, "");

    }

    private String userids;
    private String classid;
    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 2) {
            BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
            if (backResultBean.getCode() == 200) {
                //群主加人调用此方法
                userids = "";
                for (int i = 0; i < body.size(); i++) {
                    List<AllClassMemberBean.BodyBean.AddUserBean> add_user = body.get(i).getAdd_user();
                    for (int j = 0; j < add_user.size(); j++) {
                        AllClassMemberBean.BodyBean.AddUserBean addUserBean = add_user.get(j);
                        if (addUserBean.isCheck()) {
                            if (TextUtils.isEmpty(userids)) {
                                userids = addUserBean.getUserid();
                            }else{
                                userids = userids + ","+addUserBean.getUserid();
                            }
                        }
                    }
                }
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().groupManager().addUsersToGroup(group_id,userids.split(",") );//需异步处理
                            handler.sendEmptyMessage(0);
                        } catch (HyphenateException e) {

                            e.printStackTrace();
                        }
                    }
                }.start();
            }
            Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
        }else{
            //班级和成员数据列表
            AllClassMemberBean allClassMemberBean = gson.fromJson(s, AllClassMemberBean.class);
            if (allClassMemberBean.getCode() == 200) {
                body = allClassMemberBean.getBody();

                groupAddMemberAdapter = new GroupAddMemberAdapter(ctx, this.body);
                lvList.setAdapter(groupAddMemberAdapter);
            }
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

        AllClassMemberBean.BodyBean bodyBean = body.get(groupPosition);
        AllClassMemberBean.BodyBean.AddUserBean addUserBean = bodyBean.getAdd_user().get(childPosition);
        addUserBean.setCheck(!addUserBean.isCheck());
        int checkNum = 0;
        for (int i = 0; i < bodyBean.getAdd_user().size(); i++) {
            AllClassMemberBean.BodyBean.AddUserBean addUserBean1 = bodyBean.getAdd_user().get(i);
            if (addUserBean1.isCheck()) {
                checkNum++;
                if (checkNum == bodyBean.getAdd_user().size()) {
                    bodyBean.setCheck(true);
                }else{
                    bodyBean.setCheck(false);
                }
            }
        }
        groupAddMemberAdapter.notifyDataSetChanged();
        return false;
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent();
            intent.putExtra("classid",classid);
            setResult(1,intent);
            finish();
        }
    };
    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

        return false;
    }
}
