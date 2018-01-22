package com.dlwx.wisdomschool.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.LogUtiles;
import com.dlwx.baselib.view.MyListView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.activitys.AllPicActivity;
import com.dlwx.wisdomschool.activitys.PublishGroupUpActivity;
import com.dlwx.wisdomschool.activitys.RecordDescActivity;
import com.dlwx.wisdomschool.activitys.RecordVideoActivity;
import com.dlwx.wisdomschool.activitys.SynthesizeActivity;
import com.dlwx.wisdomschool.activitys.SynthesizeFullActivity;
import com.dlwx.wisdomschool.adapter.RecordListAdapter;
import com.dlwx.wisdomschool.adapter.RecordScreenAddAdapter;
import com.dlwx.wisdomschool.adapter.RecordScreenCreateAdapter;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.bean.MyAllClassBean;
import com.dlwx.wisdomschool.bean.PublishUpPiccheckBean;
import com.dlwx.wisdomschool.bean.RecordListBean;
import com.dlwx.wisdomschool.interfac.SoftKeyBoard;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.dlwx.wisdomschool.utiles.SlideCancelLoadPic;
import com.dlwx.wisdomschool.utiles.SpUtiles;
import com.dlwx.wisdomschool.views.PolaroidResizeLinearLayout;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

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
 * 成长纪录
 */
