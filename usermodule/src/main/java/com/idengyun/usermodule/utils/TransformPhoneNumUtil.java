package com.idengyun.usermodule.utils;

import com.dengyun.baselibrary.utils.RegexUtils;

/**
 * Created by Burning on 2020/3/3.
 */

public class TransformPhoneNumUtil {

    public static String transform(String num) {
        if (RegexUtils.isMobileSimple(num)) {
            String head = num.substring(0, 3);
            String end = num.substring(7, 11);
            return head + "****" + end;
        }
        return "";
    }
}
