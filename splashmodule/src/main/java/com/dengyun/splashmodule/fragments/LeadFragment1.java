package com.dengyun.splashmodule.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.net.rx.RxSchedulers;
import com.dengyun.baselibrary.utils.ListUtils;
import com.dengyun.splashmodule.R;
import com.dengyun.splashmodule.beans.MainConfig;
import com.dengyun.splashmodule.beans.MainUrlConstants;
import com.dengyun.splashmodule.config.GuidePicData;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.dengyun.splashmodule.utils.ArouterToFirstUtil;
import com.dengyun.splashmodule.utils.SaveMainConfigUtil;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.Response;

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
                        ArouterToFirstUtil.skipToFirst();
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
            NetOption netOption = NetOption.newBuilder(MainUrlConstants.MAINHTTP)
                    .fragment(this)
                    .type(type)
                    .build();
            NetApi.<ApiBean<MainConfig>>getData(RequestMethod.GET, netOption, new JsonCallback<ApiBean<MainConfig>>(netOption) {
                @Override
                public void onSuccess(Response<ApiBean<MainConfig>> response) {
                    //保存本地MainConfig配置url
                    final MainConfig mainConfigApiBean = response.body().data;
                    RxSchedulers.doTask(getMyActivity(), new RxSchedulers.Task() {
                        @Override
                        public void doOnIOThread() {
                            if (null!=mainConfigApiBean && !ListUtils.isEmpty(mainConfigApiBean.urlConfig)){
                                SaveMainConfigUtil.saveMainBean(mainConfigApiBean);
                            }
                        }

                        @Override
                        public void doOnUIThread() {
                            ArouterToFirstUtil.skipToFirst();
                            getActivity().finish();
                        }
                    });
                }
            });
            return false;
        }
        return true;
    }


}
