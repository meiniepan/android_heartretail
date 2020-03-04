package com.dengyun.splashmodule.beans;

/**
 * @Title 主配置中解析的每一个url的实体
 * @Author: zhoubo
 * @CreateDate: 2020-03-04 11:32
 */
public class UrlConfigBean {
    public int urlId;//接口id
    public String urlName;//接口名
    public String urlCode;//接口key，不能重复
    public String urlHead;//接口地址http://10.10.8.22:3000/mock/39
    public String urlTail;//接口资源地址/config/query
    public int checkLogin;//校验是否登陆（header的头部token合法）0否1是
}
