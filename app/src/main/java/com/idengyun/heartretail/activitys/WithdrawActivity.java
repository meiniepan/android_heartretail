package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.idengyun.heartretail.R;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    }


    @OnClick({R.id.tv_bank_add, R.id.tv_bank_refresh, R.id.tv_fast_withdraw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bank_add:
                break;
            case R.id.tv_bank_refresh:
                break;
            case R.id.tv_fast_withdraw:
                break;
        }
    }
}
