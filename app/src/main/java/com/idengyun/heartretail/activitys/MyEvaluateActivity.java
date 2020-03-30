package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.utils.phoneapp.AppUtils;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.google.gson.reflect.TypeToken;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.adapters.EvaluateListAdapter;
import com.idengyun.heartretail.beans.CommentListBean;
import com.idengyun.statusrecyclerviewlib.RefreshStatusRecyclerView;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Burning
 * @description:
 * @date :2020/3/6 0006 9:06
 */
public class MyEvaluateActivity extends BaseActivity {
    @BindView(R.id.sr_evaluate)
    RefreshStatusRecyclerView recyclerView;
    List<CommentListBean.CommentBean> mData = new ArrayList<>();
    private EvaluateListAdapter adapter;
    private int page = 1;

    public static void start(Context context) {
        Intent starter = new Intent(context, MyEvaluateActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_evaluate;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initRefreshLoadMore();
        getData();
        adapter = new EvaluateListAdapter(R.layout.item_evaluate, mData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EvaluateDetailActivity.start(getContext(),mData.get(position).evaluationId);
            }
        });
    }

    private void getData() {
        Type type = new TypeToken<ApiBean<CommentListBean>>() {
        }.getType();
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.myEvaluationList())
                .activity(activity)
                .params("version", AppUtils.getAppVersionName())
                .params("page", page)
                .params("userId", "1")
//                .params("userId", HRUser.getId())
                .params("pageSize", 10)
                .isShowDialog(true)
                .type(type)
                .build();

        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<ApiBean<CommentListBean>>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean<CommentListBean>> response) {
                recyclerView.finishRefreshLoadMore();
                List<CommentListBean.CommentBean> data = response.body().data.evaluationList;
                if (response.body().data!=null&data != null && data.size() > 0) {
                    mData.addAll(data);
                } else {
                    if (page != 1) {
                        ToastUtils.showShort(R.string.load_more_end);
                    }
                }
                recyclerView.notifyDataSetChange();
            }

            @Override
            public void onError(Response<ApiBean<CommentListBean>> response) {
                super.onError(response);
                recyclerView.finishRefreshLoadMore();
            }
        });
    }

    private void initRefreshLoadMore() {
        recyclerView.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                doRefresh();
            }
        });
    }

    private void doRefresh() {
        mData.clear();
        page = 1;
        getData();
    }


}
