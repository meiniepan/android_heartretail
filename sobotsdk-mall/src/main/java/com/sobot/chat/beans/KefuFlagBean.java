package com.sobot.chat.beans;

/**
 * Created by liupeng on 2017/11/10.
 */

public class KefuFlagBean {

    /**
     * code : 200
     * data : {"QQ":"http://resource.idengyun.com/resource/2017/01/contactus.html","sexIsShow":"0","realnameIsShow":"0","remarkIsShow":"1","phoneIsShow":"1","cityIsShow":"1","qqIsShow":"0","kefuFlag":"0","nickIsShow":"0","teamId":"1ea89e5e225e4167ad45c4cdcd53ade6","user":{"city":"北京市","district":"西城区","mobile":"17600017835","nickName":"BL17102841199452","province":"北京市","qq":"123321","realName":"阿斯顿发","remark":"","sex":0}}
     * msg : 成功
     */

    public String code;
    public DataBean data;
    public String msg;

    public static class DataBean {
        /**
         * QQ : http://resource.idengyun.com/resource/2017/01/contactus.html
         * sexIsShow : 0
         * realnameIsShow : 0
         * remarkIsShow : 1
         * phoneIsShow : 1
         * cityIsShow : 1
         * qqIsShow : 0
         * kefuFlag : 0
         * nickIsShow : 0
         * teamId : 1ea89e5e225e4167ad45c4cdcd53ade6
         * user : {"city":"北京市","district":"西城区","mobile":"17600017835","nickName":"BL17102841199452","province":"北京市","qq":"123321","realName":"阿斯顿发","remark":"","sex":0}
         */

        public String QQ;
        public String sexIsShow;
        public String realnameIsShow;
        public String remarkIsShow;
        public String phoneIsShow;
        public String cityIsShow;
        public String qqIsShow;
        public String kefuFlag;
        public String nickIsShow;
        public String teamId;
        public UserBean user;
        public String appKeyAndroid;

        public static class UserBean {
            /**
             * city : 北京市
             * district : 西城区
             * mobile : 17600017835
             * nickName : BL17102841199452
             * province : 北京市
             * qq : 123321
             * realName : 阿斯顿发
             * remark :
             * sex : 0
             */

            public String city;
            public String district;
            public String mobile;
            public String nickName;
            public String province;
            public String qq;
            public String realName;
            public String remark;
            public int sex;
        }
    }
}
