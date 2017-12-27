package com.dlwx.baselib.model;

import java.util.Map;

/**
 * model内的接口
 */

public interface ModeInterface {
    /**
     *
     * 加载数据
     */
    void loadData(LoadListener loadListener, Map<String, String> map, boolean isget, String url, String cachKey);

    /**
     * 夹在数据监听
     */
    interface LoadListener{
        /**
         * 加载完成回调
         */
        void complete(String s);
        void onError();
    }

}
