package com.showjoy.qps.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.showjoy.qps.dao.NginxHostDao;
import com.showjoy.qps.dao.QpsLimitDao;
import com.showjoy.qps.model.NginxHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.*;

@Service
public class MultiThreadService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiThreadService.class);
    @Value("${app.env}")
    String env;

    @Resource
    private NginxHostDao nginxHostDao;
    @Resource
    private QpsLimitDao qpsLimitDao;
    @Resource
    private DingDingService dingDingService;

    private ThreadFactory multiThreadAnalysis = new ThreadFactoryBuilder()
            .setNameFormat("Analysis-pool-%d").build();
    private ExecutorService pool = new ThreadPoolExecutor(4, 10, 20, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1024),
            multiThreadAnalysis,
            new ThreadPoolExecutor.AbortPolicy());

    @Scheduled(cron = "30 * * * * ?")
    public void StartAnalysisService() {
        LOGGER.info("开始分析qps");
        List<NginxHost> nginxHost = nginxHostDao.selectNginxIp(env);

        for (int n = 0; n < nginxHost.size(); n++) {
            String privateIp = nginxHost.get(n).getPrivateIp();
            try {
                AnalysisThread analysisThread = new AnalysisThread(qpsLimitDao, privateIp, env, dingDingService);

                LOGGER.info("开始分析nginx:{}, env:{}", privateIp, env);
                pool.execute(analysisThread);
            } catch (Exception e) {
                LOGGER.error("nginx error, nginx:{}, env:{}", privateIp, env, e);
            }

        }
    }
}
