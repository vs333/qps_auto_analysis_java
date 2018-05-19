package com.showjoy.qps.service;

import com.alibaba.fastjson.JSON;
import com.showjoy.qps.model.Url;

import java.util.List;


public class CollectService {

    public List<Url> regularResponse(String result) {
        List<Url> urls = JSON.parseArray(result, Url.class);
        if (urls == null) {
            return null;
        }
        for (int i = 0; i < urls.size(); i++) {
            Url tmpurl = urls.get(i);
            String hostname = tmpurl.url.split(":")[0];
            long datetime = Long.parseLong(tmpurl.url.split(":")[tmpurl.url.split(":").length-1]);
            tmpurl.url = hostname;
            tmpurl.datetime = datetime;
            tmpurl.average = tmpurl.time / tmpurl.qps;
            urls.set(i, tmpurl);
        }
        return urls;
    }
}
