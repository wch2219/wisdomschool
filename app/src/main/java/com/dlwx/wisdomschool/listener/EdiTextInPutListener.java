package com.dlwx.wisdomschool.listener;

import android.text.Editable;
import android.text.TextWatcher;

import com.dlwx.baselib.utiles.LogUtiles;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/1/12/012.
 */

public class EdiTextInPutListener implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String s = editable.toString().trim();
        Pattern compile = Pattern.compile("ee_\\d\\d");
        Matcher matcher = compile.matcher(s);
        List<String> allSatisfyStr = new ArrayList<>();
        while (matcher.find()) {
            allSatisfyStr.add(matcher.group());
            int start = matcher.start();
            
        }
        LogUtiles.LogI("长度："+allSatisfyStr.size());
        for (int j = 0; j < allSatisfyStr.size(); j++) {
            LogUtiles.LogI(allSatisfyStr.get(j));
        }
    }
}
