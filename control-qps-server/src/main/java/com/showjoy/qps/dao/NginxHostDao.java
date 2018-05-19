package com.showjoy.qps.dao;

import com.showjoy.qps.model.NginxHost;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NginxHostDao {
    List<NginxHost> selectNginxIp(@Param("Env") String Env);
}
