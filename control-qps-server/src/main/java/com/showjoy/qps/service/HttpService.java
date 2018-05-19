package com.showjoy.qps.service;

import com.google.common.collect.Lists;
import com.showjoy.treasure.http.HttpClientUtil;
import com.showjoy.treasure.http.NameValuePairImp;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;

import java.util.List;

public class HttpService {

    private String address;

    public HttpService(String address) {
        this.address = address;
    }

    public String httpget(String host, String uri, String value) {
        StringBuilder sb = new StringBuilder();
        BasicHeader header = new BasicHeader("Host", host);
        sb.append("http://").append(address).append("/").append(uri);
        List<NameValuePair> nameValuePairs = Lists.newArrayList();
        nameValuePairs.add(new NameValuePairImp("action", value));
        //final String result = HttpClientUtil.get(sb.toString(), nameValuePairs,header);
        final String result = HttpClientUtil.httpRequest(sb.toString(), nameValuePairs, "get", null, "utf-8", header);
        return result;
    }

    public String httppost(String host, String uri, String urlkey, int qpslimit, int expiretime) {
        StringBuilder sb = new StringBuilder();
        BasicHeader header = new BasicHeader("Host", host);
        sb.append("http://").append(address).append("/").append(uri);
        List<NameValuePair> nameValuePairs = Lists.newArrayList();
        nameValuePairs.add(new NameValuePairImp("urlkey", urlkey));
        nameValuePairs.add(new NameValuePairImp("limitvalue", String.valueOf(qpslimit)));
        nameValuePairs.add(new NameValuePairImp("expiretime", String.valueOf(expiretime)));
        final String result = HttpClientUtil.httpRequest(sb.toString(), nameValuePairs, "post", null, "utf-8", header);
        return result;
    }
}
