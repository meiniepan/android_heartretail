package com.dengyun.baselibrary.net.deal;

import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.constants.ProjectType;
import com.dengyun.baselibrary.utils.GsonConvertUtil;
import com.dengyun.baselibrary.utils.ObjectUtils;
import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;
import com.dengyun.baselibrary.utils.encode.EncryptUtils;
import com.dengyun.baselibrary.utils.phoneapp.AppUtils;
import com.orhanobut.logger.Logger;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @titile 处理参数的工具类（参数添加公共参数、加密）
 * @desc Created by seven on 2018/4/10.
 */

public class DealParamsUtil {
    /**
     * 获取处理过的参数（添加公共参数、加密处理）
     *
     * @return 处理之后的参数Json
     */
    public static String getDealParams(NetOption netOption) {
        if (netOption.getProjectType() == ProjectType.IDENGYUN_MTMY) {
            //每天美耶项目配置参数
            return getDealMtmyParams(netOption.getParams(), netOption.isEncrypt());
        } else if (netOption.getProjectType() == ProjectType.IDENGYUN_FZX) {
            //妃子校项目配置参数
            return getDealFZXParams(netOption.getParams(), netOption.isEncrypt());
        } else if (netOption.getProjectType() == ProjectType.FZX_CS) {
            //妃子校美货项目配置参数
            return getDealFZXCSParams(netOption.getParams(), netOption.isEncrypt());
        } else if (netOption.getProjectType() == ProjectType.FZX_MS) {
            //妃子校自媒体项目配置参数
            return getDealFZXMSParams(netOption.getParams(), netOption.isEncrypt());
        } else if (netOption.getProjectType() == ProjectType.FZX_DT) {
            //妃子校地推项目配置参数
            return getDealFZXDTParams(netOption.getParams(), netOption.isEncrypt());
        } else if (netOption.getProjectType() == ProjectType.IDENGYUN_SOBOT) {
            //智齿客服项目配置参数
            return getDealSobotParams(netOption.getParams(), netOption.isEncrypt());
        } else if (netOption.getProjectType() == ProjectType.IDENGYUN_IM) {
            //IM项目配置参数
            //注：im项目使用的是form表单上传，处理过（公共参数、加密)的参数生成json，然后使用一个表单字段上传，
            //  这里替换掉netOption中的params参数，替换成一个key，value为加密之后的参数json
            String imDealParams = getDealIMParams(netOption.getParams(), netOption.isEncrypt());
            netOption.getParams().clear();
            netOption.getParams().put("paramString", imDealParams);
            return imDealParams;
        } else if (netOption.getProjectType() == ProjectType.IDENGYUN_HR) {
            return getDealHRParams(netOption, netOption.getParams(), netOption.isEncrypt());
        } else {
            return GsonConvertUtil.toJson(netOption.getParams());
        }
    }

    /**
     * 获取处理过的url（当请求是get请求时，参数在url上,可能需要拼接url）
     *
     * @return 处理之后的url
     */
    public static void dealUrlForGet(NetOption netOption) {
        if (netOption.getProjectType() == ProjectType.IDENGYUN_HR) {
            Map queryMap = netOption.getParams();
            //添加公共参数
            setHRPublicParam(queryMap);
            if (netOption.getUrl().contains("?")) return;

            /* 真实请求参数 */
            Object[] array = queryMap.keySet().toArray();
            Arrays.sort(array);
            //整理参数拼接
            StringBuilder builder = new StringBuilder().append("?");
            for (Object key : array) {
                Object value = queryMap.get(key);
                builder.append(key).append("=").append(value).append("&");
            }
            /* MD5加密参数 */
            //去掉sbd开头的"?"和结尾的"&"
            String parameters = builder.substring(1, builder.length() - 1);
            String sign = EncryptUtils.stringToMD5(parameters + "xls");
            netOption.addHeaders("sign", sign);

            String newUrl = netOption.getUrl() + builder.substring(0, builder.length() - 1);
            netOption.setUrl(newUrl);
        } else {
            //其他项目的暂时没有配置get方式的拼接处理
            //doNothing;
        }
    }

    public static String getDealMtmyParams(Map paramsMap, boolean isEncrypt) {
        setMtmyPublicParam(paramsMap);//添加公共参数
        if (isEncrypt) {
            return getMtmyEncryptJson(paramsMap);//每天美耶参数加密
        } else {
            return GsonConvertUtil.toJson(paramsMap);
        }
    }

