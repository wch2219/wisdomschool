package com.dlwx.wisdomschool.fragments;


import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;
import com.dlwx.wisdomschool.interfac.VoiceRecordOrPlayListener;
import com.dlwx.wisdomschool.utiles.VoicetranscribeAndPlayUtiles;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 录音和播放
 */
public class RecordOrPlayFragment extends BaseFragment {

    @BindView(R.id.cb_record)
    CheckBox cbRecord;
    @BindView(R.id.ll_record)
    LinearLayout llRecord;
    @BindView(R.id.seekbar)
    SeekBar seekbar;
    @BindView(R.id.tv_record)
    TextView tvRecord;
    @BindView(R.id.cb_play)
    CheckBox cbPlay;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.ll_play)
    LinearLayout llPlay;
    Unbinder unbinder;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_record_or_play;

    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {
            cbRecord.setOnCheckedChangeListener(recordListener);
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

    @OnClick({R.id.tv_record, R.id.tv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_record:
                break;
            case R.id.tv_delete:
                break;
        }
    }
    private CompoundButton.OnCheckedChangeListener recordListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {//开始录制
                VoicetranscribeAndPlayUtiles.start((Activity) ctx);
            }else{//结束录制

                    String outFile = VoicetranscribeAndPlayUtiles.stop();
                    VoiceRecordOrPlayListener.recordListener.backFile(outFile);

            }
        }
    };

}
