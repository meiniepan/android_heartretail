package com.idengyun.heartretail.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by seven on 2016/8/1.
 */

public class DecimalFormatUtil {

    /**
    *   pi=12.34567;
        //取一位整数
        System.out.println(new DecimalFormat("0").format(pi));//12

        //取一位整数和两位小数
        System.out.println(new DecimalFormat("0.00").format(pi));//12.35

        //取两位整数和三位小数，整数不足部分以0填补。
        System.out.println(new DecimalFormat("00.000").format(pi));// 12.346

        //取所有整数部分
        System.out.println(new DecimalFormat("#").format(pi));//12

        //以百分比方式计数，并取两位小数
        System.out.println(new DecimalFormat("#.##%").format(pi));//1234.57%
    *
     *
     * @param format 格式化方式
     * @param num 待格式化数字
     * */
    public static String getFormatDecimal(String format,double num){
        return new DecimalFormat(format).format(num);
    }
}
