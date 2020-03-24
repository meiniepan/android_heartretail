package com.idengyun.heartretail.activitys;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.utils.ImageUtils;
import com.dengyun.baselibrary.utils.ScreenUtil;
import com.dengyun.baselibrary.utils.SizeUtils;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.utils.bar.StatusBarUtil;
import com.dengyun.baselibrary.widgets.toolbar.BaseToolBar;
import com.dengyun.sharelibrary.callback.OnShareResult;
import com.dengyun.sharelibrary.config.ShareChannelConstants;
import com.dengyun.sharelibrary.utils.ShareOptions;
import com.dengyun.sharelibrary.utils.ShareUtil;
import com.idengyun.heartretail.R;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.yzq.zxinglibrary.encode.CodeCreator;

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
    @BindView(R.id.rl_share_parent)
    RelativeLayout rlShareParent;
    @BindView(R.id.toolbar_share_qr)
    BaseToolBar toolbarShareQr;

    private String invitationCode = "A1B2C3D4F5G6";

    public static void start(Context context) {
        Intent starter = new Intent(context, ShareQRCodeActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
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
        ShareUtil.onActivityResult(this, requestCode, resultCode, data);
    }

    private void createQRCode() {
        String qrUrl = "" + "?appType=0&recommendType=1&recommendCode=" + invitationCode;
        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo_share_qr);
        Bitmap qrCodeBitmap = CodeCreator.createQRCode(qrUrl, SizeUtils.dp2px(200), SizeUtils.dp2px(200), logo);
        ivQrCode.setImageBitmap(qrCodeBitmap);
    }

    private Bitmap getShareBitmap() {
        toolbarShareQr.setVisibility(View.GONE);
        tvCopy.setVisibility(View.GONE);
        tvSaveShare.setVisibility(View.GONE);
        Bitmap bitmapNoCompress = ImageUtils.view2Bitmap(rlShareParent);
        Bitmap bitmap = ImageUtils.compressBySampleSize(bitmapNoCompress,ScreenUtil.getScreenWidth()/3,ScreenUtil.getScreenHeight()/3,true);
        toolbarShareQr.setVisibility(View.VISIBLE);
        tvCopy.setVisibility(View.VISIBLE);
        tvSaveShare.setVisibility(View.VISIBLE);
        return bitmap;
    }

    private void saveAndShare() {
        Bitmap shareBitmap = getShareBitmap();
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        shareBitmap.recycle();
                        ToastUtils.showShort("拒绝了读写权限");
                    }
                }).onGranted(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
                String shareImgPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/xls/xls_share_qr_img.png";
                Logger.d("download  ===   " + shareImgPath);
                boolean isSaveSuccess = ImageUtils.save(shareBitmap, shareImgPath, Bitmap.CompressFormat.JPEG, false);
                if (isSaveSuccess) {
                    ShareOptions shareOptions = ShareOptions.newBuilder(ShareQRCodeActivity.this)
                            .shareChannel("1_2_4_0")
                            .shareTitle("图片")
                            .shareBoardTitle("已保存至相册，请分享")
                            .shareImgBitmap(shareBitmap)
                            .build();
                    ShareUtil.shareWithPermission(shareOptions, new OnShareResult() {
                        @Override
                        public void onShareSuccess(ShareOptions shareOptions, String shareChannel) {
                            shareBitmap.recycle();
                        }

                        @Override
                        public void onShareError(ShareOptions shareOptions, String shareChannel, Throwable throwable) {
                            super.onShareError(shareOptions, shareChannel, throwable);
                            shareBitmap.recycle();
                        }

                        @Override
                        public void onShareCancel(ShareOptions shareOptions, String shareChannel) {
                            super.onShareCancel(shareOptions, shareChannel);
                            shareBitmap.recycle();
                        }
                    });
                } else {
                    ToastUtils.showShort("保存失败");
                    shareBitmap.recycle();
                }
            }
        }).start();
    }
}
