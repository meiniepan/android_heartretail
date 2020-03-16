package com.dengyun.baselibrary.net.upload;

/**
 * Created by seven on 2016/5/9.
 */
public class UploadBean {

    /**
     * message : 成功
     * result : 200
     * file_url : http://resource.idengyun.com/resource/images/2016/05/05451722-bdf2-43d7-b281-68c230c685d8.png
     * file_url0 : null
     */

    public int code;
    public boolean success;
    public String msg;
    public Data data;

    public class Data{
        public String filePath;
    }

}
