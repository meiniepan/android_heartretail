package com.dengyun.baselibrary.config;

/**
 * @Title 全局属性
 * @Author: zhoubo
 * @CreateDate: 2019-05-14 13:56
 */
public class GlobalProperty {
    private String provinceName = "北京市";//定位的省（定位未成功时为“北京市”）
    private String provinceId = "北京市";//定位的省（定位未成功时为“北京市”）

    private String cityName = "北京市";//定位的市（定位未成功时为“北京市”）
    private String cityId = "北京市";//定位的市（定位未成功时为“北京市”）

    private String districtName = "朝阳区";//定位的区（定位未成功时为“朝阳区”）
    private String districtId = "朝阳区";//定位的区（定位未成功时为“朝阳区”）

    private double longitude = 116.481270d;//定位的经度
    private double latitude = 39.912010d;//定位的纬度


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

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
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

}
