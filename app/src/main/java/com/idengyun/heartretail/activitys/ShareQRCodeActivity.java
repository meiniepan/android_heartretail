package com.idengyun.heartretail.activitys;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.utils.ImageUtils;
import com.dengyun.baselibrary.utils.SizeUtils;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.utils.bar.StatusBarUtil;
import com.dengyun.baselibrary.utils.phoneapp.PhoneUtils;
import com.dengyun.downloadlibrary.download.DownloadUtil;
import com.dengyun.sharelibrary.callback.OnShareResult;
import com.dengyun.sharelibrary.config.ShareChannelConstants;
import com.dengyun.sharelibrary.utils.ShareOptions;
import com.dengyun.sharelibrary.utils.ShareUtil;
import com.idengyun.heartretail.R;
import com.umeng.socialize.UMShareAPI;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.yzq.zxinglibrary.encode.CodeCreator;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareQRCodeActivity extends BaseActivity {

    @BindView(R.id.tv_invitation_code)
    TextView tvInvitationCode;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    @BindView(R.id.iv_qr_code)
    ImageView ivQrCode;
    @BindView(R.id.tv_save_share)
    TextView tvSaveShare;

    private String invitationCode = "A1B2C3D4F5G6";
    private Bitmap qrCodeBitmap;

    @Override
    public void setStatusBar() {
        StatusBarUtil.setTransparent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share_qrcode;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvInvitationCode.setText(invitationCode);
        createQRCode();
    }

    @OnClick({R.id.tv_copy, R.id.tv_save_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_copy:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(invitationCode);
                ToastUtils.showShort("复制成功");
                break;
            case R.id.tv_save_share:
                saveAndShare();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ShareUtil.onActivityResult(this,requestCode,resultCode,data);
    }

    private void createQRCode() {
        String qrUrl = "" + "?appType=0&recommendType=1&recommendCode=" + invitationCode;

        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo_square);
        qrCodeBitmap = CodeCreator.createQRCode(qrUrl, SizeUtils.dp2px(56), SizeUtils.dp2px(56), logo);
        ivQrCode.setImageBitmap(qrCodeBitmap);
    }

    private Bitmap getShareBitmap(){
        int width = SizeUtils.dp2px(150);
        int height = SizeUtils.dp2px(300);

        //创建分享的图片的画布
        Bitmap shareBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(shareBitmap);
        Rect bgRect = new Rect(0,0,width,height);
        Bitmap bgBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.splash_bg_bg);
        //绘制分享图片
        canvas.drawBitmap(bgBitmap,null,bgRect,null);
        canvas.drawBitmap(qrCodeBitmap,(width-qrCodeBitmap.getWidth())/2,(height-qrCodeBitmap.getHeight())/2+height/4,null);
        return shareBitmap;
    }

    private void saveAndShare(){
        Bitmap shareBitmap = getShareBitmap();
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        ToastUtils.showShort("拒绝了读写权限");
                    }
                }).onGranted(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
                String shareImgPath = getFilesDir().getAbsolutePath() + "/download/xls_share_qr_img.png";
                boolean isSaveSuccess = ImageUtils.save(shareBitmap, shareImgPath, Bitmap.CompressFormat.JPEG,false);
                if (isSaveSuccess){
                    ShareOptions shareOptions = ShareOptions.newBuilder(ShareQRCodeActivity.this)
                            .shareChannel("1_4_0")
                            .shareTitle("图片")
                            .shareImgBitmap(shareBitmap)
                            .build();
                    ShareUtil.shareWithPermission(shareOptions, new OnShareResult() {
                        @Override
                        public void onShareSuccess(ShareOptions shareOptions, String shareChannel) {

                        }
                    });
                }else {
                    ToastUtils.showShort("保存失败");
                }
            }
        }).start();
    }
}
