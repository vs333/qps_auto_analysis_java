package com.showjoy.qps.web;

import com.alibaba.fastjson.JSON;
import com.showjoy.qps.dao.QpsLimitDao;
import com.showjoy.qps.model.QpsLimit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/qps/")
public class Controller {

    @Resource
    private QpsLimitDao qpsLimitDao;
    @RequestMapping("test")
    public String test() {
        List<QpsLimit> qpsLimits = qpsLimitDao.selectByUrl("12");
        return JSON.toJSONString("");
    }
}
