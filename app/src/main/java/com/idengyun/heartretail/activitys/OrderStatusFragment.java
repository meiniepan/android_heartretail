package com.idengyun.heartretail.activitys;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.idengyun.heartretail.R;
import com.idengyun.statusrecyclerviewlib.RefreshStatusRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Burning
 * @description:订单状态详情 待付款, 代销中, 待发货, 待收货, 待评价
 * @date :2020/3/4 0004 13:25
 */
public class OrderStatusFragment extends BaseFragment {
    @BindView(R.id.rsr_order_status)
    RefreshStatusRecyclerView rsrOrderStatus;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_status;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
