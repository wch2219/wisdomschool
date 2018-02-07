package com.dlwx.wisdomschool.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.base.BaseRecrviewAdapter;
import com.dlwx.baselib.bean.Image;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.Utils;
import com.dlwx.baselib.view.MyListView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.SendMessPicListAdapter;
import com.dlwx.wisdomschool.adapter.SendNotiAddActionAdapter;
import com.dlwx.wisdomschool.adapter.SendNotiSeleteAdapter;
import com.dlwx.wisdomschool.bean.ActionTitleBean;
import com.dlwx.wisdomschool.bean.BackResultBean;
import com.dlwx.wisdomschool.bean.UpPicBean;
import com.dlwx.wisdomschool.fragments.EmojiconFragment;
import com.dlwx.wisdomschool.fragments.RecordOrPlayFragment;
import com.dlwx.wisdomschool.interfac.EmoInterface;
import com.dlwx.wisdomschool.interfac.VoiceRecordOrPlayListener;
import com.dlwx.wisdomschool.utiles.GroupPublishBigPic;
import com.dlwx.wisdomschool.utiles.HttpUrl;
import com.dlwx.wisdomschool.utiles.UpFileUtiles;
import com.dlwx.wisdomschool.utiles.VoicetranscribeAndPlayUtiles;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.wisdomschool.base.MyApplication.Token;

/**
 * 发送通知
 */
