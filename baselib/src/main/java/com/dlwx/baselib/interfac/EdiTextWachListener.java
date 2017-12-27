package com.dlwx.baselib.interfac;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Administrator on 2017/9/23/023.
 */

public class EdiTextWachListener implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (s.length() >6) {
            return;
        }
        if (textNumListener != null) {
            textNumListener.ResultText(s.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() >6) {
            return;
        }

    }
    private TextNumListener textNumListener;
    public interface TextNumListener {
        void ResultText(String s);
    }
    public void setTextNumListener(TextNumListener textNumListener){
        this.textNumListener = textNumListener;
    }
}
