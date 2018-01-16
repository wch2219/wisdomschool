package com.dlwx.wisdomschool.listener;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/1/12/012.
 */

public class EdiTextInPutListener implements TextWatcher {
    private TextView tv_num;
    public EdiTextInPutListener(TextView tv_num){
        this.tv_num = tv_num;

    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String s = editable.toString().trim();

            tv_num.setText(s.length()+"/200");

    }
}
