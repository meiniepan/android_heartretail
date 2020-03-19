package com.idengyun.msgmodule.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.msgmodule.beans.NoticeCountBean;
import com.idengyun.msgmodule.beans.NoticeBean;
import com.idengyun.msgmodule.beans.NoticeStatusBean;
import com.idengyun.usermodule.HRUser;
import com.lzy.okgo.model.Response;

/**
 * 验证码API
 *
 * @author aLang
 */
public final class NoticeViewModel extends ViewModel {

    public static NoticeViewModel getInstance(@NonNull FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(NoticeViewModel.class);
    }

    private final MutableLiveData<NoticeCountBean> noticeCountLiveData;
    private final MutableLiveData<NoticeStatusBean> noticeStatusLiveData;
    private final MutableLiveData<NoticeBean> noticeListLiveData;

    public NoticeViewModel() {
        super();
        noticeCountLiveData = new MutableLiveData<>();
        noticeStatusLiveData = new MutableLiveData<>();
        noticeListLiveData = new MutableLiveData<>();
    }

    /* 查询用户未读推送数量 */
    public void requestNoticeCount(Fragment fragment) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.messageCounts())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(NoticeCountBean.class)
                .params("userId", HRUser.getId())
                .build();

        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<NoticeCountBean>(netOption) {
            @Override
            public void onSuccess(Response<NoticeCountBean> response) {
                noticeCountLiveData.setValue(response.body());
            }
        });
    }

    /* 更新用户阅读状态 */
    public void requestNoticeUpdateStatus(Fragment fragment, int notifyId, int status) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.messageStatusUpdate())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(NoticeStatusBean.class)
                .params("userId", HRUser.getId())
                .params("notifyId", notifyId)
                .params("status", status)
                .build();

        NetApi.getData(netOption, new JsonCallback<NoticeStatusBean>(netOption) {
            @Override
            public void onSuccess(Response<NoticeStatusBean> response) {
                noticeStatusLiveData.setValue(response.body());
            }
        });
    }

    /* 推送消息列表查询 */
    public void requestNoticeList(Fragment fragment, int page, int pageSize, int notifyGroup) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.messageList())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(NoticeBean.class)
                .params("userId", HRUser.getId())
                .params("page", page)
                .params("pageSize", pageSize)
                .params("notifyGroup", notifyGroup)
                .build();

        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<NoticeBean>(netOption) {
            @Override
            public void onSuccess(Response<NoticeBean> response) {
                noticeListLiveData.setValue(response.body());
            }
        });
    }

    public LiveData<NoticeCountBean> getNoticeCount() {
        return noticeCountLiveData;
    }

    public LiveData<NoticeStatusBean> getNoticeStatus() {
        return noticeStatusLiveData;
    }

    public LiveData<NoticeBean> getNoticeList() {
        return noticeListLiveData;
    }
}
