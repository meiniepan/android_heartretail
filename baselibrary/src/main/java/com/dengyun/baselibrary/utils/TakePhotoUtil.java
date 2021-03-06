package com.dengyun.baselibrary.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.dengyun.baselibrary.base.dialog.BaseBottomItemDialog;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
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
        takePhotoWithItem(activity, isCrop,1,1, -1, 0);
    }
    public static void takePhotoWithItem(Fragment fragment, boolean isCrop) {
        takePhotoWithItem(fragment, isCrop,1,1, -1, 0);
    }

    /**
     * @param activity    请求的activity
     * @param isCrop      是否剪切
     * @param requestCode 请求码
     */
    public static void takePhotoWithItem(Activity activity, boolean isCrop, int requestCode) {
        takePhotoWithItem(activity, isCrop, 1,1,-1, requestCode);
    }
    public static void takePhotoWithItem(Fragment fragment, boolean isCrop, int requestCode) {
        takePhotoWithItem(fragment, isCrop,1,1, -1, requestCode);
    }

    /**
     * @param activity     请求的activity
     * @param isCrop       是否剪切
     * @param scaleX       裁剪：xy比例中的x
     * @param scaleY       裁剪：xy比例中的Y
     * @param maxSelectNum 最大选择图片数量
     * @param requestCode  请求码
     */
    public static void takePhotoWithItem(Activity activity, boolean isCrop, int scaleX, int scaleY,int maxSelectNum, int requestCode) {
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
                                        .withAspectRatio(scaleX,scaleY)
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
                                        .withAspectRatio(1,1)
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

    /**
     * @param fragment     请求的fragment
     * @param isCrop       是否剪切
     * @param scaleX       裁剪：xy比例中的x
     * @param scaleY       裁剪：xy比例中的Y
     * @param maxSelectNum 最大选择图片数量
     * @param requestCode  请求码
     */
    public static void takePhotoWithItem(Fragment fragment, boolean isCrop,int scaleX, int scaleY,int maxSelectNum, int requestCode) {
        BaseBottomItemDialog.newBuilder(fragment.getContext())
                .addSheetItem("用相机更换", BaseBottomItemDialog.Builder.SheetItemColor.Blue, (dialog, which) -> {
                    dialog.dismiss();
                    AndPermission.with(fragment)
                            .runtime()
                            .permission(Permission.Group.STORAGE,
                                    Permission.Group.CAMERA)
                            .onGranted(data -> {
                                PictureSelectionModel model = PictureSelector.create(fragment)
                                        .openCamera(PictureMimeType.ofImage())
                                        .enableCrop(isCrop)
                                        .withAspectRatio(scaleX,scaleY)
                                        .compress(true);
                                if (maxSelectNum > 0) model.maxSelectNum(maxSelectNum);
                                model.minimumCompressSize(200).forResult(requestCode == 0 ? PictureConfig.CAMERA : requestCode);
                            }).onDenied(data -> {
                        ToastUtils.showShort("拒绝了相机权限");
                    }).start();
                })
                .addSheetItem("去相册选择", BaseBottomItemDialog.Builder.SheetItemColor.Blue, (dialog, which) -> {
                    dialog.dismiss();
                    AndPermission.with(fragment)
                            .runtime()
                            .permission(Permission.Group.STORAGE,
                                    Permission.Group.CAMERA)
                            .onGranted(data -> {
                                PictureSelectionModel model = PictureSelector.create(fragment)
                                        .openGallery(PictureMimeType.ofImage())
                                        .enableCrop(isCrop)
                                        .withAspectRatio(scaleX,scaleY)
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

    /**
     * 获取返回的路径（已经判断过是否压缩、采集：压缩的返回压缩后路径；裁剪过的返回裁剪过路径；不处理的返回原路径）
     * @param localMedia
     * @return
     */
    public static String getResultPath(LocalMedia localMedia){
        if (localMedia.isCompressed()){
            return localMedia.getCompressPath();
        }else if (localMedia.isCut()){
            return localMedia.getCutPath();
        }else {
            return localMedia.getPath();
        }
    }
}
