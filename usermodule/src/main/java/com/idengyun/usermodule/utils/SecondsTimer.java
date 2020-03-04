package com.idengyun.usermodule.utils;

import android.os.CountDownTimer;

/**
 * 秒数版本倒计时
 *
 * @author aLang
 */
public final class SecondsTimer extends CountDownTimer {
    private Callback callback;

    /**
     * 倒计时回调接口
     */
    public static interface Callback {
        public void onTick(long secondsUntilFinished);

        public void onFinish();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public SecondsTimer(long secondsInFuture, Callback callback) {
        super(secondsInFuture * 1000L, 1000L);
        this.callback = callback;
    }

    private SecondsTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (callback != null) callback.onTick(millisUntilFinished / 1000L);
    }

    @Override
    public void onFinish() {
        if (callback != null) callback.onFinish();
    }
}
