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

    private String message;
    private String result;
    private String file_url;
    private Object file_url0;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public Object getFile_url0() {
        return file_url0;
    }

    public void setFile_url0(Object file_url0) {
        this.file_url0 = file_url0;
    }
}
