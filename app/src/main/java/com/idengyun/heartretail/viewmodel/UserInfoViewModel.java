package com.idengyun.heartretail.viewmodel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

/**
 * 请求用户信息
 *
 * @author aLang
 */
public final class UserInfoViewModel extends ViewModel {
    MediatorLiveData<Integer> liveDataMerger = new MediatorLiveData<>();
    MutableLiveData<Integer> liveData1 = new MutableLiveData<>();
    MutableLiveData<Integer> liveData2 = new MutableLiveData<>();

    public void fun(LifecycleOwner owner) {
        liveDataMerger.addSource(liveData1, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                System.out.println("+++++++++liveData1 = " + integer);
                liveDataMerger.setValue(integer);
            }
        });
        liveDataMerger.addSource(liveData2, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                System.out.println("--------liveData2 = " + integer);
                liveDataMerger.setValue(integer);
            }
        });
        liveData1.setValue(127);
        liveData2.setValue(128);
        liveDataMerger.observe(owner, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                System.out.println("=======liveDataMerger = " + integer);
            }
        });
    }
}
