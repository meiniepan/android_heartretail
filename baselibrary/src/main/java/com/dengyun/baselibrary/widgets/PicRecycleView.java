package com.dengyun.baselibrary.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dengyun.baselibrary.R;
import com.dengyun.baselibrary.utils.ImageUtils;
import com.dengyun.baselibrary.utils.SizeUtils;
import com.dengyun.baselibrary.utils.TimeUtils;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.utils.Utils;
import com.idengyun.statusrecyclerviewlib.RecycleViewDivider;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * @Title 下方选择9张图片的自定义view
 * @Desc: 例如商品评价下方选择图片
 * @Author: zhoubo
 * @CreateDate: 2019-06-04 16:08
 */
public class PicRecycleView extends RecyclerView {
    private int CHOOSE_REQUEST;
    private int maxNum;
    private int columnNum;
    private int defultAddPic;
    private ArrayList<PicRVBean> picPathList = new ArrayList<>();
    private PicRVAdapter picRVAdapter;
    private boolean isFull = false;

    public PicRecycleView(@NonNull Context context) {
        this(context, null, 0);
    }

    public PicRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PicRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PicRecycleView);
        columnNum = ta.getInt(R.styleable.PicRecycleView_column_num, 3);
        maxNum = ta.getInt(R.styleable.PicRecycleView_max_num, 3);
        defultAddPic = ta.getResourceId(R.styleable.PicRecycleView_add_pic, R.drawable.base_ic_pic_close);
        ta.recycle();
        initAdapter(context);
    }

    /**
     * 设置选择图片返回，在onActivityResult方法中返回
     */
    public void setResultPic(int requestCode, int resultCode, @Nullable Intent data) {
        List<LocalMedia> localMediaList = null;
        if (resultCode == RESULT_OK && requestCode == CHOOSE_REQUEST) {
            localMediaList = PictureSelector.obtainMultipleResult(data);
        }
        if (null == localMediaList) return;

        for (int i = 0; i < localMediaList.size(); i++) {
            String picPath = localMediaList.get(i).getCompressPath();
            if (!TextUtils.isEmpty(picPath)) {
                PicRVBean picRVBean = new PicRVBean(picPath, false);
                picPathList.add(picPathList.size() - 1, picRVBean);
            }
        }
        checkPicFull();
        picRVAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化控件的时候，设置初始的几个网络图片
     * @param urlList
     */
    public void initUrlList(List<String> urlList){
        picPathList.clear();
        addLastPic();
        for (String url:urlList){
            PicRVBean picRVBean = new PicRVBean(url,true);
            picPathList.add(picPathList.size()-1,picRVBean);
        }
        checkPicFull();
        picRVAdapter.notifyDataSetChanged();
    }


    /**
     * @return 获取本地选择的图片路径
     */
    public ArrayList<String> getLocalPicPathList(){
        ArrayList<String> picList = new ArrayList<>();
        for (PicRVBean picRVBean: picPathList){
            if(!picRVBean.isNetPic&&!picRVBean.isAddPic){
                picList.add(picRVBean.picPath);
            }
        }
        return picList;
    }

    /**
     * @return 获取默认设置的网络图片路径
     */
    public ArrayList<String> getNetPicUrlList(){
        ArrayList<String> picList = new ArrayList<>();
        for (PicRVBean picRVBean: picPathList){
            if(picRVBean.isNetPic&&!picRVBean.isAddPic){
                picList.add(picRVBean.picPath);
            }
        }
        return picList;
    }

    private void initAdapter(Context context) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, columnNum);
        setLayoutManager(gridLayoutManager);
        addLastPic();
        picRVAdapter = new PicRVAdapter(R.layout.base_item_picrecycleview, picPathList);
        setAdapter(picRVAdapter);
        addItemDecoration(new RecycleViewDivider(context, SizeUtils.dp2px(5)));
        picRVAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (picPathList.get(position).isAddPic) {
                    //点击添加图片
                    AndPermission.with(Utils.getApp().getApplicationContext())
                            .runtime()
                            .permission(com.yanzhenjie.permission.runtime.Permission.Group.STORAGE,
                                    com.yanzhenjie.permission.runtime.Permission.Group.CAMERA)
                            .onGranted(new Action<List<String>>() {
                                @Override
                                public void onAction(List<String> data) {
                                    CHOOSE_REQUEST = TimeUtils.getNowMillsOnly();
                                    PictureSelector.create((Activity) context)
                                            .openGallery(PictureMimeType.ofImage())
                                            .compress(true)
                                            .enableCrop(false)
                                            .isCamera(true)
                                            .maxSelectNum(maxNum - picPathList.size() + 1)
                                            .forResult(CHOOSE_REQUEST);
                                }
                            }).onDenied(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            ToastUtils.showShort("拒绝了权限,请为此应用授予相册权限");
                        }
                    }).start();
                } else {
                    //点击已经添加过的图片，放大
                    if (context instanceof Activity) {
                        ArrayList<String> skipBigList = new ArrayList<>();
                        skipBigList.add(picPathList.get(position).picPath);
                        ImageUtils.skipToBigPic((Activity) context, 0, skipBigList);
                    }
                }
            }
        });
        picRVAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_delete) {
                    //点击删除图片
                    picPathList.remove(position);
                    if (isFull) addLastPic();
                    adapter.notifyDataSetChanged();
                    isFull = false;
                }
            }
        });
    }

    private void checkPicFull(){
        if (picPathList.size() > maxNum) {
            isFull = true;
            picPathList.remove(picPathList.size() - 1);
        } else {
            isFull = false;
        }
    }

    private void addLastPic() {
        picPathList.add(new PicRVBean( defultAddPic));
    }


    private class PicRVBean {
        String picPath;
        int picRes;
        boolean isAddPic;
        boolean isNetPic;

        public PicRVBean(String picPath, boolean isNetPic) {
            this.picPath = picPath;
            this.isNetPic = isNetPic;
            this.isAddPic = false;
        }

        public PicRVBean(int picRes) {
            this.picRes = picRes;
            this.isAddPic = true;
        }
    }



    private class PicRVAdapter extends BaseQuickAdapter<PicRVBean, BaseViewHolder> {
        private RequestOptions requestOptions = null;
        private PicRVAdapter(int layoutResId, @Nullable List<PicRVBean> data) {
            super(layoutResId, data);
            if(null==requestOptions){
                RoundedCorners roundedCorners= new RoundedCorners(20);
                requestOptions = new RequestOptions()
                        .transform(roundedCorners)//圆角和centercrop不同时生效，待处理
                        .centerCrop();
            }
        }

        @Override
        protected void convert(BaseViewHolder helper, PicRVBean item) {
            ImageView ivPic = helper.getView(R.id.iv_pic);
            ImageView ivDelete = helper.getView(R.id.iv_delete);

            if (item.isAddPic) {
                ivDelete.setVisibility(GONE);
                Glide.with(mContext).load(item.picRes).apply(requestOptions).into(ivPic);
            } else {
                ivDelete.setVisibility(VISIBLE);
                Glide.with(mContext).load(item.picPath).apply(requestOptions).into(ivPic);
            }
            helper.addOnClickListener(R.id.iv_delete);
        }
    }
}
