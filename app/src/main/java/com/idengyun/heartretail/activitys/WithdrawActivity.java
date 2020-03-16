package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.base.dialog.BaseDialog;
import com.dengyun.baselibrary.base.dialog.BaseDialogFragment;
import com.dengyun.baselibrary.base.dialog.ViewConvertListener;
import com.dengyun.baselibrary.base.dialog.listener.DialogViewHolder;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.widget.WithdrawPopupWindow;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Burning
 * @description:
 * @date :2020/3/16 0016 10:50
 */
public class WithdrawActivity extends BaseActivity {
    @BindView(R.id.et_input_num)
    EditText etInputNum;
    @BindView(R.id.tv_can_sum)
    TextView tvCanSum;
    @BindView(R.id.tv_withdraw_spec)
    TextView tvWithdrawSpec;
    @BindView(R.id.tv_bank_add)
    TextView tvBankAdd;
    @BindView(R.id.tv_bank_refresh)
    TextView tvBankRefresh;
    @BindView(R.id.iv_bank)
    ImageView ivBank;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_bank_type)
    TextView tvBankType;
    @BindView(R.id.tv_bank_code)
    TextView tvBankCode;
    @BindView(R.id.cb_protocol3)
    CheckBox cbProtocol3;
    @BindView(R.id.tv_protocol3)
    TextView tvProtocol3;
    @BindView(R.id.ll_protoco3)
    LinearLayout llProtoco3;
    @BindView(R.id.tv_fast_withdraw)
    TextView tvFastWithdraw;
    private PopupWindow popupWindow;

    public static void start(Context context) {
        Intent starter = new Intent(context, WithdrawActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initPopupWindow();
    }

    private void initPopupWindow() {
        if (popupWindow == null) {
            popupWindow = new WithdrawPopupWindow(this);
        }
    }
    @OnClick({R.id.tv_bank_add, R.id.tv_bank_refresh, R.id.tv_fast_withdraw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bank_add:
                break;
            case R.id.tv_bank_refresh:
                break;
            case R.id.tv_fast_withdraw:
                BaseDialog.init().setLayoutId(R.layout.dialog_withdraw)
                        .setConvertListener(new ViewConvertListener() {
                            @Override
                            protected void convertView(DialogViewHolder holder, BaseDialogFragment dialog) {
                                holder.getView(R.id.tv_withdraw_now).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ToastUtils.showShort("立即");
                                    }
                                });
                            }
                        })
                        .setWidthMarginDp(30)
                        .show(getSupportFragmentManager());

                break;
        }
    }
}
