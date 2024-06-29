package com.xidian.carpool;

import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
@MapperScan("com.xidian.carpool.mapper")
public class CarpoolApplication {
    private final static Logger logger = Logger.getLogger(CarpoolApplication.class);

    /**
     * 上传服务配置
     *
     * @return CommonsMultipartResolver
     */
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setResolveLazily(true);
        resolver.setMaxInMemorySize(-1);
        resolver.setMaxUploadSize(5 * 1024 * 1024);
        return resolver;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CarpoolApplication.class, args);
        logger.info("Server listening on http://127.0.0.1:" + context.getEnvironment().getProperty("server.port") + ((context.getEnvironment().getProperty("server.servlet.context-path") == null) ? "" : context.getEnvironment().getProperty("server.servlet.context-path")));
    }

}
