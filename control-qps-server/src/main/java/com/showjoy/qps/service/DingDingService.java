package com.showjoy.qps.service;

import com.alibaba.fastjson.JSON;
import com.showjoy.qps.model.DingMsg;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.http.entity.StringEntity;

@Service
public class DingDingService {
    @Value("${app.dingurl}")
    String dingurl;
    @Value("${app.env}")
    String env;
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalysisThread.class);

    public void AlterToRobot(long average, String msg) {
        if (this.env.equals("com") && average >= 5000) {
            try {
                DingMsg dingmsg = new DingMsg();
                dingmsg.setMsgtype("text");
                dingmsg.setText(new DingMsg.Text(msg));
                dingmsg.setAt(new DingMsg.At(false));
                String textMsg = JSON.toJSONString(dingmsg);
                HttpClient httpclient = HttpClients.createDefault();
                HttpPost httppost = new HttpPost("https://oapi.dingtalk.com/robot/send?access_token=" + dingurl);
                httppost.addHeader("Content-Type", "application/json; charset=utf-8");
                StringEntity se = new StringEntity(textMsg, "utf-8");
                se.setContentEncoding("UTF-8");
                httppost.setEntity(se);
                HttpResponse response = httpclient.execute(httppost);
                LOGGER.info("发送钉钉告警:" + response);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("发送钉钉告警失败");
            }
        }
    }
}
