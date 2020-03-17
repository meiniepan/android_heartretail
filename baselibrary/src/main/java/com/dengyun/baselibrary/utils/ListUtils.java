package com.dengyun.baselibrary.utils;

import java.util.Arrays;
import java.util.List;

/**
 * @Title List相关的工具方法
 * @Author: zhoubo
 * @CreateDate: 2019/1/24 4:14 PM
 */
public class ListUtils {
    public static boolean isEmpty(List list){
        if(list!=null&&list.size()>0){
            return false;
        }
        return true;
    }

    public static boolean isAllEmpty(List... lists){
        for (int i = 0; i < lists.length; i++) {
            if(!isEmpty(lists[i])){
                return false;
            }
        }
        return true;
    }

    public static String listToString(List<String> list,String separator){
        if(isEmpty(list)) return "";
        StringBuilder sbd = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sbd.append(list.get(i));
            if(i!=list.size()-1){
                sbd.append(separator);
            }
        }
        return sbd.toString();
    }

    public static List intArrayToList(int[] protocols){
        int size = protocols.length;
        Integer[] array = new Integer[size];
        for (int i = 0; i < protocols.length; i++) {
            Integer integer = protocols[i];
            array[i] = integer;
        }
        return Arrays.asList(array);
    }
}
