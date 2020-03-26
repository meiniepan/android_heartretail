package com.dengyun.splashmodule.beans;

/**
 * @Title 升级接口的实体类
 * @Author: zhoubo
 * @CreateDate: 2020-03-25 16:22
 */
public class CustomUpdateBean {
    public int isNewAppversion;//是否有新版本：0：有，1：没有
    public int verStatus;//是否需要更新 0 不需要，1 需要
    public String verNewno;//新版本号
    public String verDes;//标题
    public String verContent;//升级内容
    public String verUpdateurl;//更新连接
}
