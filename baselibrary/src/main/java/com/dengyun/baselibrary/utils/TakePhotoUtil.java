package com.dengyun.baselibrary.utils;

import android.app.Activity;

import com.dengyun.baselibrary.base.dialog.BaseBottomItemDialog;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

/**
 * Created by seven on 2016/12/20.
 */

public class TakePhotoUtil {

    /**
     * @param activity 请求的activity
     * @param isCrop   是否剪切
     */
    public static void takePhotoWithItem(Activity activity, boolean isCrop) {
        takePhotoWithItem(activity, isCrop, -1, 0);
    }


    /**
     * @param activity    请求的activity
     * @param isCrop      是否剪切
     * @param requestCode 请求码
     */
    public static void takePhotoWithItem(Activity activity, boolean isCrop, int requestCode) {
        takePhotoWithItem(activity, isCrop, -1, requestCode);
    }

    /**
     * @param activity     请求的activity
     * @param isCrop       是否剪切
     * @param maxSelectNum 最大选择图片数量
     * @param requestCode  请求码
     */
    public static void takePhotoWithItem(Activity activity, boolean isCrop, int maxSelectNum, int requestCode) {
        BaseBottomItemDialog.newBuilder(activity)
                .addSheetItem("用相机更换", BaseBottomItemDialog.Builder.SheetItemColor.Blue, (dialog, which) -> {
                    dialog.dismiss();
                    AndPermission.with(activity)
                            .runtime()
                            .permission(Permission.Group.STORAGE,
                                    Permission.Group.CAMERA)
                            .onGranted(data -> {
                                PictureSelectionModel model = PictureSelector.create(activity)
                                        .openCamera(PictureMimeType.ofImage())
                                        .enableCrop(isCrop)
                                        .compress(true);
                                if (maxSelectNum > 0) model.maxSelectNum(maxSelectNum);
                                model.minimumCompressSize(200).forResult(requestCode == 0 ? PictureConfig.CAMERA : requestCode);
                            }).onDenied(data -> {
                        ToastUtils.showShort("拒绝了相机权限");
                    }).start();
                })
                .addSheetItem("去相册选择", BaseBottomItemDialog.Builder.SheetItemColor.Blue, (dialog, which) -> {
                    dialog.dismiss();
                    AndPermission.with(activity)
                            .runtime()
                            .permission(Permission.Group.STORAGE,
                                    Permission.Group.CAMERA)
                            .onGranted(data -> {
                                PictureSelectionModel model = PictureSelector.create(activity)
                                        .openGallery(PictureMimeType.ofImage())
                                        .enableCrop(isCrop)
                                        .compress(true)
                                        .isCamera(false);
                                if (maxSelectNum > 0) model.maxSelectNum(maxSelectNum);
                                model.minimumCompressSize(200)
                                        .selectionMode(PictureConfig.SINGLE)
                                        .forResult(requestCode == 0 ? PictureConfig.CHOOSE_REQUEST : requestCode);

                            }).onDenied(data -> {

                    }).start();
                }).build().show();
    }

}
