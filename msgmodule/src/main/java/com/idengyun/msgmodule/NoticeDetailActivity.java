package com.idengyun.msgmodule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.utils.GsonConvertUtil;
import com.idengyun.msgmodule.beans.NoticeListBean;

import java.util.List;

/**
 * 消息模块
 * 包含5套消息模块，对应5套UI视图，对应5套Holder
 * 5套消息判断依据字段：contentType
 *
 * @author aLang
 */
public final class NoticeDetailActivity extends BaseActivity {

    private TextView tv_notice_hint;

    public static void start(Context context, int contentType, String content) {
        Intent starter = new Intent(context, NoticeDetailActivity.class);
        starter.putExtra("contentType", contentType);
        starter.putExtra("content", content);
        context.startActivity(starter);
    }

    private void updateUI(@Nullable FragmentActivity activity) {
        if (activity == null) return;
        Intent intent = activity.getIntent();
        int contentType = intent.getIntExtra("contentType", -1);
        String content = intent.getStringExtra("content");
        tv_notice_hint.setText(content);
        switch (contentType) {
            case 0:
                NoticeListBean.Data.Content.Type0 type0 = GsonConvertUtil.fromJson(content, NoticeListBean.Data.Content.Type0.class);
                String contentTitle = type0.contentTitle;
                String contentDetail = type0.contentDetail;
                break;
            case 1:
                NoticeListBean.Data.Content.Type1 type1 = GsonConvertUtil.fromJson(content, NoticeListBean.Data.Content.Type1.class);
                String contentTitle1 = type1.contentTitle;
                String contentDetail1 = type1.contentDetail;
                break;
            case 2:
                NoticeListBean.Data.Content.Type2 type2 = GsonConvertUtil.fromJson(content, NoticeListBean.Data.Content.Type2.class);
                String contentTitle2 = type2.contentTitle;
                String contentDetail2 = type2.contentDetail;
                List<NoticeListBean.Data.Content.Type2.Goods> goodsList = type2.goodsList;
                break;
            case 3:
                break;
            case 4:
                NoticeListBean.Data.Content.Type4 type4 = GsonConvertUtil.fromJson(content, NoticeListBean.Data.Content.Type4.class);
                String contentTitle3 = type4.contentTitle;
                String contentDetail3 = type4.contentDetail;
                String imgUrl = type4.imgUrl;
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                NoticeListBean.Data.Content.Type8 type8 = GsonConvertUtil.fromJson(content, NoticeListBean.Data.Content.Type8.class);
                String contentTitle4 = type8.contentTitle;
                String contentDetail4 = type8.contentDetail;
                String imgUrl1 = type8.imgUrl;
                break;
            case 9:
                break;
            case 10:
                NoticeListBean.Data.Content.Type10 type10 = GsonConvertUtil.fromJson(content, NoticeListBean.Data.Content.Type10.class);
                String contentTitle5 = type10.contentTitle;
                String contentDetail5 = type10.contentDetail;
                String subTitle = type10.subTitle;
                String imgUrl2 = type10.imgUrl;
                break;
            case 11:
                break;
            default:
                break;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tv_notice_hint = findViewById(R.id.tv_notice_hint);
        updateUI(this);
    }
}
