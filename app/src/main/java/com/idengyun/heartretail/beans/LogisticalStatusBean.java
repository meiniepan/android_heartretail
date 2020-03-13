package com.idengyun.heartretail.beans;

/**
 * @类名称: CLASS
 * @类描述:
 * @创建人：Burning
 * @创建时间：2020/3/12 16:50
 * @备注：
 */
public class LogisticalStatusBean {

    public String statusName;
    public int isCurrentStatus;


    public LogisticalStatusBean(String statusName, int isCurrentStatus) {
        this.statusName = statusName;
        this.isCurrentStatus = isCurrentStatus;
    }


}
