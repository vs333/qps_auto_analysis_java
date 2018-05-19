package com.showjoy.qps.model;

import java.sql.Timestamp;

public class QpsLimit {
    private Integer id;
    private String url;
    private Integer limitqps;
    private Timestamp updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getLimitqps() {
        return limitqps;
    }

    public void setLimitqps(Integer limitqps) {
        this.limitqps = limitqps;
    }

    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp timestamp) {
        this.updatetime = updatetime;
    }
}
