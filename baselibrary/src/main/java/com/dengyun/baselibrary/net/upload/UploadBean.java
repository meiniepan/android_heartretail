package com.dengyun.baselibrary.net.upload;

/**
 * Created by seven on 2016/5/9.
 */
public class UploadBean {

    public int code;
    public boolean success;
    public String msg;
    public Data data;

    public class Data{
        public String filePath;
    }

}