    public static String getDealFZXParams(Map paramsMap, boolean isEncrypt) {
        setFZXPublicParam(paramsMap);//添加公共参数
        if (isEncrypt) {
            String secret = SharedPreferencesUtil.getData(Utils.getApp(), "main", "secret", "");
            return getFZXEncryptJson(paramsMap, secret);//妃子校参数加密
        } else {
            return GsonConvertUtil.toJson(paramsMap);
        }
    }

    public static String getDealFZXCSParams(Map paramsMap, boolean isEncrypt) {
        paramsMap.put("ver_num", SharedPreferencesUtil.getData(Utils.getApp(), "main", "cs_ver_num", ""));
        setFZXPublicParam(paramsMap);//添加公共参数
        if (isEncrypt) {
            String cs_secret = SharedPreferencesUtil.getData(Utils.getApp(), "main", "cs_secret", "");
            return getFZXEncryptJson(paramsMap, cs_secret);//妃子校参数加密
        } else {
            return GsonConvertUtil.toJson(paramsMap);
        }
    }

    public static String getDealFZXMSParams(Map paramsMap, boolean isEncrypt) {
        setFZXPublicParam(paramsMap);//添加公共参数
        if (isEncrypt) {
            String ms_secret = SharedPreferencesUtil.getData(Utils.getApp(), "main", "ms_secret", "");
            return getFZXEncryptJson(paramsMap, ms_secret);//妃子校参数加密
        } else {
            return GsonConvertUtil.toJson(paramsMap);
        }
    }

    public static String getDealFZXDTParams(Map paramsMap, boolean isEncrypt) {
        setFZXPublicParam(paramsMap);//添加公共参数
        if (isEncrypt) {
            String secret_dt = SharedPreferencesUtil.getData(Utils.getApp(), "main", "marketService", "");
            return getFZXEncryptJson(paramsMap, secret_dt);//妃子校参数加密
        } else {
            return GsonConvertUtil.toJson(paramsMap);
        }
    }

    public static String getDealSobotParams(Map paramsMap, boolean isEncrypt) {
        setSobotPublicParam(paramsMap);//添加公共参数
        if (isEncrypt) {
            return getSobotEncryptJson(paramsMap);//智齿客服参数加密
        } else {
            return GsonConvertUtil.toJson(paramsMap);
        }
    }

    public static String getDealIMParams(Map paramsMap, boolean isEncrypt) {
        setIMPublicParam(paramsMap);//添加公共参数
        if (isEncrypt) {
            return getIMEncryptJson(paramsMap);//im参数加密
        } else {
            return GsonConvertUtil.toJson(paramsMap);
        }
    }

    public static String getDealHRParams(NetOption netOption, Map paramsMap, boolean isEncrypt) {
        setHRPublicParam(paramsMap);//添加公共参数
        String paramJson0 = GsonConvertUtil.toJson(paramsMap);
        if (isEncrypt) {
            //加密参数放在header中
            Object[] array = paramsMap.keySet().toArray();
            Arrays.sort(array);
            StringBuilder builder = new StringBuilder();
            for (Object key : array) {
                Object value = paramsMap.get(key);

                if (ObjectUtils.isPrimitive(value) || value instanceof String){
                    //判断是否是基本数据类型或者String类型
                    builder.append(key).append("=").append(value).append("&");
                }else {
                    //其他类型先转为Json字符串
                    builder.append(key).append("=").append(GsonConvertUtil.toJson(value)).append("&");
                }
            }
            /* MD5加密参数 */
            String parameters = builder.substring(0, builder.length() - 1);
            String sign = EncryptUtils.stringToMD5(parameters + "xls");
            netOption.addHeaders("sign", sign);
        }
        return paramJson0;

    }


    /**
     * 每天美耶参数加密
     *
     * @param map 加密之前的map
     * @return 加密之后的参数Json
     */
    private static String getMtmyEncryptJson(Map map) {
        String paramJson0 = GsonConvertUtil.toJson(map);
        String secret = SharedPreferencesUtil.getData(Utils.getApp(), "mainconfig", "secret", "");
        //加密字段拼接在请求json前后，得md5值
        String signMd5 = EncryptUtils.stringToMD5(secret + paramJson0 + secret);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("jsonStr", "mtmy" + paramJson0);
        paramMap.put("sign", signMd5);
        return GsonConvertUtil.toJson(paramMap);
    }

    /**
     * 妃子校参数加密
     *
     * @param map 加密之前的map
     * @return 加密之后的参数Json
     */
    private static String getFZXEncryptJson(Map map, String secret) {
        String paramJson0 = GsonConvertUtil.toJson(map);
        String signMd5 = EncryptUtils.stringToMD5(secret + paramJson0 + secret);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("jsonStr", secret + paramJson0);
        paramMap.put("sign", signMd5);
        return GsonConvertUtil.toJson(paramMap);
    }

