package com.showjoy.qps.dao;

import com.showjoy.qps.model.QpsLimit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QpsLimitDao {

    int insert(@Param("limitqps") int limitqps,@Param("url") String url);

    List<QpsLimit> selectByUrl(@Param("url") String url);

    int updateQps(@Param("limitqps") int limitqps,@Param("url") String url);

    int deleteByUrl(@Param("url") String url);
}