public class RecordFragment extends BaseFragment implements View.OnClickListener, RecordListAdapter.ShowInputWindowListener {
    @BindView(R.id.iv_screen)
    ImageView ivScreen;
    @BindView(R.id.fab_edit)
    Button fabEdit;
    @BindView(R.id.rl_synthesize)
    RelativeLayout rl_synthesize;
    @BindView(R.id.ll_seleteclass)
    LinearLayout ll_seleteclass;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.lv_list)
    ListView lv_list;
    @BindView(R.id.tv_classseach)
    TextView tv_classseach;
    @BindView(R.id.iv_face)
    ImageView ivFace;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_sendsms)
    TextView tvSendsms;
    @BindView(R.id.ll_bottominput)
    LinearLayout llBottominput;
    @BindView(R.id.poresize)
    PolaroidResizeLinearLayout poresize;
    Unbinder unbinder;

    private AlertDialog diashow;
    private PopupWindow popupWindow;
    private String type = "";
    private String class_no = "";
    private List<MyAllClassBean.BodyBean.CreateListBean> create_list;
    private List<MyAllClassBean.BodyBean.JoinListBean> join_list;
    private int createNum;
    private RecordScreenCreateAdapter createAdapter;
    private int addNum;
    private RecordScreenAddAdapter addAdapter;
    private ViewHolderDia vhDia;
    private RecordListAdapter recordListAdapter;
    private PopupWindow popushare;
    private List<RecordListBean.BodyBean> recordList;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_record;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initDate() {
        getDataList();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initListener() {
        initrefresh(refreshLayout, false);
        poresize.setOnResizeRelativeListener(new PolaroidResizeLinearLayout.OnResizeRelativeListener() {
            @Override
            public void OnResizeRelative(int w, int h, int oldw, int oldh) {
                DisplayMetrics outMetrics = new DisplayMetrics();
                int widthPixels = outMetrics.widthPixels;
                int dh = h - oldh;
                /*布局第一次inflate时,是由0变到填充界面的,所以这里要加上oldh>0,这样后面的改变都是键盘弹出和隐藏而带来,
                如果漆面键盘的弹出模式设置为WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN,则这个监听不会起作用,因为没有挤压布局,就不会有sizeChange*/
                if (oldh > 0) {
                    if (dh > 300) {
                        //TODO:do sth;
                        wch("隐藏");
                        SoftKeyBoard.softKeyBoardListener.showorhind(View.VISIBLE);
                        llBottominput.setVisibility(View.GONE);
                        fabEdit.setVisibility(View.VISIBLE);
                    } else if (dh < -300) {
                        wch("显示");
                        SoftKeyBoard.softKeyBoardListener.showorhind(View.GONE);
                        fabEdit.setVisibility(View.GONE);
                    }
                }
            }
        });
        SlideCancelLoadPic.startListener();

        lv_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    //空閒狀態
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                      LogUtiles.LogI("lege"+ "空閒狀態");
                        SlideCancelLoadPic.pauseRequestsListener.isPause(false);
                        recordListAdapter.notifyDataSetChanged();
                        break;
                    //滑動狀態
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        SlideCancelLoadPic.pauseRequestsListener.isPause(true);
                        LogUtiles.LogI("lege"+ "滑動狀態");
                        break;
                    //慣性
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        LogUtiles.LogI("lege"+  "慣性");
                        SlideCancelLoadPic.pauseRequestsListener.isPause(true);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecordListBean.BodyBean bodyBean = recordList.get(position);
                Intent intent = new Intent(ctx, RecordDescActivity.class);
                intent.putExtra("bodyBean",bodyBean);
                startActivity(intent);
            }
        });
    }

    @Override
    public void downOnRefresh() {
        page =1;
        getDataList();
    }

    @Override
    public void loadmore() {
        page++;
        getDataList();
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

    @OnClick({R.id.iv_screen, R.id.fab_edit, R.id.rl_synthesize, R.id.ll_seleteclass, R.id.tv_sendsms})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_screen://筛选
                showBottomPopu();
                break;
            case R.id.rl_synthesize://综合素质大提升
                skipSynther();
                break;
            case R.id.fab_edit://编辑
                showPopu();
                break;
//            case R.id.ll_list://纪录详情
//                startActivity(new Intent(ctx, RecordDescActivity.class));
//                break;
            case R.id.ll_seleteclass://选择班级纪录
                Httptype = 2;
                Map<String, String> map = new HashMap<>();
                map.put("token", Token);
                mPreenter.fetch(map, true, HttpUrl.Classroom, HttpUrl.Classroom + Token);
                break;
            case R.id.tv_sendsms:
                String content = etContent.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(ctx, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                llBottominput.setVisibility(View.GONE);
                mHandler.sendEmptyMessageDelayed(0, 200);
                Map<String, String> map1 = new HashMap<>();
                map1.put("token", Token);
                Httptype = 4;
                map1.put("id", recorid);
                if (otherId == null) {
                    map1.put("view_content", content);

                    mPreenter.fetch(map1, false, HttpUrl.RecordComment, "");
                } else {
                    map1.put("reply_content", content);
                    map1.put("id", otherId);
                    mPreenter.fetch(map1, false, HttpUrl.Recordreply, "");
                }
                break;

        }
    }

    /**
     * 综合素质
     */
    private void skipSynther() {
        Httptype = 3;
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        mPreenter.fetch(map, true, HttpUrl.CheckSign, HttpUrl.CheckSign + Token);
    }

    private int Httptype;

    /**
     *
     */
    private void showBottomPopu() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_record_screen, null);
        ViewHolderSceer viewHolderSceer = new ViewHolderSceer(view);
        if (TextUtils.isEmpty(type)) {
            viewHolderSceer.rb_all.setChecked(true);
        } else {
            viewHolderSceer.rb_myself.setChecked(true);
        }
        final PopupWindow popuSceer = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        viewHolderSceer.rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_all:
                        popuSceer.dismiss();
                        type = "";
                        page =1;
                        getDataList();
                        break;
                    case R.id.rb_myself:
                        popuSceer.dismiss();
                        type = "1";
                        page =1;
                        getDataList();
                        break;
                }
            }
        });
        viewHolderSceer.tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popuSceer.dismiss();
            }
        });
        popuSceer.setFocusable(true);
        popuSceer.setOutsideTouchable(true);
        backgroundAlpha(0.5f);
        popuSceer.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });
        popuSceer.showAtLocation(ivScreen, Gravity.BOTTOM, 0, 0);


    }

    private void showPopu() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_record_edit, null);
        ViewHolderPopu vhpopu = new ViewHolderPopu(view);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        backgroundAlpha(0.5f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
                popupWindow.dismiss();
            }
        });
        vhpopu.ll_close.setOnClickListener(this);
        vhpopu.tv_synthesize.setOnClickListener(this);
        vhpopu.tv_video.setOnClickListener(this);
        vhpopu.tv_picandtext.setOnClickListener(this);
        popupWindow.showAtLocation(ivScreen, Gravity.BOTTOM, 0, 0);
    }

    private void showDia() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.dia_recordscreen, null);
        vhDia = new ViewHolderDia(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(view);
        diashow = builder.show();
        vhDia.iv_close.setOnClickListener(this);
        vhDia.tv_mycreatenum.setOnClickListener(this);
        vhDia.tv_myaddnum.setOnClickListener(this);
        vhDia.ll_listprivacy.setOnClickListener(this);
        vhDia.ll_allrecord.setOnClickListener(this);
        vhDia.lv_listcreate.setOnItemClickListener(createListener);
        vhDia.lv_listadd.setOnItemClickListener(addListener);
        Glide.with(ctx).load(sp.getString(SpUtiles.Header_pic, "")).into(vhDia.iv_pic);
        if (create_list.size() > 2) {
            createNum = 2;
            vhDia.tv_mycreatenum.setVisibility(View.VISIBLE);
            vhDia.tv_mycreatenum.setText("显示全部" + create_list.size() + "个班级");
            createAdapter = new RecordScreenCreateAdapter(ctx, create_list, createNum);

        } else {
            createNum = create_list.size();
            vhDia.tv_mycreatenum.setVisibility(View.GONE);
            createAdapter = new RecordScreenCreateAdapter(ctx, create_list, createNum);
        }
        vhDia.lv_listcreate.setAdapter(createAdapter);

        if (join_list.size() > 2) {
            addNum = 2;
            vhDia.tv_myaddnum.setVisibility(View.VISIBLE);
            vhDia.tv_myaddnum.setText("显示全部" + join_list.size() + "个班级");
            addAdapter = new RecordScreenAddAdapter(ctx, join_list, addNum);
        } else {
            addNum = join_list.size();
            vhDia.tv_myaddnum.setVisibility(View.GONE);
            addAdapter = new RecordScreenAddAdapter(ctx, join_list, addNum);
        }
        vhDia.lv_listadd.setAdapter(addAdapter);
    }

    /**
     * 创建的班级条目点击监听
     */
    private AdapterView.OnItemClickListener createListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            MyAllClassBean.BodyBean.CreateListBean createListBean = create_list.get(i);
            boolean check = createListBean.isCheck();
            createListBean.setCheck(!check);
            createAdapter.notifyDataSetChanged();
            vhDia.cb_myself.setChecked(false);
            class_no = createListBean.getCnid();
            vhDia.cb_check.setChecked(false);
            tv_classseach.setText(createListBean.getClass_name());
            diashow.dismiss();
            type = "";
            page = 1;
            getDataList();
        }
    };
    /**
     * 加入的班级条目点击监听
     */
    private AdapterView.OnItemClickListener addListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            MyAllClassBean.BodyBean.JoinListBean joinListBean = join_list.get(i);
            boolean check = joinListBean.isCheck();
            joinListBean.setCheck(!check);
            addAdapter.notifyDataSetChanged();
            vhDia.cb_myself.setChecked(false);
            class_no = joinListBean.getCnid();
            vhDia.cb_check.setChecked(false);
            diashow.dismiss();
            type = "";
            page = 1;
            tv_classseach.setText(joinListBean.getClass_name());
            getDataList();
        }
    };

    /**
     * 选择自己改变状态
     */
    private void change() {
        for (int i = 0; i < create_list.size(); i++) {
            MyAllClassBean.BodyBean.CreateListBean createListBean = create_list.get(i);
            createListBean.setCheck(false);
        }
        for (int i = 0; i < join_list.size(); i++) {
            MyAllClassBean.BodyBean.JoinListBean joinListBean = join_list.get(i);
            joinListBean.setCheck(false);
        }
        createAdapter.notifyDataSetChanged();
        addAdapter.notifyDataSetChanged();
        if (!vhDia.cb_myself.isChecked()) {
            vhDia.cb_myself.setChecked(true);
        }
        diashow.dismiss();
        type = "1";
        class_no = "";
        page = 1;
        tv_classseach.setText("仅自己可见");
        getDataList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_synthesize:
                popupWindow.dismiss();
                publishGroupUp(1);
                break;
            case R.id.tv_video:
                popupWindow.dismiss();
                publishGroupUp(2);
                break;
            case R.id.tv_picandtext:
                popupWindow.dismiss();
                publishGroupUp(3);
                break;
            case R.id.ll_close:
                popupWindow.dismiss();
                break;
            case R.id.iv_close:
                diashow.dismiss();
                break;
            case R.id.tv_mycreatenum:

                createNum = create_list.size();
                vhDia.tv_mycreatenum.setVisibility(View.GONE);
                createAdapter = new RecordScreenCreateAdapter(ctx, create_list, createNum);
                vhDia.lv_listcreate.setAdapter(createAdapter);
                break;

            case R.id.tv_myaddnum:

                addNum = join_list.size();
                vhDia.tv_myaddnum.setVisibility(View.GONE);
                addAdapter = new RecordScreenAddAdapter(ctx, join_list, addNum);
                vhDia.lv_listadd.setAdapter(addAdapter);
                break;
            case R.id.ll_listprivacy:
                change();
                break;
            case R.id.ll_allrecord://查看全部成长纪录
                allRecord();
                break;

        }
    }

    /**
     * 查看全部成长纪录
     */
    private void allRecord() {
        for (int i = 0; i < create_list.size(); i++) {
            MyAllClassBean.BodyBean.CreateListBean createListBean = create_list.get(i);
            createListBean.setCheck(false);
        }
        for (int i = 0; i < join_list.size(); i++) {
            MyAllClassBean.BodyBean.JoinListBean joinListBean = join_list.get(i);
            joinListBean.setCheck(false);
        }
        createAdapter.notifyDataSetChanged();
        addAdapter.notifyDataSetChanged();
        vhDia.cb_check.setChecked(true);
        vhDia.cb_myself.setChecked(false);

        class_no = "";
        type = "";
        page = 1;
        diashow.dismiss();
        getDataList();
    }

    /**
     * 发布成长
     *
     * @param type 1综合素质 2视频纪录 3图文纪录
     */
    private void publishGroupUp(int type) {
        Intent intent;
        if (type == 1) {
            skipSynther();
        } else if (type == 3) {
            intent = new Intent(ctx, AllPicActivity.class);
            intent.putExtra("type", type);
            startActivityForResult(intent, 2);
        } else {
            intent = new Intent(ctx, RecordVideoActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    private class ViewHolderDia {
        public View rootView;
        public ImageView iv_close;
        public ImageView iv_pic;
        public CheckBox cb_check;
        public MyListView lv_listcreate;
        public TextView tv_mycreatenum;
        public TextView tv_myaddnum;
        public MyListView lv_listadd;
        public LinearLayout ll_listprivacy;
        public CheckBox cb_myself;
        public LinearLayout ll_allrecord;

        public ViewHolderDia(View rootView) {
            this.rootView = rootView;
            this.iv_close = (ImageView) rootView.findViewById(R.id.iv_close);
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.cb_check = (CheckBox) rootView.findViewById(R.id.cb_check);
            this.lv_listcreate = (MyListView) rootView.findViewById(R.id.lv_listcreate);
            this.tv_mycreatenum = (TextView) rootView.findViewById(R.id.tv_mycreatenum);
            this.tv_myaddnum = (TextView) rootView.findViewById(R.id.tv_myaddnum);
            this.lv_listadd = (MyListView) rootView.findViewById(R.id.lv_listadd);
            this.ll_listprivacy = (LinearLayout) rootView.findViewById(R.id.ll_listprivacy);
            this.ll_allrecord = (LinearLayout) rootView.findViewById(R.id.ll_allrecord);
            this.cb_myself = rootView.findViewById(R.id.cb_myself);

        }
    }

    public static class ViewHolderPopu {
        public View rootView;
        public TextView tv_synthesize;
        public TextView tv_video;
        public TextView tv_picandtext;
        public LinearLayout ll_close;

        public ViewHolderPopu(View rootView) {
            this.rootView = rootView;
            this.tv_synthesize = (TextView) rootView.findViewById(R.id.tv_synthesize);
            this.tv_video = (TextView) rootView.findViewById(R.id.tv_video);
            this.tv_picandtext = (TextView) rootView.findViewById(R.id.tv_picandtext);
            this.ll_close = (LinearLayout) rootView.findViewById(R.id.ll_close);
        }
    }
    private int page = 1;
    /**
     * 拉取数据
     */
    private void getDataList() {
        Httptype = 1;
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("type", type);
        map.put("class_no", class_no);
        map.put("page",page+"");
        mPreenter.fetch(map, true, HttpUrl.Growthrecord, HttpUrl.Growthrecord + Token + type + class_no);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (Httptype == 1) {

            recordList(s, gson);
        } else if (Httptype == 2) {//选择班级列表
            MyAllClassBean myAllClassBean = gson.fromJson(s, MyAllClassBean.class);
            if (myAllClassBean.getCode() == 200) {
                MyAllClassBean.BodyBean body = myAllClassBean.getBody();
                create_list = body.getCreate_list();
                join_list = body.getJoin_list();
                showDia();
            }
        } else if (Httptype == 3) {//检查综合素质提升
            PublishUpPiccheckBean publishUpPiccheckBean = gson.fromJson(s, PublishUpPiccheckBean.class);
            if (publishUpPiccheckBean.getCode() == 200) {
                List<Integer> body = publishUpPiccheckBean.getBody();
                if (body != null) {
                    if (body.size() != 0) {
                        Intent intent = new Intent(ctx, SynthesizeActivity.class);
                        intent.putIntegerArrayListExtra("body", (ArrayList<Integer>) body);
                        startActivity(intent);
                    } else {
                        startActivity(new Intent(ctx, SynthesizeFullActivity.class));
                    }
                } else {
                    startActivity(new Intent(ctx, SynthesizeFullActivity.class));
                }
            }
        } else if (Httptype == 4) {//评价数据回调
            BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
            if (backResultBean.getCode() == 200) {
                //todo 评论  回复
                getDataList();
            }
            Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();

        } else if (Httptype == 5) {
            BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
            if (backResultBean.getCode() == 200) {
                //todo 点赞

            }
            Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
        }else if (Httptype == 6) {//删除一条记录
            BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
            if (backResultBean.getCode() == 200) {
               recordList.remove(deleteposition);
               recordListAdapter.notifyDataSetChanged();
            }
            Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    private void recordList(String s, Gson gson) {
        RecordListBean recordListBean = gson.fromJson(s, RecordListBean.class);
        if (recordListBean.getCode() == 200) {
            recordList = recordListBean.getBody();
            recordListAdapter = new RecordListAdapter(ctx, recordList, tvSendsms);
            lv_list.setAdapter(recordListAdapter);
            recordListAdapter.setWindowListener(this);
        } else {
            Toast.makeText(ctx, recordListBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 筛选
     */
    private class ViewHolderSceer {
        public View rootView;
        public RadioButton rb_all;
        public RadioButton rb_myself;
        public RadioGroup rg_group;
        public TextView tv_close;

        public ViewHolderSceer(View rootView) {
            this.rootView = rootView;
            this.rb_all = (RadioButton) rootView.findViewById(R.id.rb_all);
            this.rb_myself = (RadioButton) rootView.findViewById(R.id.rb_myself);
            this.rg_group = (RadioGroup) rootView.findViewById(R.id.rg_group);
            this.tv_close = (TextView) rootView.findViewById(R.id.tv_close);
        }

    }


    @Override
    public void show() {
        llBottominput.setVisibility(View.VISIBLE);
        etContent.setFocusable(true);
        etContent.setFocusableInTouchMode(true);
        etContent.requestFocus();
        mHandler.sendEmptyMessageDelayed(0, 200);
    }

    /**
     * @param recorid 纪录id
     * @param otherId
     */
    @Override
    public void send(String recorid, String otherId) {
        this.recorid = recorid;
        this.otherId = otherId;
    }

    /**
     * 点赞
     *
     * @param recorid 纪录id
     * @param type    (1点赞，2取消点赞)
     */
    @Override
    public void praise(String recorid, String type) {
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("id", recorid);
        map.put("type", type);
        Httptype = 5;
        mPreenter.fetch(map, true, HttpUrl.RecordPrise, "");
    }

    /**
     * 分享
     *
     * @param recorid
     * @param shareurl
     */
    @Override
    public void share(int postion,String recorid, String shareurl) {
        this.recorid = recorid;
        this.shareUrl = shareurl;
        this.deleteposition = postion;
        showSharePopu();
    }
    private int deleteposition;
    private String shareUrl;
    private ViewHolderShare holderShare;

    private void showSharePopu() {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_record_share, null);
        popushare = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        holderShare = new ViewHolderShare(view);
        popushare.setFocusable(true);
        popushare.setOutsideTouchable(true);
        popushare.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popushare.dismiss();
                backgroundAlpha(1);
            }
        });

        backgroundAlpha(0.5f);
        popushare.showAtLocation(lv_list, Gravity.BOTTOM, 0, 0);
    }

    private String recorid, otherId;
    private InputMethodManager imm;

    public void inputshoworhind() {

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        boolean active = imm.isActive();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            inputshoworhind();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {

            return;
        }
        Intent intent;
        switch (requestCode) {


            case 1://视频
                String vodeofile = data.getStringExtra("videofile");
                wch("视频：" + vodeofile);
                intent = new Intent(ctx, PublishGroupUpActivity.class);
                intent.putExtra("videofile", vodeofile);
                startActivity(intent);
                break;
            case 2://图片
                ArrayList<String> images = data.getStringArrayListExtra("images");
                intent = new Intent(ctx, PublishGroupUpActivity.class);
                intent.putStringArrayListExtra("images", images);
                startActivity(intent);
                break;
        }
    }

    private class ViewHolderShare implements View.OnClickListener {
        public View rootView;
        public ImageView iv_close;
        public LinearLayout ll_wxfrent;
        public LinearLayout ll_wx;
        public LinearLayout ll_qq;
        public LinearLayout ll_zone;
        public TextView tv_closemess;
        public TextView tv_close;

        public ViewHolderShare(View rootView) {
            this.rootView = rootView;
            this.iv_close = (ImageView) rootView.findViewById(R.id.iv_close);
            this.ll_wxfrent = (LinearLayout) rootView.findViewById(R.id.ll_wxfrent);
            this.ll_wx = (LinearLayout) rootView.findViewById(R.id.ll_wx);
            this.ll_qq = (LinearLayout) rootView.findViewById(R.id.ll_qq);
            this.ll_zone = (LinearLayout) rootView.findViewById(R.id.ll_zone);
            this.tv_closemess = (TextView) rootView.findViewById(R.id.tv_closemess);
            this.tv_close = (TextView) rootView.findViewById(R.id.tv_close);
            this.iv_close.setOnClickListener(this);
            this.ll_wxfrent.setOnClickListener(this);
            this.ll_wx.setOnClickListener(this);
            this.ll_qq.setOnClickListener(this);
            this.ll_zone.setOnClickListener(this);
            this.tv_closemess.setOnClickListener(this);
            this.tv_close.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.iv_close:
                    popushare.dismiss();
                    break;
                case R.id.ll_wxfrent://分享朋友圈
                    break;
                case R.id.ll_wx://分享微信
                    popushare.dismiss();
                    break;
                case R.id.ll_qq://分享qq好友
                    popushare.dismiss();
                    break;
                case R.id.ll_zone://分享空间
                    popushare.dismiss();
                    break;
                case R.id.tv_closemess://撤回消息
                    Map<String, String> map = new HashMap<>();
                    map.put("token", Token);
                    map.put("id", recorid);
                    map.put("type", "1");
                    Httptype = 6;
                    mPreenter.fetch(map, true, HttpUrl.DeleteRecord, "");
                    popushare.dismiss();
                    break;
                case R.id.tv_close://取消
                    popushare.dismiss();
                    break;

            }
        }
    }
}
