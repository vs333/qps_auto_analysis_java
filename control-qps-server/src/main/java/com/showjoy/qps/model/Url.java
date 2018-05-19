package com.showjoy.qps.model;

import java.io.Serializable;

public class Url implements Serializable {
    private static final long serialVersionUID = -4720862116024907499L;
    public String url;
    public long datetime;
    public long qps;
    public long time;
    public long average;
    public long limit;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public double getQps() {
        return qps;
    }

    public void setQps(long qps) {
        this.qps = qps;
    }

    public double getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(long average) { this.average = average; }

    public double getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }
}
