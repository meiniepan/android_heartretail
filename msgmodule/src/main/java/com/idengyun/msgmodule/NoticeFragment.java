package com.idengyun.msgmodule;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.ImageApi;
import com.dengyun.baselibrary.utils.GsonConvertUtil;
import com.idengyun.msgmodule.beans.NoticeListBean;
import com.idengyun.msgmodule.viewmodel.NoticeViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息模块
 *
 * @author aLang
 */
public final class NoticeFragment extends BaseFragment {

    private int notifyGroup;
    private RecyclerView recycler_view;
    private View tv_notice_no_more;
    private NoticeViewModel noticeViewModel;
    private NoticeAdapter noticeAdapter;

    /* 分页 */
    private int totalPageSize = -1;
    private int totalPage = -1;
    private int pageSize = 10;
    private int page = 0;
    private boolean loadMore = true;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_notice;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) return;
        requestAPI();
    }

    @MainThread
    private void updateUI(@Nullable NoticeListBean noticeListBean) {
        if (noticeListBean == null) return;
        NoticeListBean.Data data = noticeListBean.data;

        totalPageSize = data.total;
        totalPage = (int) Math.ceil(1D * totalPageSize / pageSize);
        loadMore = ++page < totalPage;

        noticeAdapter.noticeList.addAll(data.contentList);
        noticeAdapter.notifyDataSetChanged();
        tv_notice_no_more.setVisibility(loadMore ? View.GONE : View.VISIBLE);
    }

    private void requestAPI() {
        if (noticeViewModel != null && notifyGroup != -1) {
            if (!loadMore) return;
            noticeViewModel.requestNoticeList(this, page + 1, pageSize, notifyGroup);
        }
    }

    private void init() {
        notifyGroup = getNotifyGroup();
        findViewById();
        initViewModel();

        recycler_view.addOnScrollListener(new More() {
            @Override
            public void onLoadMore(RecyclerView recyclerView) {
                requestAPI();
            }
        });
        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                System.out.println("newState= "+newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                System.out.println("dy= "+dy);
            }
        });
        noticeAdapter = new NoticeAdapter();
        recycler_view.setAdapter(noticeAdapter);
    }

    private void initViewModel() {
        FragmentActivity activity = getActivity();
        if (activity == null) return;
        if (noticeViewModel == null) {
            noticeViewModel = NoticeViewModel.getInstance(this);
            noticeViewModel.getNoticeList().observe(this, new Observer<NoticeListBean>() {
                @Override
                public void onChanged(@Nullable NoticeListBean noticeListBean) {
                    updateUI(noticeListBean);
                }
            });
        }
    }

    private int getNotifyGroup() {
        int notifyGroup = -1;
        String tag = getTag();
        if ("促销优惠".equals(tag)) {
            notifyGroup = 0;
        } else if ("账户通知".equals(tag)) {
            notifyGroup = 1;
        } else if ("服务通知".equals(tag)) {
            notifyGroup = 2;
        }
        return notifyGroup;
    }

    private void findViewById() {
        recycler_view = findViewById(R.id.recycler_view);
        tv_notice_no_more = findViewById(R.id.tv_notice_no_more);
    }

    private static class NoticeAdapter extends RecyclerView.Adapter {

        private LayoutInflater inflater;
        final List<NoticeListBean.Data.Content> noticeList = new ArrayList<>();

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (inflater == null) inflater = LayoutInflater.from(parent.getContext());
            if (0 == viewType) {
                View itemView = inflater.inflate(R.layout.fragment_notice_item_0, parent, false);
                return new Holder0(itemView);
            } else if (1 == viewType) {
                View itemView = inflater.inflate(R.layout.fragment_notice_item_1, parent, false);
                return new Holder1(itemView);
            } else if (2 == viewType) {
                View itemView = inflater.inflate(R.layout.fragment_notice_item_2, parent, false);
                return new Holder2(itemView);
            } else if (4 == viewType) {
                View itemView = inflater.inflate(R.layout.fragment_notice_item_4, parent, false);
                return new Holder4(itemView);
            } else if (11 == viewType) {
                View itemView = inflater.inflate(R.layout.fragment_notice_item_11, parent, false);
                return new Holder11(itemView);
            } else {
                View itemView = inflater.inflate(R.layout.fragment_notice_item_0, parent, false);
                return new Holder0(itemView);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            NoticeListBean.Data.Content content = noticeList.get(position);
            int viewType = getItemViewType(position);
            if (0 == viewType) {
                ((Holder0) holder).updateUI(content);
            } else if (1 == viewType) {
                ((Holder1) holder).updateUI(content);
            } else if (2 == viewType) {
                ((Holder2) holder).updateUI(content);
            } else if (4 == viewType) {
                ((Holder4) holder).updateUI(content);
            } else if (11 == viewType) {
                ((Holder11) holder).updateUI(content);
            }
        }

        @Override
        public int getItemCount() {
            return noticeList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return noticeList.get(position).contentType;
        }
    }

    private static class Holder0 extends RecyclerView.ViewHolder {

        private TextView tv_notice_0_push_time;
        private TextView tv_notice_0_title;
        private TextView tv_notice_0_detail;

        public Holder0(@NonNull View itemView) {
            super(itemView);
            findViewById(itemView);
        }

        @MainThread
        private void updateUI(NoticeListBean.Data.Content content) {
            String pushTime = content.pushTime;
            String title = content.title;
            NoticeListBean.Data.Content.Type0 type0 = GsonConvertUtil.fromJson(content.content, NoticeListBean.Data.Content.Type0.class);
            String contentTitle = type0.contentTitle;
            String contentDetail = type0.contentDetail;

            tv_notice_0_push_time.setText(pushTime);
            tv_notice_0_title.setText(contentTitle);
            tv_notice_0_detail.setText(contentDetail);
        }

        private void findViewById(@NonNull View itemView) {
            tv_notice_0_push_time = itemView.findViewById(R.id.tv_notice_0_push_time);
            tv_notice_0_title = itemView.findViewById(R.id.tv_notice_0_title);
            tv_notice_0_detail = itemView.findViewById(R.id.tv_notice_0_detail);
        }
    }

    private static class Holder1 extends RecyclerView.ViewHolder {

        private TextView tv_notice_1_push_time;
        private TextView tv_notice_1_title;
        private ImageView iv_notice_1_url;

        public Holder1(@NonNull View itemView) {
            super(itemView);
            findViewById(itemView);
        }

        @MainThread
        private void updateUI(NoticeListBean.Data.Content content) {
            String pushTime = content.pushTime;
            String title = content.title;
            String json = content.content;
            NoticeListBean.Data.Content.Type1 type1 = GsonConvertUtil.fromJson(json, NoticeListBean.Data.Content.Type1.class);
            String contentTitle = type1.contentTitle;
            String contentDetail = type1.contentDetail;

            tv_notice_1_push_time.setText(pushTime);
            tv_notice_1_title.setText(contentTitle);
            ImageApi.displayImage(iv_notice_1_url.getContext(), iv_notice_1_url, contentDetail);
        }

        private void findViewById(@NonNull View itemView) {
            tv_notice_1_push_time = itemView.findViewById(R.id.tv_notice_1_push_time);
            tv_notice_1_title = itemView.findViewById(R.id.tv_notice_1_title);
            iv_notice_1_url = itemView.findViewById(R.id.iv_notice_1_url);
        }
    }

    private static class Holder2 extends RecyclerView.ViewHolder {

        private TextView tv_notice_2_push_time;
        private TextView tv_tv_notice_2_title;
        private TextView tv_notice_2_click;
        private ImageView iv_notice_2_goods_url;
        private TextView tv_notice_2_goods_title;
        private TextView tv_notice_2_goods_spec;
        private TextView tv_notice_2_goods_num;

        public Holder2(@NonNull View itemView) {
            super(itemView);
            findViewById(itemView);
        }

        @MainThread
        private void updateUI(NoticeListBean.Data.Content content) {
            String pushTime = content.pushTime;
            String title = content.title;
            NoticeListBean.Data.Content.Type2 type2 = GsonConvertUtil.fromJson(content.content, NoticeListBean.Data.Content.Type2.class);
            String contentTitle = type2.contentTitle;
            String contentDetail = type2.contentDetail;
            List<NoticeListBean.Data.Content.Type2.Goods> goodsList = type2.goodsList;

            tv_notice_2_push_time.setText(pushTime);
            tv_tv_notice_2_title.setText(contentTitle);
            if (!goodsList.isEmpty()) {
                NoticeListBean.Data.Content.Type2.Goods goods = goodsList.get(0);
                String goodsName = goods.goodsName;
                String goodsNumStr = goods.goodsNumStr;
                String imgUrl = goods.imgUrl;
                String specKeyName = goods.specKeyName;

                tv_notice_2_goods_title.setText(goodsName);
                tv_notice_2_goods_spec.setText(specKeyName);
                tv_notice_2_goods_num.setText(goodsNumStr);
                ImageApi.displayImage(iv_notice_2_goods_url.getContext(), iv_notice_2_goods_url, imgUrl);
            } else {
                tv_notice_2_goods_title.setText(null);
                tv_notice_2_goods_spec.setText(null);
                tv_notice_2_goods_num.setText(null);
                iv_notice_2_goods_url.setImageDrawable(null);
            }

        }

        private void findViewById(@NonNull View itemView) {
            tv_notice_2_push_time = itemView.findViewById(R.id.tv_notice_2_push_time);
            tv_tv_notice_2_title = itemView.findViewById(R.id.tv_tv_notice_2_title);
            tv_notice_2_click = itemView.findViewById(R.id.tv_notice_2_click);
            iv_notice_2_goods_url = itemView.findViewById(R.id.iv_notice_2_goods_url);
            tv_notice_2_goods_title = itemView.findViewById(R.id.tv_notice_2_goods_title);
            tv_notice_2_goods_spec = itemView.findViewById(R.id.tv_notice_2_goods_spec);
            tv_notice_2_goods_num = itemView.findViewById(R.id.tv_notice_2_goods_num);
        }
    }

    private static class Holder4 extends RecyclerView.ViewHolder {

        private TextView tv_notice_4_push_time;
        private TextView tv_notice_4_title;
        private ImageView iv_notice_4_url;
        private TextView tv_notice_4_invalid;
        private TextView tv_notice_4_detail;

        public Holder4(@NonNull View itemView) {
            super(itemView);
            findViewById(itemView);
        }

        @MainThread
        private void updateUI(NoticeListBean.Data.Content content) {
            String pushTime = content.pushTime;
            String title = content.title;
            String json = content.content;
            NoticeListBean.Data.Content.Type4 type4 = GsonConvertUtil.fromJson(json, NoticeListBean.Data.Content.Type4.class);
            String contentTitle = type4.contentTitle;
            String contentDetail = type4.contentDetail;
            String imgUrl = type4.imgUrl;

            tv_notice_4_push_time.setText(pushTime);
            tv_notice_4_title.setText(contentTitle);
            tv_notice_4_detail.setText(contentDetail);
            ImageApi.displayImage(iv_notice_4_url.getContext(), iv_notice_4_url, imgUrl);
            //tv_notice_4_invalid.setText(pushTime);
        }

        private void findViewById(@NonNull View itemView) {
            tv_notice_4_push_time = itemView.findViewById(R.id.tv_notice_4_push_time);
            tv_notice_4_title = itemView.findViewById(R.id.tv_notice_4_title);
            iv_notice_4_url = itemView.findViewById(R.id.iv_notice_4_url);
            tv_notice_4_invalid = itemView.findViewById(R.id.tv_notice_4_invalid);
            tv_notice_4_detail = itemView.findViewById(R.id.tv_notice_4_detail);
        }
    }

    private static class Holder11 extends RecyclerView.ViewHolder {

        private TextView tv_notice_11_push_time;
        private ImageView iv_notice_type_11_url;
        private TextView tv_notice_type_11_detail;

        public Holder11(@NonNull View itemView) {
            super(itemView);
            findViewById(itemView);
        }

        @MainThread
        private void updateUI(NoticeListBean.Data.Content content) {
            String pushTime = content.pushTime;
            String title = content.title;
            String json = content.content;
            NoticeListBean.Data.Content.Type11 type11 = GsonConvertUtil.fromJson(json, NoticeListBean.Data.Content.Type11.class);
            String contentDetail = type11.contentDetail;
            String imgUrl = type11.imgUrl;

            tv_notice_11_push_time.setText(pushTime);
            ImageApi.displayImage(iv_notice_type_11_url.getContext(), iv_notice_type_11_url, imgUrl);
            tv_notice_type_11_detail.setText(contentDetail);
        }

        private void findViewById(@NonNull View itemView) {
            tv_notice_11_push_time = itemView.findViewById(R.id.tv_notice_11_push_time);
            iv_notice_type_11_url = itemView.findViewById(R.id.iv_notice_type_11_url);
            tv_notice_type_11_detail = itemView.findViewById(R.id.tv_notice_type_11_detail);
        }
    }
}
