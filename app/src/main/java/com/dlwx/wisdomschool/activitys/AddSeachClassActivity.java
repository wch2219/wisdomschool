package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.bean.FindClassBean;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.dlwx.wisdomschool.utiles.SpUtiles;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 加入班级
 */
public class AddSeachClassActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.btn_cheack)
    Button btnCheack;
    @BindView(R.id.et_class)
    EditText et_class;
    @BindView(R.id.tv_classnum)
    TextView tvClassnum;
    @BindView(R.id.tv_teach)
    TextView tvTeach;
    @BindView(R.id.tv_classcode)
    TextView tvClasscode;
    @BindView(R.id.btn_addclass)
    Button btnAddclass;
    @BindView(R.id.ll_seachresult)
    LinearLayout llSeachresult;
    @BindView(R.id.tv_kefu)
    TextView tv_kefu;
    @BindView(R.id.iv_pic)
    ImageView iv_pic;
    private PopupWindow popupWindow;
    private String cnid;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_add_seach_class);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            tvTitle.setText("加入一个班级");
            initTabBar(toolBar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.btn_cheack, R.id.btn_addclass, R.id.tv_kefu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cheack:
                String classcode = et_class.getText().toString().trim();
                if (TextUtils.isEmpty(classcode)) {
                    Toast.makeText(ctx, "班级号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                inputhind(iv_pic);

                checkClass();
                break;
            case R.id.btn_addclass:
                showPopu();
                break;
            case R.id.tv_kefu:

                break;

        }
    }
    /**
     * 查找班级
     */
    private void checkClass() {
        String seach = et_class.getText().toString().trim();
        if (TextUtils.isEmpty(seach)) {
            Toast.makeText(ctx, "请输入目标班级班级号", Toast.LENGTH_SHORT).show();
            return;
        }
        HttpType = 1;
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        map.put("code",seach);
        mPreenter.fetch(map,true, HttpUrl.findClass,"");
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch("返回"+s);
        Gson gson = new Gson();
        if (HttpType == 1) {


            checkClass(s, gson);
        }else {
            BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
            if (backResultBean.getCode() == 200) {
                finish();
            }
            Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }
    private void checkClass(String s, Gson gson) {
        FindClassBean findClassBean = gson.fromJson(s, FindClassBean.class);
        if (findClassBean.getCode() == 200) {
            List<FindClassBean.BodyBean> body = findClassBean.getBody();
            if (body != null&& body.size()!=0) {
                llSeachresult.setVisibility(View.VISIBLE);
                FindClassBean.BodyBean bodyBean = body.get(0);
                Glide.with(ctx).load(bodyBean.getClass_pic()).into(iv_pic);
                tvClassnum.setText(bodyBean.getClass_name());
                tvClasscode.setText(bodyBean.getClass_no());
                tvTeach.setText(bodyBean.getTotal_user());
                tvTeach.setText(bodyBean.getTeacher_name());
                cnid = bodyBean.getCnid();
            }else{
                llSeachresult.setVisibility(View.GONE);
                Toast.makeText(ctx, findClassBean.getResult(), Toast.LENGTH_SHORT).show();
            }
        }else{
            llSeachresult.setVisibility(View.GONE);
            Toast.makeText(ctx, findClassBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }
    private void showPopu() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_addclass, null);
        ViewHolder vh = new ViewHolder(view);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
        popupWindow.showAtLocation(tv_kefu, Gravity.BOTTOM, 0, 0);

    }

    private class ViewHolder implements View.OnClickListener {
        public View rootView;
        public ImageView iv_close;
        public TextView tv_oneself;
        public TextView tv_patriarch;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_close = (ImageView) rootView.findViewById(R.id.iv_close);
            this.tv_oneself = (TextView) rootView.findViewById(R.id.tv_oneself);
            this.tv_patriarch = (TextView) rootView.findViewById(R.id.tv_patriarch);
            this.iv_close.setOnClickListener(this);
            this.tv_oneself.setOnClickListener(this);
            this.tv_patriarch.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            popupWindow.dismiss();
            switch (view.getId()) {
                case R.id.iv_close:

                    break;
                case R.id.tv_oneself:
                    Map<String,String> map = new HashMap<>();

                    map.put("urid",sp.getString(SpUtiles.Userid,""));
                    joinClass(map);
                    break;
                case R.id.tv_patriarch:
                    Intent intent = new Intent(ctx,SeleteOtherIdActivity.class);
                    intent.putExtra("title","设置姓名和称呼");
                    startActivityForResult(intent,1);
                    break;

            }
        }
    }

    /**
     * 加入班级
     * @param map
     */
    private void joinClass(Map<String, String> map) {
        map.put("token",Token);
        map.put("cid",cnid);
        HttpType =2;
        mPreenter.fetch(map,false,HttpUrl.JoinClass,"");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         switch (requestCode){
                    case 1:
                        if (data == null) {
                            return;
                        }
                        String name = data.getStringExtra("name");
                        String named = data.getStringExtra("named");//称呼
                        Map<String,String> map = new HashMap<>();
                        map.put("student_name",name);
                        map.put("role_identity",named);
                        joinClass(map);
                        break;
                }
    }
}
