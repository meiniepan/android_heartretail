package com.sobot.chat.utils;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dengyun.baselibrary.config.RouterPathConfig;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.constants.ProjectType;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.lzy.okgo.model.Response;
import com.sobot.chat.SobotApi;
import com.sobot.chat.SpSobotUtils;
import com.sobot.chat.api.model.ConsultingContent;
import com.sobot.chat.api.model.Information;
import com.sobot.chat.beans.KefuFlagBean;
import com.sobot.chat.dengy.bean.SobotUserBean;


/**
 * Created by liupeng on 2017/10/11.
 * <p>
 * 需要增加  queryOrderListForKF 查询订单接口
 * kefuFlag            配置接口
 */

public class InformationUtil {
    private Context mContext;
    private Information info;

    public InformationUtil(Context context) {
        SobotApi.initPlatformUnion(context.getApplicationContext(), "1001");
        SobotApi.setEvaluationCompletedExit(context, true);
        info = new Information();
        mContext = context;
    }

    /**
     * @param type 订单，商品
     * @param msg  拼接好后的消息内容
     *             如果不传入商品和订单传入null
     */
    public void startSobot(String type, String msg) {
        if (!SpSobotUtils.isLogin()) {
            SpSobotUtils.skipLogin(mContext);
        } else {
            if (!TextUtils.isEmpty(type) && !TextUtils.isEmpty(msg)) {
                setConsulting(type, msg);//商品/订单卡信息
            }

            NetOption netOption = NetOption.newBuilder(SpSobotUtils.queryZCService())
                    .params("userId", SpSobotUtils.getUserId())
                    .isShowDialog(false)
                    .isEncrypt(true)
                    .clazz(KefuFlagBean.class)
                    .projectType(ProjectType.IDENGYUN_SOBOT)
                    .build();
            NetApi.getData(netOption, new JsonCallback<KefuFlagBean>(netOption) {
                @Override
                public void onSuccess(Response<KefuFlagBean> response) {
                    KefuFlagBean bean = response.body();
                    if ("0".equals(bean.data.kefuFlag)) {
                        //appKey
                        info.setAppkey(bean.data.appKeyAndroid);//我们平台聊天的key
                        info.setUid(SpSobotUtils.getUserId()); //注意：uid为用户唯一标识，不能传入一样的值
                        info.setFace(SpSobotUtils.getUserHeadPic());//自定义头像，选填
                        if (bean.data.user == null) {
                            ToastUtil.showToast(mContext, "无法获取用户信息，请尝试重新登陆");
                        }
                        info.setUname("0".equals(bean.data.nickIsShow) ? bean.data.user.nickName : ""); //用户昵称，选填
                        info.setRealname("0".equals(bean.data.realnameIsShow) ? bean.data.user.realName : "");//真实姓名
                        info.setTel("0".equals(bean.data.phoneIsShow) ? bean.data.user.mobile : "");//电话
                        info.setQq("0".equals(bean.data.qqIsShow) ? bean.data.user.qq : "");//QQ
                        info.setRemark("0".equals(bean.data.remarkIsShow) ? bean.data.user.remark : "");//备注
                        info.setSkillSetId(bean.data.teamId);//技能组id
                        info.setColor("#333333");//标题栏颜色

                        SobotApi.startSobotChat(mContext, info, getUser());

                    } else if ("1".equals(bean.data.kefuFlag)) {
                        // TODO: 2017/11/10 去QQ客服
                        String kefu = bean.data.QQ
                                + "&groupId=" + bean.data.teamId
                                + "&partnerId=" + SpSobotUtils.getUserId()
                                + "&tel=" + bean.data.user.mobile
                                + "&uname=" + bean.data.user.realName
                                + "&face=" + SpSobotUtils.getUserHeadPic();
                        ARouter.getInstance().build(RouterPathConfig.app_SomewebActivity)
                                .withString("weburl", kefu)
                                .withString("title", "联系客服")
                                .withInt("type", 3)
                                .navigation();
                    }
                }
            });

        }


    }

    /**
     * 传递给智齿智齿客服界面，用于请求订单信息的数据
     * orderUrl  是订单数据接口
     * isShowOrder 0:不显示订单按钮，1：显示订单按钮
     * fromWhere  来自哪个平台   mtmy:每天美耶  blln:蓓丽莲娜  fzx:妃子校
     */
    private SobotUserBean getUser() {
        SobotUserBean sobotUserBean = new SobotUserBean();
        sobotUserBean.isShowOrder = "0";
        sobotUserBean.isShowAfterOrder = "0";
        sobotUserBean.fromWhere = "mtmy";
        return sobotUserBean;
    }

    /**
     * 卡片信息
     *
     * @param type       订单/商品
     * @param orderGoods 拼接好的订单/商品数据
     */
    private void setConsulting(String type, String orderGoods) {
        ConsultingContent consultingContent = new ConsultingContent();
        //必填  类型
        consultingContent.setSobotGoodsTitle(type);
        //必填  消息内容
        consultingContent.setSobotGoodsFromUrl(orderGoods);
        info.setConsultingContent(consultingContent);
    }

}
