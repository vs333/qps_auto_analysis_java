package com.showjoy.qps.model;


public class NginxHost {
    private Integer id;
    private String NginxName;
    private String PrivateIp;
    private String Env;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNginxName() {
        return NginxName;
    }

    public void setNginxName(String nginxName) {
        NginxName = nginxName;
    }

    public String getPrivateIp() {
        return PrivateIp;
    }

    public void setPrivateIp(String privateIp) {
        PrivateIp = privateIp;
    }

    public String getEnv() {
        return Env;
    }

    public void setEnv(String env) {
        Env = env;
    }
}
