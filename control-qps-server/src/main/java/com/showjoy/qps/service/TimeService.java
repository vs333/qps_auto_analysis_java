package com.showjoy.qps.service;

import java.util.Date;
import java.sql.Timestamp;

public class TimeService {
    public static Timestamp getNowTimstrap() {
        Timestamp ts = new Timestamp(new Date().getTime());
        return ts;
    }
}
