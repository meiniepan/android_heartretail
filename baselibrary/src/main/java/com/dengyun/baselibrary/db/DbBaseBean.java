package com.dengyun.baselibrary.db;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

/**
 * author : JunDao
 * date   : 2019/8/5 14:48
 * desc   :数据库基类
 */
public class DbBaseBean extends LitePalSupport {
    private int id;
    private String user_id;
    private Date create_time;
    private Date update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
