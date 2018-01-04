package com.dlwx.wisdomschool.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.MyListView;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.adapter.DisCussChatAdapter;
import com.dlwx.wisdomschool.fragments.EmojiconFragment;
import com.dlwx.wisdomschool.interfac.EmoInterface;
import com.dlwx.wisdomschool.utiles.VoicetranscribeAndPlayUtiles;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 智慧讨论
 */
public class DisCussActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_persionmanage)
    ImageView ivPersionmanage;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.rv_recyclerview)
    MyListView rvRecyclerview;
    @BindView(R.id.cb_type)
    CheckBox cb_type;
    @BindView(R.id.tv_speck)
    TextView tvSpeck;
    @BindView(R.id.iv_face)
    ImageView ivFace;
    @BindView(R.id.iv_send)
    ImageView ivSend;
    @BindView(R.id.et_edit)
    EditText etEdit;
    @BindView(R.id.emojicon_menu_container)
    FrameLayout emojiconMenuContainer;
    @BindView(R.id.ll_edit)
    LinearLayout ll_edit;
    private boolean inited;
    protected LayoutInflater layoutInflater;
    private View foodView;
    private ViewHolder foodholder;
    @Override
    protected void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_dis_cuss);
        View foodView = LayoutInflater.from(ctx).inflate(R.layout.food_discuss, null);
        ButterKnife.bind(this);
        foodholder = new ViewHolder(foodView);

    }

    @Override
    protected void initData() {
        tvTitle.setText("智慧讨论");
        initTabBar(toolBar);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        EmojiconFragment emojiconFragment = new EmojiconFragment();
        fragmentTransaction.add(R.id.emojicon_menu_container, emojiconFragment);
        fragmentTransaction.show(emojiconFragment);
        fragmentTransaction.commit();
        emojiconMenuContainer.setVisibility(View.GONE);
//        LinearLayoutManager manager = new LinearLayoutManager(ctx);
//        rvRecyclerview.setLayoutManager(manager);
        DisCussChatAdapter disCussChatAdapter = new DisCussChatAdapter(ctx);

        rvRecyclerview.setAdapter(disCussChatAdapter);
//        rvRecyclerview.smoothScrollToPosition(disCussChatAdapter.getItemCount());
    }

    @Override
    protected void initListener() {
        cb_type.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_edit.setVisibility(View.VISIBLE);
                    tvSpeck.setVisibility(View.GONE);
//                    showSoftInputFromWindow((Activity) ctx, etEdit);
                    emojiconMenuContainer.setVisibility(View.VISIBLE);
                    tvSpeck.setVisibility(View.GONE);
                } else {
                    ll_edit.setVisibility(View.GONE);
                    tvSpeck.setVisibility(View.VISIBLE);
                    etEdit.clearFocus();//清除焦点
                    emojiconMenuContainer.setVisibility(View.GONE);
                    tvSpeck.setVisibility(View.VISIBLE);
                }
            }
        });
        EmoInterface.setCheckEmoInterface(new EmoInterface.CheckEmoInterface() {
            @Override
            public void backEmo(Bitmap bitmap, int i) {
                ImageSpan imageSpan = new ImageSpan(ctx, bitmap);
                SpannableString spannableString = new SpannableString("ee_");   //“image”是图片名称的前缀
                spannableString.setSpan(imageSpan, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                etEdit.append(spannableString);
            }
        });
        tvSpeck.setOnTouchListener(touchListener);
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private boolean isYuyin = true;

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick({R.id.iv_persionmanage, R.id.tv_speck, R.id.iv_face, R.id.iv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_persionmanage:
                startActivity(new Intent(ctx, DisCussSettActivity.class));
                break;
            case R.id.tv_speck:
                break;
            case R.id.iv_face://表情
                cb_type.setChecked(true);
                break;
            case R.id.iv_send://发送
                String trim = etEdit.getText().toString().trim();

                wch(trim);
                tvType.setText(trim);
                break;
        }
    }

    /**
     * 语音讲话的点击事件
     */
    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    VoicetranscribeAndPlayUtiles.start((Activity) ctx);
                    tvSpeck.setText("松开 结束");
                    tvSpeck.setTextColor(getResources().getColor(R.color.garytext));
                    tvSpeck.setBackgroundResource(R.drawable.selete_speck_bggary);
                    break;
                case MotionEvent.ACTION_UP:
                    tvSpeck.setText("按住说话");
                    tvSpeck.setTextColor(getResources().getColor(R.color.white));
                    tvSpeck.setBackgroundResource(R.drawable.shape_class_btn);
                    try {
                        String outFile = VoicetranscribeAndPlayUtiles.stop();
                        //filePath为语音文件路径，length为录音时间(秒)
                        double v = VoicetranscribeAndPlayUtiles.durationTime();
                        wch(v);
                        //发送语音
//                        EMMessage message = EMMessage.createVoiceSendMessage(outFile, length, "toChatUsername");
//                        //如果是群聊，设置chattype，默认是单聊
//                        if (chatType == CHATTYPE_GROUP)
//                            message.setChatType(EMMessage.ChatType.GroupChat);
//                        EMClient.getInstance().chatManager().sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    int height = view.getHeight();

                    break;
            }

            return false;
        }
    };

    private class ViewHolder {
        public View rootView;
        public CheckBox cb_type;
        public TextView tv_speck;
        public EditText et_edit;
        public ImageView iv_face;
        public ImageView iv_send;
        public FrameLayout emojicon_menu_container;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.cb_type = (CheckBox) rootView.findViewById(R.id.cb_type);
            this.tv_speck = (TextView) rootView.findViewById(R.id.tv_speck);
            this.et_edit = (EditText) rootView.findViewById(R.id.et_edit);
            this.iv_face = (ImageView) rootView.findViewById(R.id.iv_face);
            this.iv_send = (ImageView) rootView.findViewById(R.id.iv_send);
            this.emojicon_menu_container = (FrameLayout) rootView.findViewById(R.id.emojicon_menu_container);
        }

    }
}
