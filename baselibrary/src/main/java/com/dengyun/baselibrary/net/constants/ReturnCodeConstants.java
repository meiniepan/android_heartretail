package com.dengyun.baselibrary.net.constants;

import com.dengyun.baselibrary.net.NetOption;

/**
 * @titile
 * @desc Created by seven on 2018/4/4.
 */

public class ReturnCodeConstants {

    /*------------------------------------------mtmy code常量--------------------------------------*/

    //每天美耶全局拦截code，在application中全局配置拦截中操作
    private static String[] MTMY_DEAL_GLOBAL_CODES = {"10034"};
    //每天美耶需要处理的code，返回到success，例如成功、升级
    private static String[] MTMY_DEAL_CODES = {
            "200",  // 请求成功
            //"10004",// 用户名或密码错误
            "10031",
            "10026",// 版本过低
            "10027",// 超出购买限制
            "10014",// 购物车为空
            "10018",// 库存不足
            "10035",// 购买商品包含已失效商品
            "10036",// 红包已失效
            "10025",// 购买商品包含下架商品
            "10019",// 小主，该红包已领取过！
            "10030",// 小主，该红包已被领完！
            "10033" // 小主，超出领取数量了！
    };

    /*------------------------------------------fzx code常量--------------------------------------*/

    //妃子校全局拦截code，在application中全局配置拦截中操作
    private static String[] FZX_DEAL_GLOBAL_CODES = {"10014","10017"};
    //妃子校需要处理的code，返回到success，例如成功、升级
    private static String[] FZX_DEAL_CODES = {
            "200",  // 请求成功
            "10037",//账户被冻结
            "10023"//此机构未签约！请先签约
//            "10027",//该手机号已注册 --（不处理直接吐司）
//            "10028",//昵称已存在 --（不处理直接吐司）
//            "10002",//获取验证码失败 --（不处理直接吐司）
//            "10003",//验证码错误 --（不处理直接吐司）
//            "10004",//用户名或密码错误 --（不处理直接吐司）
//            "10005",//参数错误 --（不处理直接吐司）
//            "10016"//非法请求 --（不处理直接吐司）
    };

    /*-----------------------------------------im code常量--------------------------------------*/

    //IM全局拦截code，在application中全局配置拦截中操作
    private static String[] IM_DEAL_GLOBAL_CODES = {};
    //IM需要处理的code，返回到success，
    private static String[] IM_DEAL_CODES = {
            "10001"//请求成功
            //"10002",//请求失败
            //"10003",//参数缺失
            //"10004",//参数值含空
            //"10005",//验签失败
            //"10006",//该用户不存在
            //"10007",//该用户已经是好友了
            //"10008",//用户群组数量超出限制，取code
            //"10009",//群组用户参与群组数量超出限制，取data（List)
            //"10010",//改群组成员数量超出限制，取msg
    };

    /*------------------------------------------sobot code常量--------------------------------------*/

    //sobot客服全局拦截code，在application中全局配置拦截中操作
    private static String[] SOBOT_DEAL_GLOBAL_CODES = {};
    //sobot客服需要处理的code，返回到success，例如成功、升级
    private static String[] SOBOT_DEAL_CODES = {
            "200",  // 请求成功
    };

    /*------------------------------------------心零售 code常量--------------------------------------*/

    //心零售全局拦截code，在application中全局配置拦截中操作
    private static String[] HR_DEAL_GLOBAL_CODES = {};
    //心零售需要处理的code，返回到success，例如成功、升级
    private static String[] HR_DEAL_CODES = {
            "200",  // 请求成功
    };


    /**
     * 是否是需要返回处理的code
     * 这些会返回到onSuccess里，当做正确返回，和正常的success一样
     * @param netOption
     * @param code
     * @return
     */
    public static boolean isContainsDealCode(NetOption netOption, String code){
        if(netOption.getProjectType()==ProjectType.IDENGYUN_MTMY){
            //每天美耶项目，判断code是否是需要返回success处理的code
            return isContains(MTMY_DEAL_CODES,code);
        }else if(netOption.getProjectType()==ProjectType.IDENGYUN_FZX){
            //妃子校项目，判断code是否是需要返回success处理的code
            return isContains(FZX_DEAL_CODES,code);
        }else if(netOption.getProjectType()==ProjectType.FZX_CS){
            //妃子校项目，判断code是否是需要返回success处理的code
            return isContains(FZX_DEAL_CODES,code);
        }else if(netOption.getProjectType()==ProjectType.FZX_MS){
            //妃子校项目，判断code是否是需要返回success处理的code
            return isContains(FZX_DEAL_CODES,code);
        }else if(netOption.getProjectType()==ProjectType.FZX_DT){
            //妃子校项目，判断code是否是需要返回success处理的code
            return isContains(FZX_DEAL_CODES,code);
        }else if(netOption.getProjectType()==ProjectType.IDENGYUN_IM){
            //IM项目，判断code是否是需要返回success处理的code
            return isContains(IM_DEAL_CODES,code);
        }else if(netOption.getProjectType()==ProjectType.IDENGYUN_SOBOT){
            //智齿项目，判断code是否是需要返回success处理的code
            return isContains(SOBOT_DEAL_CODES,code);
        }else if(netOption.getProjectType()==ProjectType.IDENGYUN_HR){
            //心零售项目，判断code是否是需要返回success处理的code
            return isContains(HR_DEAL_CODES,code);
        }
        //不进行配置code，返回true（全返回到success)
        return true;
    }

    /**
     * 是否是需要全局处理的code （例如token过期）
     * @param code
     * @return
     */
    public static boolean isContainsGlobalDealCode(NetOption netOption,String code) {
        if(netOption.getProjectType()==ProjectType.IDENGYUN_MTMY){
            //每天美耶项目，判断code是否是需要返回success处理的code
            return isContains(MTMY_DEAL_GLOBAL_CODES,code);
        }else if(netOption.getProjectType()==ProjectType.IDENGYUN_FZX){
            //妃子校项目，判断code是否是需要返回success处理的code
            return isContains(FZX_DEAL_GLOBAL_CODES,code);
        }else if(netOption.getProjectType()==ProjectType.FZX_CS){
            //妃子校项目，判断code是否是需要返回success处理的code
            return isContains(FZX_DEAL_GLOBAL_CODES,code);
        }else if(netOption.getProjectType()==ProjectType.FZX_MS){
            //妃子校项目，判断code是否是需要返回success处理的code
            return isContains(FZX_DEAL_GLOBAL_CODES,code);
        }else if(netOption.getProjectType()==ProjectType.FZX_DT){
            //妃子校项目，判断code是否是需要返回success处理的code
            return isContains(FZX_DEAL_GLOBAL_CODES,code);
        }else if(netOption.getProjectType()==ProjectType.IDENGYUN_IM){
            //IM项目，判断code是否是需要返回success处理的code
            return isContains(IM_DEAL_GLOBAL_CODES,code);
        }else if(netOption.getProjectType()==ProjectType.IDENGYUN_SOBOT){
            //IM项目，判断code是否是需要返回success处理的code
            return isContains(SOBOT_DEAL_GLOBAL_CODES,code);
        }else if(netOption.getProjectType()==ProjectType.IDENGYUN_HR){
            //心零售项目，判断code是否是需要返回success处理的code
            return isContains(HR_DEAL_GLOBAL_CODES,code);
        }
        //不进行配置code，返回false，不进行全局拦截,继续向下判断
        return false;
    }

    private static boolean isContains(String[] strArr,String key){
        for (String s : strArr) {
            if (s.equals(key)){
                return true;
            }
        }
        return false;
    }
}
