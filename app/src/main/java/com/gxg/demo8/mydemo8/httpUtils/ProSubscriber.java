package com.gxg.demo8.mydemo8.httpUtils;

import android.content.Context;

import com.gxg.demo8.mydemo8.CustomProgressDialogIos;

import rx.Subscriber;

/**
 * 作者：Administrator on 2018/6/25 10:24
 * 邮箱：android_gaoxuge@163.com
 */
public abstract class ProSubscriber<T> extends Subscriber<T> {
    private Context mContext;
    private boolean isShowDialog = false;//是否显示加载框，默认不显示
    private CustomProgressDialogIos customProgressDialog;
    public ProSubscriber(Context mContext, boolean isShowDialog) {
        this.mContext = mContext;
        this.isShowDialog = isShowDialog;
        customProgressDialog = new CustomProgressDialogIos(mContext);
    }
    public ProSubscriber(Context mContext) {
        this.mContext = mContext;
        customProgressDialog = new CustomProgressDialogIos(mContext);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(isShowDialog){
            showProDailog();
        }
    }

    @Override
    public void onCompleted() {
        if(isShowDialog){
            dismissProgressDialog();
        }
        onCancelProgress();
    }

    @Override
    public void onError(Throwable e) {
       dismissProgressDialog();
    }

    @Override
    public void onNext(T o) {
        dismissProgressDialog();
    }

    private void showProDailog(){
        if(customProgressDialog!=null){
            customProgressDialog.show();
        }
    }
    private void dismissProgressDialog() {
        if ( customProgressDialog != null) {
            customProgressDialog.dismiss();
            customProgressDialog = null;
        }
    }
    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
