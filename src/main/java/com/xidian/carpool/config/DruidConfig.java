package com.xidian.carpool.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;


@Configuration
public class DruidConfig {
    /**
     * 将application.properties中的数据源配置和我们的druid绑定起来，让其生效
     *
     * @return DataSource
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    /**
     * 配置druid的后台管理页面
     *
     * @return ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        HashMap<String, String> map = new HashMap<>();
        // 设置后台监控的登录信息，其key都是固定的
        map.put("loginUsername", "admin");
        map.put("loginPassword", "carpool123456");
        // 允许哪些请求访问，""表示都可以访问
        map.put("allow", "");

        // 设置禁止访问
        // map.put("locolNetwork", "192.168.1.1");

        bean.setInitParameters(map);
        return bean;
    }
}
