package com.sobot.chat.utils;

import android.content.Context;
import android.text.TextUtils;
import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.utils.ListUtils;
import com.google.gson.reflect.TypeToken;
import com.idengyun.routermodule.IntentRouterUtils;
import com.lzy.okgo.model.Response;
import com.sobot.chat.SobotApi;
import com.sobot.chat.SpSobotUtils;
import com.sobot.chat.api.model.ConsultingContent;
import com.sobot.chat.api.model.Information;
import com.sobot.chat.beans.XlsKefuBean;
import com.sobot.chat.dengy.bean.SobotUserBean;
import com.sobot.chat.dengy.utils.SpliceUtil;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by liupeng on 2017/10/11.
 * new InformationUtil(getActivity()).startSobot(null, null);
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
     * @param context 测试商品发送客服
     */
    private static void testGoods(Context context) {
        String productSplice = SpliceUtil.getProductSplice("4169",
                "艾丽婷双头纸轴棉棒",
                "http://resource.idengyun.com/resource/images/2017/11/e2dc0dbe-e96a-41fd-abe1-b62d0cdc1872.jpg",
                4.8);
        new InformationUtil(context).startSobot("商品", productSplice);
    }

    public void startSobot() {
        startSobot(null, null);
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

            Type netType = new TypeToken<ApiBean<List<XlsKefuBean>>>() {
            }.getType();
            NetOption netOption = NetOption.newBuilder(SpSobotUtils.queryZCService())
                    .params("userId", SpSobotUtils.getUserId())
                    .isShowDialog(false)
                    .isEncrypt(true)
                    .type(netType)
                    .build();
            NetApi.<ApiBean<List<XlsKefuBean>>>getData(RequestMethod.GET, netOption,
                    new JsonCallback<ApiBean<List<XlsKefuBean>>>(netOption) {
                        @Override
                        public void onSuccess(Response<ApiBean<List<XlsKefuBean>>> response) {
                            List<XlsKefuBean> kefuBeans = response.body().data;
                            if (ListUtils.isEmpty(kefuBeans)) return;
                            Map<String, String> kefuBeanMap = new HashMap<>();
                            for (int i = 0; i < kefuBeans.size(); i++) {
                                kefuBeanMap.put(kefuBeans.get(i).getConfigKey(), kefuBeans.get(i).getConfigValue());
                            }

                            if ("0".equals(kefuBeanMap.get("kefuFlag"))) {
                                //appKey
                                info.setAppkey(kefuBeanMap.get("zcAppKeyAndroid"));//我们平台聊天的key
                                info.setUid(SpSobotUtils.getUserId()); //注意：uid为用户唯一标识，不能传入一样的值
                                info.setFace(SpSobotUtils.getUserHeadPic());//自定义头像，选填
                                info.setUname("0".equals(kefuBeanMap.get("nickIsShow")) ? SpSobotUtils.getUserNickName() : ""); //用户昵称，选填
                                info.setRealname("0".equals(kefuBeanMap.get("realnameIsShow")) ? SpSobotUtils.getUserRealName() : "");//真实姓名
                                info.setTel("0".equals(kefuBeanMap.get("phoneIsShow")) ? SpSobotUtils.getUserPhone() : "");//电话
                                info.setQq("0".equals(kefuBeanMap.get("qqIsShow")) ? SpSobotUtils.getUserQQ() : "");//QQ
                                info.setRemark("0".equals(kefuBeanMap.get("zcRemarkIsShow")) ? SpSobotUtils.getUserRemark() : "");//备注
                                info.setSkillSetId(kefuBeanMap.get("teamId"));//技能组id
                                info.setColor("#333333");//标题栏颜色

                                SobotApi.startSobotChat(mContext, info, getUser());

                            } else if ("1".equals(kefuBeanMap.get("kefuFlag")) && !TextUtils.isEmpty(SpSobotUtils.getUserQQ())) {
                                String kefu = SpSobotUtils.getUserQQ()
                                        + "&groupId=" + kefuBeanMap.get("teamId")
                                        + "&partnerId=" + SpSobotUtils.getUserId()
                                        + "&tel=" + SpSobotUtils.getUserPhone()
                                        + "&uname=" + SpSobotUtils.getUserNickName()
                                        + "&face=" + SpSobotUtils.getUserHeadPic();
                                IntentRouterUtils.skipToSomeWeb(kefu);
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
        sobotUserBean.isShowOrder = "1";
        sobotUserBean.isShowAfterOrder = "0";
        sobotUserBean.fromWhere = "xls";
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
