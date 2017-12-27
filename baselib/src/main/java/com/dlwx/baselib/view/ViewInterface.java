package com.dlwx.baselib.view;

import java.io.IOException;

/**
 * view接口
 */

public interface ViewInterface {
    void showLoading();
    /**
     * 显示数据
     */
    void showData(String s) throws IOException;
    void disLoading();
    void onError();
}