    /**
     * 智齿客服参数加密
     *
     * @param map 加密之前的map
     * @return 加密之后的参数Json
     */
    private static String getSobotEncryptJson(Map map) {
        String paramJson0 = GsonConvertUtil.toJson(map);
        //  2020-03-18 测试客服功能先写死mtmy
//        String secret = SharedPreferencesUtil.getData(Utils.getApp(), "mainconfig", "secret", "");
        String secret = "mtmy";
        //加密字段拼接在请求json前后，得md5值
        String signMd5 = EncryptUtils.stringToMD5(secret + paramJson0 + secret);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("jsonStr", secret + paramJson0);
        paramMap.put("sign", signMd5);
        return GsonConvertUtil.toJson(paramMap);
    }

    /**
     * im参数加密
     *
     * @param map 加密之前的map
     * @return 加密之后的参数Json
     */
    private static String getIMEncryptJson(Map map) {
        //map中的key添加进ArrayList并排序
        Collection<String> paramsKeySet = map.keySet(); //ASCII排序
        Iterator paramIterator = paramsKeySet.iterator();
        List<String> list = new ArrayList(paramsKeySet.size());
        while (paramIterator.hasNext()) {
            list.add((String) paramIterator.next());
        }
        Collections.sort(list);//按字典升序排序

        //字符串拼接,md5获得加密签名
        StringBuffer sbu = new StringBuffer();
        sbu.append("appKey=im");//字符串签名拼接
        for (String key : list) {
            sbu.append("&" + key + "&");
        }
        sbu.append("&appId=im");//字符串后面拼接
        String signMd5 = EncryptUtils.stringToMD5(sbu.toString());
        map.put("sign", signMd5);
        return GsonConvertUtil.toJson(map);
    }

    /**
     * 每天美耶参数添加公共参数
     *
     * @param map 加密之前的参数map
     */
    public static void setMtmyPublicParam(Map map) {
        if (!map.containsKey("client")) map.put("client", "android");
        if (!map.containsKey("version")) map.put("version", AppUtils.getAppVersionName());
        if (SensorsDataAPI.sharedInstance(Utils.getApp()).getAnonymousId() != null) {
            if (!map.containsKey("distinct_id"))
                map.put("distinct_id", SensorsDataAPI.sharedInstance(Utils.getApp()).getAnonymousId());
        }
        //不需要传device_id
        if (!map.containsKey("device_id"))
            map.put("device_id", SensorsDataUtils.getAndroidID(Utils.getApp()) == null
                    ? "" :
                    SensorsDataUtils.getAndroidID(Utils.getApp()));
    }

    /**
     * 妃子校参数添加公共参数
     *
     * @param map 之前的参数map
     */
    public static void setFZXPublicParam(Map map) {
        if (!map.containsKey("client")) map.put("client", "android");
        if (!map.containsKey("ver_num")) map.put("ver_num", AppUtils.getAppVersionName());
        if (SensorsDataAPI.sharedInstance(Utils.getApp()).getAnonymousId() != null) {
            if (!map.containsKey("distinct_id"))
                map.put("distinct_id", SensorsDataAPI.sharedInstance(Utils.getApp()).getAnonymousId());
        }
        //不需要传device_id
        if (!map.containsKey("device_id"))
            map.put("device_id", SensorsDataUtils.getAndroidID(Utils.getApp()) == null
                    ? "" :
                    SensorsDataUtils.getAndroidID(Utils.getApp()));

    }

    /**
     * im参数添加公共参数
     *
     * @param map 之前的参数map
     */
    public static void setIMPublicParam(Map map) {
        if (!map.containsKey("client")) map.put("client", "android");
        if (!map.containsKey("version")) map.put("version", AppUtils.getAppVersionName());
    }

    /**
     * 智齿客服参数添加公共参数
     *
     * @param map 之前的参数map
     */
    public static void setSobotPublicParam(Map map) {
        if (!map.containsKey("client")) map.put("client", "android");
        if (!map.containsKey("version")) map.put("version", AppUtils.getAppVersionName());
    }

    /**
     * 心零售参数添加公共参数
     *
     * @param map 之前的参数map
     */
    public static void setHRPublicParam(Map<String, Object> map) {
        if (null == map.get("platform")) map.put("platform", "Android");
        if (null == map.get("version")) map.put("version", AppUtils.getAppVersionName());
    }

}
