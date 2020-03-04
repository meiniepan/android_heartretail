package com.dengyun.splashmodule.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.config.RouterPathConfig;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.constants.ProjectType;
import com.dengyun.baselibrary.net.rx.RxObserver;
import com.dengyun.baselibrary.net.rx.RxSchedulers;
import com.dengyun.baselibrary.spconstants.SpUserConstants;
import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;
import com.dengyun.splashmodule.R;
import com.dengyun.splashmodule.beans.MainConfig;
import com.dengyun.splashmodule.beans.MainUrlConstants;
import com.dengyun.splashmodule.config.GuidePicData;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeadFragment1 extends BaseFragment {

    private ImageView iv;

    public static LeadFragment1 newInstance(int index) {

        Bundle args = new Bundle();
        args.putInt("leadpic_index",index);
        LeadFragment1 fragment = new LeadFragment1();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.splash_fragment_lead_fragment1;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        iv = (ImageView) view.findViewById(R.id.iv_leadfragment1);
        initGetArguments();
    }

    private void initGetArguments() {
        Bundle bundle = getArguments();
        int leadpic [] = GuidePicData.leadpic;
        int picIndex = bundle.getInt("leadpic_index");
        iv.setImageResource(leadpic [picIndex]);
        if(picIndex+1==leadpic.length){
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isSaveMainConfigUrls()){
                        int interest = SpUserConstants.getInterest();
                        int whatFragment = interest <= 0 ? 0 : (interest - 1);
                        ARouter.getInstance().build(RouterPathConfig.app_FirstActivity).withInt("whatFragment",whatFragment).navigation();
                        getActivity().finish();
                    }
                }
            });
        }
    }

    private boolean isSaveMainConfigUrls(){
        String url = SpMainConfigConstants.index();
        if(TextUtils.isEmpty(url)){
            Type type = new TypeToken<ApiBean<MainConfig>>() {}.getType();
            // TODO: 2020-03-02 项目类型妃子校 需修改
            NetOption netOption = NetOption.newBuilder(MainUrlConstants.MAINHTTP)
                    .fragment(this)
                    .type(type)
                    .projectType(ProjectType.IDENGYUN_FZX)
                    .isEncrypt(false)
                    .isShowDialog(true)
                    .build();
            NetApi.<MainConfig>getDataRX(netOption)
                    .compose(RxSchedulers.<MainConfig>io_main())
                    .subscribe(new RxObserver<MainConfig>(netOption) {
                        @Override
                        public void onNext(MainConfig mainConfig) {
                            //保存本地MainConfig配置url
                            saveLocalMainConfig(mainConfig);
                        }
                    });


            return false;
        }
        return true;
    }

    /**
     * 将主配置保存在本地sp中
     */
    private void saveLocalMainConfig(final MainConfig mainConfigApiBean){
        RxSchedulers.doTask(this, new RxSchedulers.Task() {
            @Override
            public void doOnIOThread() {
                SharedPreferencesUtil.saveDataBean(Utils.getApp(), SpMainConfigConstants.spFileName, mainConfigApiBean.getData());
            }

            @Override
            public void doOnUIThread() {
                int interest = SpUserConstants.getInterest();
                int whatFragment = interest <= 0 ? 0 : (interest - 1);
                ARouter.getInstance().build(RouterPathConfig.app_FirstActivity).withInt("whatFragment",whatFragment).navigation();
                getActivity().finish();
            }
        });
    }

}
