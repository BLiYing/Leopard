package com.yuan.leopardkit.http.base;

import android.content.Context;

import com.yuan.leopardkit.interfaces.HttpRespondResult;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Yuan on 2016/8/23.
 * Detail 基础订阅者
 */
public class BaseSubscriber<T extends ResponseBody> extends Subscriber<T> {

    private Context mContext;
    private HttpRespondResult callback;

    public BaseSubscriber(Context mContext, HttpRespondResult callback) {
        this.mContext = mContext;
        this.callback = callback;
    }

    @Override
    public void onStart() {
        super.onStart();
        callback.onPreViewAction();
    }

    @Override
    public void onCompleted() {
        callback.onAfterViewAction();
    }

    @Override
    public void onError(Throwable e) {
        callback.onFailure(e, e.getMessage().toString());
    }

    @Override
    public void onNext(T t) {
        try {
            String jsonString = new String(t.bytes());
            callback.onSuccess(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
