package com.dlwx.wisdomschool.activitys;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyGridView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.GroupMemberListAdapter;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.bean.GroupMemberListBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.dlwx.wisdomschool.utiles.SpUtiles;
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
 * 群成员信息
 */
public class GroupMemberManageActivity extends BaseActivity implements AdapterView.OnItemClickListener, GroupMemberListAdapter.DeleteListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.gv_list)
    MyGridView gvList;
    @BindView(R.id.tv_groupname)
    TextView tvGroupname;
    @BindView(R.id.tv_dissolvegroup)
    TextView tvDissolvegroup;
    @BindView(R.id.tv_mynickname)
    TextView tvMynickname;
    @BindView(R.id.tv_cleanhis)
    TextView tvCleanhis;
    @BindView(R.id.btn_back)
    Button btnBack;
    private String group_id;
    private List<GroupMemberListBean.BodyBean> body;
    private GroupMemberListAdapter groupMemberListAdapter;
    private String classid;
    private String groupname;
    private GroupMemberListBean groupMemberListBean;

    @Override
    protected void initView() {
        group_id = getIntent().getStringExtra("group_id");
        classid = getIntent().getStringExtra("classid");
        groupname = getIntent().getStringExtra("groupname");
        setContentView(R.layout.activity_group_member_manage);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText("聊天信息");
        tvGroupname.setText(groupname);
    }

    @Override
    protected void onResume() {
        getMemberList();
        super.onResume();
    }

    private void getMemberList() {
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        HttpType = 1;
        map.put("group_id", group_id);
        mPreenter.fetch(map, true, HttpUrl.GroupMemberList, HttpUrl.GroupMemberList + Token + group_id);
    }

    @Override
    protected void initListener() {
        gvList.setOnItemClickListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.tv_dissolvegroup, R.id.tv_cleanhis, R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_dissolvegroup://解散群
                showDia();
                break;
            case R.id.tv_cleanhis://清除聊天纪录
                EMClient.getInstance().chatManager().deleteConversation(group_id, true);
                Toast.makeText(ctx, "清除完成", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_back://退出群
                break;
        }
    }

    //解散群
    @SuppressLint("NewApi")
    private void showDia() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_jurisdi, null);
        builder.setView(view);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_mess = view.findViewById(R.id.tv_mess);
        TextView tv_close = view.findViewById(R.id.tv_close);
        Button btn_aff = view.findViewById(R.id.btn_aff);
        final AlertDialog show = builder.show();
        tv_title.setText("确定要解散群聊吗？");
        tv_mess.setText("现在群里一共有" + body.size() + "个成员，\n你真的忍心解散本群吗");
        btn_aff.setText("确认");
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
            }
        });
        btn_aff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
               handler.sendEmptyMessage(1);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == body.size() + 1) {//删除
            isDelete = !isDelete;
            groupMemberListAdapter.setShowDelete(isDelete);
        } else if (position == body.size()) {//添加群成员
            startActivityForResult(new Intent(ctx, AddGroupMemberActivity.class)
                    .putExtra("body",groupMemberListBean)
                    .putExtra("group_id",group_id).putExtra("classid",classid),1);
        }
    }

    private boolean isDelete;

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {

            memberList(s, gson);
        } else if (HttpType == 2) {
            BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
            if (backResultBean.getCode() == 200) {
                body.remove(deleposition);
                groupMemberListAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
            }
        }else if (HttpType == 3) {
            BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
            if (backResultBean.getCode() == 200) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().groupManager().destroyGroup(group_id);//需异步处理
                            handler.sendEmptyMessage(2);
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
                Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();

        }
    }

    private void memberList(String s, Gson gson) {
        groupMemberListBean = gson.fromJson(s, GroupMemberListBean.class);
        if (groupMemberListBean.getCode() == 200) {
            body = groupMemberListBean.getBody();
            String userId = sp.getString(SpUtiles.Userid, "");
            String otheruserid = body.get(0).getUserid();
            if (otheruserid.equals(userId)) {
                tvDissolvegroup.setVisibility(View.VISIBLE);
            }else{
                tvDissolvegroup.setVisibility(View.GONE);
            }
            for (int i = 0; i < body.size(); i++) {
                GroupMemberListBean.BodyBean bodyBean = body.get(i);
                if (userId.equals(bodyBean.getUserid())) {
                    tvMynickname.setText(bodyBean.getNickname());
                }
            }
            if (body.size() == 1) {
                classid = "0";
            }
            groupMemberListAdapter = new GroupMemberListAdapter(ctx, body, userId);
            groupMemberListAdapter.setDeleteListener(this);
            gvList.setAdapter(groupMemberListAdapter);
        } else {
            Toast.makeText(ctx, groupMemberListBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void delete(final int position) {
        eguid = body.get(position).getEguid();
        deleposition = position;
        new Thread() {
            @Override
            public void run() {
                //把username从群组里删除
                try {
                    EMClient.getInstance().groupManager().removeUserFromGroup(group_id, body.get(position).getUserid());//需异步处理
                    handler.sendEmptyMessage(0);
                } catch (HyphenateException e) {
                    wch(e);
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private String eguid;
    private int deleposition;
    private Handler handler = new Handler() {

        private Map<String, String> map;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    map = new HashMap<>();
                    map.put("token", Token);
                    map.put("eguid", eguid);
                    HttpType = 2;
                    mPreenter.fetch(map, true, HttpUrl.GroupDeleteMember, "");
                    break;
                case 1:
                    map = new HashMap<>();
                    map.put("token", Token);
                    map.put("group_id", group_id);
                    HttpType = 3;
                    mPreenter.fetch(map, true, HttpUrl.DeleteGroup, "");
                    break;
                case 2:
                    finish();
                    close();
                    break;
            }

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
         switch (requestCode){
                    case 1:
                        classid = data.getStringExtra("classid");//重新赋值班级id
                        break;
                }
    }
}
