package com.dlwx.baselib.presenter;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/8/12/012.
 */

public abstract class BasePresenter <T>{
  public WeakReference<T> mViewRef;


    /**
     * bind p and v
     * @param view
     */
    public void attachView(T view){
        mViewRef = new WeakReference<T>(view);
    }
    public void detachView(){
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    protected T getView(){

        return mViewRef.get();
    }
}
