package com.showjoy.qps.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = { "classpath*:spring/disconf.xml" })
public class AppContext {
}
