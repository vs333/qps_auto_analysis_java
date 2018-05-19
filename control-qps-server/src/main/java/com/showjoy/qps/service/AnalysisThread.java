package com.showjoy.qps.service;


import com.showjoy.qps.dao.QpsLimitDao;
import com.showjoy.qps.model.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.IndexOutOfBoundsException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class AnalysisThread implements Runnable {
    private QpsLimitDao qpsLimitDao;
    private String stringAddress;
    private String host;
    private DingDingService dingDingService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalysisThread.class);

    public AnalysisThread(QpsLimitDao qpsLimitDao, String stringAddress, String env, DingDingService dingDingService) {
        this.qpsLimitDao = qpsLimitDao;
        this.stringAddress = stringAddress;
        this.dingDingService = dingDingService;
        if (env.equals("net")) {
            this.host = "limitqps.showjoy.net";
        } else if (env.equals("com")) {
            this.host = "limitqps.showjoy.com";
        }
    }

    public void qpsLimit() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        Long nowdate = Long.parseLong(dateFormat.format(date)) - 1;
        LOGGER.info("分析qps分钟数为：{}", nowdate);
        CollectService collectService = new CollectService();
        HttpService httpService = new HttpService(this.stringAddress);
        List<Url> urls = collectService.regularResponse(httpService.httpget(this.host, "action", "show"));
        List<Url> limitUrls = collectService.regularResponse(httpService.httpget(this.host, "action", "limit"));

        for (int n = 0; n < urls.size(); n++) {
            if (urls.get(n).datetime != nowdate || urls.get(n).average < 2000) {
                continue;
            }
            int limitqps = 10000;
            long average = urls.get(n).average;
            //给返回时间做一个限制，分别为0-4，4-6，6-8，8-*
            if (average >= 9000) {
                limitqps = 1000;
            } else if (average >= 7000) {
                limitqps = 1500;
            } else if (average >= 5000) {
                limitqps = 2500;
            } else if (average >= 2000) {
                limitqps = 4000;
            }
            int result = qpsLimitDao.updateQps(limitqps, urls.get(n).url);
            if (result == 0) {
                qpsLimitDao.insert(limitqps, urls.get(n).url);
            }

            int x = 0;
            do {
                x++;
                //先去查已经配置的限流，如果已经有了，就跳过这个限流配置
                String message = "触发限流nginx内网地址:" + this.stringAddress + ";\n链接:" + urls.get(n).url + ";\n平均响应时间毫秒:" + urls.get(n).average +
                        ";\n触发限制qps数60秒:" + limitqps + ";\n请求数:" + urls.get(n).qps + ";\n总耗时毫秒:" + urls.get(n).time +
                        ";\n日期时间:" + urls.get(n).datetime;
                try {
                    if (!urls.get(n).url.equals(limitUrls.get(x).url)) {
                        httpService.httppost(this.host, "action", urls.get(n).url, limitqps, 59);
                        dingDingService.AlterToRobot(average,message);
                        LOGGER.info(message);
                    } else {
                        LOGGER.info("与已有限流冲突，放弃自动限流配置写入！" + limitUrls.get(x).url);
                        LOGGER.info(message);
                    }
                } catch (IndexOutOfBoundsException e) {
                    httpService.httppost(this.host, "action", urls.get(n).url, limitqps, 59);
                    dingDingService.AlterToRobot(average,message);
                    LOGGER.info(message);
                }
            } while (x < limitUrls.size());
        }
    }

    @Override
    public void run() {
        this.qpsLimit();
    }
}