public class SendNotifiActivity extends BaseActivity implements VoiceRecordOrPlayListener.RecordListener,
        VoiceRecordOrPlayListener.CleanOrAnewRecordListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.ll_send)
    LinearLayout llSend;
    @BindView(R.id.tv_classname)
    TextView tvClassname;
    @BindView(R.id.ll_seleteclass)
    LinearLayout llSeleteclass;
    @BindView(R.id.tv_actionname)
    TextView tvActionname;
    @BindView(R.id.ll_seleteaction)
    LinearLayout llSeleteaction;
    @BindView(R.id.gv_piclist)
    RecyclerView gvPiclist;
    @BindView(R.id.lv_list)
    MyListView lvList;
    @BindView(R.id.tv_addaction)
    TextView tvAddaction;
    @BindView(R.id.iv_mp3)
    ImageView ivMp3;
    @BindView(R.id.iv_addpic)
    ImageView ivAddpic;
    @BindView(R.id.iv_face)
    ImageView ivFace;
    @BindView(R.id.tv_entime)
    TextView tvEntime;
    @BindView(R.id.ll_endtime)
    LinearLayout llEndtime;
    @BindView(R.id.repair_date_other)
    RelativeLayout repairDateOther;
    @BindView(R.id.repair_date_sel_cancel)
    Button repairDateSelCancel;
    @BindView(R.id.repair_date_sel_ok)
    Button repairDateSelOk;
    @BindView(R.id.date_picker)
    DatePicker datePicker;
    @BindView(R.id.time_picker)
    TimePicker timePicker;
    @BindView(R.id.emojicon_menu_container)
    FrameLayout emojiconMenuContainer;
    @BindView(R.id.rl_record)
    FrameLayout rl_record;
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.iv_hind)
    ImageView iv_hind;
    @BindView(R.id.iv_videopic)
    ImageView ivVideopic;
    @BindView(R.id.rl_voide)
    RelativeLayout rlVoide;
    @BindView(R.id.tv_anewrecord)
    TextView tvAnewrecord;
    @BindView(R.id.ll_video)
    LinearLayout llVideo;
    @BindView(R.id.iv_viecoplaytype)
    ImageView ivViecoplaytype;
    @BindView(R.id.tv_sec)
    TextView tvSec;
    @BindView(R.id.ll_voice)
    LinearLayout llVoice;
    private String[] actionTitle = new String[]{"班级通知", "群组讨论", "调查问卷", "视频（实时）纪录", "活动收集", "布置作业"};
    private int requestCodeClass = 101;//班级
    private String addclass_nos;//添加的班级
    private String createclass_nos;//创建的班级
    private String class_nos;//选择的班级
    private List<ActionTitleBean.BodyBean> actionTitles = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();

    @Override
    protected void initView() {
        setContentView(R.layout.activity_send_notifi);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("发送调查");
        initTabBar(toolBar);
        lvList.setAdapter(new SendNotiAddActionAdapter(ctx));
        getActionTitle();
        datePicker.setCalendarViewShown(false);
        timePicker.setIs24HourView(true);
        resizePikcer(datePicker);// 调整datepicker大小
        resizePikcer(timePicker);// 调整timepicker大小
        //初始化表情
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        EmojiconFragment emojiconFragment = new EmojiconFragment();
        fragmentTransaction.add(R.id.emojicon_menu_container, emojiconFragment);
        fragmentTransaction.show(emojiconFragment);
        fragmentTransaction.commit();

        //初始化录音和播放
        FragmentTransaction recordTransaction = getSupportFragmentManager().beginTransaction();
        RecordOrPlayFragment recordOrPlayFragment = new RecordOrPlayFragment();
        recordTransaction.add(R.id.rl_record, recordOrPlayFragment);
        recordTransaction.show(recordOrPlayFragment);
        recordTransaction.commit();

        EmoInterface.setCheckEmoInterface(new EmoInterface.CheckEmoInterface() {
            @Override
            public void backEmo(Bitmap bitmap, int i) {
                ImageSpan imageSpan = new ImageSpan(ctx, bitmap);
                int spanend = i >= 9 ? 5 : 4;
                int suffix = i >= 9 ? i + 1 : (i + 1);
                SpannableString spannableString = new SpannableString("ee_" + suffix);   //“image”是图片名称的前缀
                spannableString.setSpan(imageSpan, 0, spanend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                et_content.append(spannableString);
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        manager.setOrientation(LinearLayout.HORIZONTAL);
        gvPiclist.setLayoutManager(manager);
    }

    /**
     * 调整FrameLayout大小
     *
     * @param tp
     */
    private void resizePikcer(FrameLayout tp) {
        List<NumberPicker> npList = findNumberPicker(tp);
        for (NumberPicker np : npList) {
            resizeNumberPicker(np);
        }
    }

    /*
     * 调整numberpicker大小
     */
    private void resizeNumberPicker(NumberPicker np) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Utils.dip2px(this, 45),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(Utils.dip2px(this, 5), 0, Utils.dip2px(this, 5), 0);
        np.setLayoutParams(params);

    }

    /**
     * 得到viewGroup里面的numberpicker组件
     *
     * @param viewGroup
     * @return
     */
    private List<NumberPicker> findNumberPicker(ViewGroup viewGroup) {
        List<NumberPicker> npList = new ArrayList<NumberPicker>();
        View child = null;
        if (null != viewGroup) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                child = viewGroup.getChildAt(i);
                if (child instanceof NumberPicker) {
                    npList.add((NumberPicker) child);
                } else if (child instanceof LinearLayout) {
                    List<NumberPicker> result = findNumberPicker((ViewGroup) child);
                    if (result.size() > 0) {
                        return result;
                    }
                }
            }
        }
        return npList;
    }

    @Override
    protected void initListener() {
        VoiceRecordOrPlayListener.setRecordListener(this);
        VoiceRecordOrPlayListener.setAnewRecordListener(this);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.ll_send, R.id.ll_seleteclass, R.id.ll_seleteaction, R.id.tv_addaction, R.id.iv_mp3, R.id.iv_addpic, R.id.iv_face,
            R.id.repair_date_sel_cancel, R.id.repair_date_sel_ok, R.id.ll_endtime,R.id.rl_voide, R.id.tv_anewrecord, R.id.ll_voice,
            R.id.iv_hind,R.id.et_content
    })
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_send://发送
                submit();
                break;
            case R.id.ll_seleteclass://选择班级
                intent = new Intent(ctx, SeletePublishClassActivity.class);
                intent.putExtra("addclass_nos", addclass_nos);
                intent.putExtra("createclass_nos", createclass_nos);
                intent.putExtra("tag", "home");
                startActivityForResult(intent, requestCodeClass);
                break;
            case R.id.ll_seleteaction://选择活动
                showseltePopu(llSeleteaction);
                break;
            case R.id.tv_addaction://添加活动
                break;
            case R.id.iv_mp3://添加语音
                int rl_recordVisibility = rl_record.getVisibility();// 返回值为0，visible；返回值为4，invisible；返回值为8，gone。
                int j = rl_recordVisibility == View.VISIBLE ? View.GONE : View.VISIBLE;
                iv_hind.setVisibility(j);
                rl_record.setVisibility(j);
                if (j == View.VISIBLE) {
                    inputhind(ivFace);
                    emojiconMenuContainer.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_addpic://添加图片
                iv_hind.setVisibility(View.GONE);
                emojiconMenuContainer.setVisibility(View.GONE);
                rl_record.setVisibility(View.GONE);
                intent = new Intent(ctx, AllPicActivity.class);
                intent.putExtra("currnum", images.size());
                startActivityForResult(intent, 2);
                inputhind(ivFace);
                break;
            case R.id.iv_face://添加表情
                int visibility = emojiconMenuContainer.getVisibility();// 返回值为0，visible；返回值为4，invisible；返回值为8，gone。
                int i = visibility == View.VISIBLE ? View.GONE : View.VISIBLE;
                emojiconMenuContainer.setVisibility(i);
                iv_hind.setVisibility(i);
                if (i == View.VISIBLE) {
                    rl_record.setVisibility(View.GONE);
                    inputhind(ivFace);
                }
                break;
            case R.id.ll_endtime://选择时间
                repairDateOther.setVisibility(repairDateOther.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.repair_date_sel_cancel://取消选择
                repairDateOther.setVisibility(repairDateOther.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.repair_date_sel_ok:
                back(false);
                break;
            case R.id.rl_voide://视频播放
                break;
            case R.id.tv_anewrecord://重录
                break;
            case R.id.ll_voice://音频

                break;
            case R.id.iv_hind:
                emojiconMenuContainer.setVisibility(View.GONE);
                rl_record.setVisibility(View.GONE);
                iv_hind.setVisibility(View.GONE);
                VoiceRecordOrPlayListener.visibilityLis.isVisibi(rl_record.getVisibility());
                break;
            case R.id.et_content:
                    emojiconMenuContainer.setVisibility(View.GONE);
                    rl_record.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 发送
     */
    private void submit() {
        if (TextUtils.isEmpty(class_nos)) {
            Toast.makeText(ctx, "请选择班级", Toast.LENGTH_SHORT).show();
            return;
        }
         if (TextUtils.isEmpty(themeID)) {
            Toast.makeText(ctx, "请选择活动主题", Toast.LENGTH_SHORT).show();
            return;
        }

//        if (images.size() == 0) {
//            Toast.makeText(ctx, "请先选择图片", Toast.LENGTH_SHORT).show();
//            return;
//        }
        showLoading();
        //上传图片语音
        if (!TextUtils.isEmpty(voiceFile)) {//先判断是否有语音,有语音先上传语音
            UpFileUtiles.setBackInterface(new UpFileUtiles.BackInterface() {
                @Override
                public void success(Response<String> response) {
                    disLoading();
                    Gson gson = new Gson();
                    wch("语音上传：" + response.body());
                    UpPicBean upPicBean = gson.fromJson(response.body(), UpPicBean.class);
                    if (upPicBean.getCode() == 200) {
                        voice = upPicBean.getBody().getFileid() + "";
                        //上传成功后再多图上传

                        upPic();
                    } else {
                        Toast.makeText(ctx, upPicBean.getResult(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            wch(voiceFile);
            UpFileUtiles.start(ctx, new File(voiceFile), "2", 0);
        } else {//没有语音开始多图上传
            upPic();
        }
    }
    private int pos = -1;
    private void upPic() {
        pos++;
        if (pos >= images.size()) {
            //图片上传完成
            send();
            return;
        }
        UpFileUtiles.setBackInterface(new UpFileUtiles.BackInterface() {
            @Override
            public void success(Response<String> response) {
                disLoading();
                Gson gson = new Gson();
                UpPicBean upPicBean = gson.fromJson(response.body(), UpPicBean.class);
                if (upPicBean.getCode() == 200) {
                    if (TextUtils.isEmpty(imgs)) {
                        imgs = upPicBean.getBody().getFileid() + "";
                    } else {
                        imgs = imgs + "," + upPicBean.getBody().getFileid();
                    }
                    upPic();
                } else {
                    Toast.makeText(ctx, upPicBean.getResult(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        UpFileUtiles.start(ctx, new File(images.get(pos)), "1", 0);

    }
    private String imgs;//图片id
    private String video;//视频id
    private String voice;//语音id

    /**
     * 发送
     */
    private void send() {
        HttpType = 2;
        String content = et_content.getText().toString().trim();
        Map<String, String> map = new HashMap<>();
        map.put("token", Token);
        map.put("classids", class_nos);
        map.put("content_pic", imgs);
//        map.put("video", video);
        if (!TextUtils.isEmpty(voiceFile)) {
            map.put("content_voice_second",tvSec.getText().toString());
        }
        map.put("content_voice", voice);
        map.put("content", content);
        map.put("theme", themeID);
        map.put("theme_endtime",tvEntime.getText().toString().trim());
        mPreenter.fetch(map, false, HttpUrl.Publish_inves, "");
    }
    private void showseltePopu(View parentview) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.popu_selete, null);
        ViewHolder vh = new ViewHolder(view);
        final PopupWindow popu = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popu.setFocusable(true);
        popu.setOutsideTouchable(true);
        ColorDrawable drawable = new ColorDrawable(getResources().getColor(R.color.blacktran));
        popu.setBackgroundDrawable(drawable);
        popu.showAsDropDown(parentview, 0, 0);
        vh.lv_list.setAdapter(new SendNotiSeleteAdapter(ctx, actionTitles));
        vh.lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActionTitleBean.BodyBean bodyBean = actionTitles.get(position);
                tvActionname.setText(bodyBean.getActivity_type());
                themeID = bodyBean.getId();
                popu.dismiss();
                if ("5".equals(themeID)) {
                    llEndtime.setVisibility(View.VISIBLE);
                }else {
                    llEndtime.setVisibility(View.GONE);
                }
            }
        });
    }
    private String themeID;//主题id
    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {

            ActionTitleBean actionTitleBean = gson.fromJson(s, ActionTitleBean.class);
            if (actionTitleBean.getCode() == 200) {
                actionTitles = actionTitleBean.getBody();
            } else {
                Toast.makeText(ctx, actionTitleBean.getResult(), Toast.LENGTH_SHORT).show();
            }
        }else if (HttpType == 2) {
            BackResultBean backResultBean = gson.fromJson(s, BackResultBean.class);
            if (backResultBean.getCode() == 200) {
                finish();
            }
            Toast.makeText(ctx, backResultBean.getResult(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {

            return;
        }
        Intent intent;
        switch (requestCode) {
            case 1://视频
                String vodeofile = data.getStringExtra("vodeofile");
                wch("视频：" + vodeofile);
                intent = new Intent(ctx, PublishGroupUpActivity.class);
                intent.putExtra("videofile", vodeofile);
                startActivity(intent);
                break;
            case 2://图片
                List<String> imag = data.getStringArrayListExtra("images");
                for (int i = 0; i < imag.size(); i++) {
                    images.add(imag.get(i));
                }
                final SendMessPicListAdapter sendMessPicListAdapter = new SendMessPicListAdapter(ctx, images);
                gvPiclist.setAdapter(sendMessPicListAdapter);
                sendMessPicListAdapter.setOnItemClickListener(new BaseRecrviewAdapter.OnItemClickListener() {
                    @Override
                    public void setOnClick(final int position) {
                        List<Image> imageList = new ArrayList<>();
                        for (int i = 0; i < images.size(); i++) {
                            Image image = new Image();
                            image.setPath(images.get(i));
                            image.setOldposition(i);
                            imageList.add(image);
                        }
                        GroupPublishBigPic.showPic(ctx, gvPiclist, imageList, position);
                        GroupPublishBigPic.setDeletteListener(new GroupPublishBigPic.DeletteListener() {
                            @Override
                            public void delete(int postion) {
                                images.remove(position);
                                sendMessPicListAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });

                break;
            case 101://选择的班级
                createclass_nos = data.getStringExtra("createclass_nos");
                addclass_nos = data.getStringExtra("addclass_nos");
                String classnames = data.getStringExtra("classnames");
                class_nos = createclass_nos;
                tvClassname.setText(classnames);
                break;
        }
    }

    private void getActionTitle() {
        HttpType = 1;
        mPreenter.fetch(new HashMap<String, String>(), true, HttpUrl.ActionTitle, HttpUrl.ActionTitle);
    }


    /**
     * 关闭调用 old为true则不变，false则改变
     *
     * @param
     */
    private void back(boolean old) {
        // 获取时间选择
        Intent intent = new Intent();
        if (old) {
            intent.putExtra("date", tvEntime.getText().toString().trim());
        } else {
            String datestr = getData();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date date = sdf.parse(datestr);
                if (!compare(date))
                    return;
                repairDateOther.setVisibility(repairDateOther.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                tvEntime.setText(datestr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    // 比较时间
    private boolean compare(Date dt1) {
        Date curDate = new Date(System.currentTimeMillis());
        if (dt1.getTime() > curDate.getTime()) {
            System.out.println("选的时间大于现在的时间");
            return true;
        } else if (dt1.getTime() < curDate.getTime()) {
            Toast.makeText(ctx, "预约时间必须大于当前时间", Toast.LENGTH_SHORT).show();
            return false;
        } else {// 相等  
            System.out.println("相等");
            return false;
        }
    }

    private String getData() {
        StringBuilder str = new StringBuilder().append(datePicker.getYear()).append("-")
                .append((datePicker.getMonth() + 1) < 10 ? "0" + (datePicker.getMonth() + 1)
                        : (datePicker.getMonth() + 1))
                .append("-")
                .append((datePicker.getDayOfMonth() < 10) ? "0" + datePicker.getDayOfMonth()
                        : datePicker.getDayOfMonth())
                .append(" ")
                .append((timePicker.getCurrentHour() < 10) ? "0" + timePicker.getCurrentHour()
                        : timePicker.getCurrentHour())
                .append(":").append((timePicker.getCurrentMinute() < 10) ? "0" + timePicker.getCurrentMinute()
                        : timePicker.getCurrentMinute());

        return str.toString();
    }


    private class ViewHolder {
        public View rootView;
        public ListView lv_list;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.lv_list = (ListView) rootView.findViewById(R.id.lv_list);
        }

    }

    private String voiceFile;
    @Override
    public void backFile(String file) {
        voiceFile = file;
        //录制完成返回的语音文件
        llVoice.setVisibility(View.VISIBLE);
        tvSec.setText(VoicetranscribeAndPlayUtiles.durationTime(voiceFile));
    }

    @Override
    public void clean() {
        llVoice.setVisibility(View.GONE);
        voiceFile = "";
    }
}
