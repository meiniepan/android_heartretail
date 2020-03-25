package com.idengyun.heartretail.main;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.ImageApi;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.response.RedPacketBean;
import com.idengyun.heartretail.viewmodel.RedPacketViewModel;
import com.idengyun.msgmodule.RVLoadMore;
import com.idengyun.usermodule.HRUser;
import com.idengyun.usermodule.LoginActivity;
import com.idengyun.usermodule.VerifyDeviceActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 红包页面
 *
 * @author aLang
 */
public final class RedPacketFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private View layout_red_packet_rule;

    private TextView tv_red_packet_0;
    private TextView tv_red_packet_1;
    private TextView tv_red_packet_2;
    private TextView tv_red_packet_3;
    private View v_red_packet_dash_line;
    private TextView tv_red_packet_level;
    private TextView tv_red_packet_percent;

    private RecyclerView recycler_view;
    private TextView tv_red_packet_more;
    private RedPacketViewModel redPacketViewModel;

    /* 分页加载 */
    private int totalSize = 0;
    private int totalPage = 0;
    private int pageSize = 0;
    private int currentPage = 0;

    private final RVLoadMore onScrollListener = new RVLoadMore() {
        @Override
        public void onLoadingMore(RVLoadMore listener) {
            onLoadMore();
        }
    };
    private FriendAdapter friendAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_red_packet;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        observe();
        friendAdapter = new FriendAdapter();
        recycler_view.setAdapter(friendAdapter);
        recycler_view.addOnScrollListener(onScrollListener);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) return;
        if (getUserVisibleHint()) {
            setUserVisibleHint(false);
            if (HRUser.isLogin()) onLoadMore();
        }
    }

    @Override
    public void onClick(View v) {
        if (!HRUser.isLogin()) {
            startLoginActivity();
            return;
        }

        if (!HRUser.isAuthentication()) {
            startDeviceVerifyActivity();
            return;
        }

        if (layout_red_packet_rule == v) {
            HRActivity.start(getContext(), InviteRuleDetailFragment.class);
        } else if (tv_red_packet_more == v) {

        }
    }

    @Override
    public void onRefresh() {
        totalSize = 0;
        totalPage = 0;
        pageSize = 10;
        currentPage = 0;
        onScrollListener.setCanLoadMore(true);
        onLoadMore();
    }

    private void startDeviceVerifyActivity() {
        VerifyDeviceActivity.start(getContext());
    }

    private void startLoginActivity() {
        LoginActivity.start(getContext());
    }

    private void onLoadMore() {
        if (redPacketViewModel == null) return;
        redPacketViewModel.requestRedPacketDetail(this, currentPage + 1, pageSize);
    }

    private void observe() {
        FragmentActivity activity = getActivity();
        if (activity == null) return;
        if (redPacketViewModel == null) {
            redPacketViewModel = RedPacketViewModel.getInstance(activity);
            redPacketViewModel.getRedPacketDetail().observe(this, new Observer<RedPacketBean>() {
                @Override
                public void onChanged(@Nullable RedPacketBean redPacketBean) {
                    updateUI(redPacketBean);
                }
            });
        }
    }

    @MainThread
    private void updateUI(@Nullable RedPacketBean redPacketBean) {
        onScrollListener.setLoadingMore(false);
        if (redPacketBean == null) return;
        RedPacketBean.Data data = redPacketBean.data;
        RedPacketBean.Data.Packet packet = data.packet;
        List<RedPacketBean.Data.Friend> friends = data.friends;

        String canExchange = packet.canExchange;
        String total = packet.total;
        String hasExchange = packet.hasExchange;
        String willSend = packet.willSend;
        int level = packet.level;
        String percent = packet.percent;
        tv_red_packet_0.setText("¥" + canExchange);
        tv_red_packet_1.setText("¥" + total);
        tv_red_packet_2.setText("¥" + hasExchange);
        tv_red_packet_3.setText("¥" + willSend);
        tv_red_packet_level.setText(level + "级");
        tv_red_packet_percent.setText(percent + "%");

        totalSize = data.total;
        totalPage = data.pages;
        currentPage = data.current;
        onScrollListener.setCanLoadMore(currentPage < totalPage);

        if (currentPage == 1) friendAdapter.friendList.clear();
        friendAdapter.friendList.addAll(friends);
        if (!onScrollListener.isCanLoadMore()) {
            RedPacketBean.Data.Friend friend = new RedPacketBean.Data.Friend();
            friend.friendId = -1;
            friendAdapter.friendList.add(friend);
        }
        friendAdapter.notifyDataSetChanged();
        // tv_red_packet_more.setVisibility(friendAdapter.getItemCount() == friendsCount ? View.GONE : View.VISIBLE);
    }

    private void findViewById(@NonNull View view) {
        layout_red_packet_rule = view.findViewById(R.id.layout_red_packet_rule);

        tv_red_packet_0 = view.findViewById(R.id.tv_red_packet_0);
        tv_red_packet_1 = view.findViewById(R.id.tv_red_packet_1);
        tv_red_packet_2 = view.findViewById(R.id.tv_red_packet_2);
        tv_red_packet_3 = view.findViewById(R.id.tv_red_packet_3);
        v_red_packet_dash_line = view.findViewById(R.id.v_red_packet_dash_line);
        tv_red_packet_level = view.findViewById(R.id.tv_red_packet_level);
        tv_red_packet_percent = view.findViewById(R.id.tv_red_packet_percent);

        recycler_view = view.findViewById(R.id.recycler_view);
        tv_red_packet_more = view.findViewById(R.id.tv_red_packet_more);

        v_red_packet_dash_line.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        layout_red_packet_rule.setOnClickListener(this);
    }

    private static class FriendAdapter extends RecyclerView.Adapter {

        private LayoutInflater inflater;
        final List<RedPacketBean.Data.Friend> friendList = new ArrayList<>();

        @NonNull
        @Override

        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (inflater == null) inflater = LayoutInflater.from(parent.getContext());
            if (-1 == viewType) {
                View itemView = inflater.inflate(R.layout.view_type_no_more, parent, false);
                return new RecyclerView.ViewHolder(itemView) {
                };
            }
            View itemView = inflater.inflate(R.layout.fragment_red_packet_item, parent, false);
            return new FriendHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) != -1) {
                RedPacketBean.Data.Friend friend = friendList.get(position);
                FriendHolder friendHolder = (FriendHolder) holder;
                friendHolder.updateUI(friend);
            }
        }

        @Override
        public int getItemCount() {
            return friendList.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (friendList.get(position).friendId == -1) return -1;
            return super.getItemViewType(position);
        }
    }

    private static class FriendHolder extends RecyclerView.ViewHolder {

        private ImageView iv_red_packet_user_avatar;
        private TextView tv_red_packet_user_name;
        private TextView tv_red_packet_date;
        private TextView tv_red_packet_out_money;

        private FriendHolder(@NonNull View itemView) {
            super(itemView);
            findViewById(itemView);
        }

        private void updateUI(RedPacketBean.Data.Friend friend) {
            String friendName = friend.friendName;
            String friendHeadImg = friend.friendHeadImg;
            String inviteTime = friend.inviteTime;
            String consumeMoney = friend.consumeMoney;
            String[] split = inviteTime.split(" ");
            String date = split.length > 0 ? split[0] : "";

            ImageApi.displayImage(iv_red_packet_user_avatar.getContext(), iv_red_packet_user_avatar, friendHeadImg);
            tv_red_packet_user_name.setText(friendName);
            tv_red_packet_date.setText(date);
            tv_red_packet_out_money.setText(consumeMoney + "元");
        }

        private void findViewById(@NonNull View itemView) {
            iv_red_packet_user_avatar = itemView.findViewById(R.id.iv_red_packet_user_avatar);
            tv_red_packet_user_name = itemView.findViewById(R.id.tv_red_packet_user_name);
            tv_red_packet_date = itemView.findViewById(R.id.tv_red_packet_date);
            tv_red_packet_out_money = itemView.findViewById(R.id.tv_red_packet_out_money);
        }
    }
}
