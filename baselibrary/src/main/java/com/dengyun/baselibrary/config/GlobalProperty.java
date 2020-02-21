package com.dengyun.baselibrary.config;

/**
 * @Title 全局属性
 * @Author: zhoubo
 * @CreateDate: 2019-05-14 13:56
 */
public class GlobalProperty {
    private String province = "北京市";//定位的省（定位未成功时为“北京市”）
    private String city = "北京市";//定位的市（定位未成功时为“北京市”）
    private String district = "朝阳区";//定位的区（定位未成功时为“朝阳区”）
    private String locationId = "";//定位的城市id（定位成功没有）
    private double longitude = 116.481270d;//定位的经度
    private double latitude = 39.912010d;//定位的纬度

    //埋点数据参数
    private String saProvince;
    private String saCity;
    private String saDistrict;
    private String saStreet;

    //业务全局参数
    private int company_id;  //当前用户的企业id

    private static GlobalProperty globalProperty;
    private GlobalProperty(){
    }
    public static GlobalProperty getInstance(){
        if(null==globalProperty){
            synchronized (GlobalProperty.class){
                if(null==globalProperty){
                    globalProperty = new GlobalProperty();
                }
            }
        }
        return globalProperty;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getSaProvince() {
        return saProvince;
    }

    public void setSaProvince(String saProvince) {
        this.saProvince = saProvince;
    }

    public String getSaCity() {
        return saCity;
    }

    public void setSaCity(String saCity) {
        this.saCity = saCity;
    }

    public String getSaDistrict() {
        return saDistrict;
    }

    public void setSaDistrict(String saDistrict) {
        this.saDistrict = saDistrict;
    }

    public String getSaStreet() {
        return saStreet;
    }

    public void setSaStreet(String saStreet) {
        this.saStreet = saStreet;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }
}
