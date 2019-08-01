package com.example.print;

import com.example.print.template.jmx.servlet.MBeanServlet;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.example.print")
public class PrintApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrintApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean<MBeanServlet> servletRegistration(MBeanServlet servlet) {
        ServletRegistrationBean<MBeanServlet> bean = new ServletRegistrationBean<>();
        bean.setServlet(servlet);
        bean.setName("manageServlet");
        bean.addUrlMappings("/mbeanInfo");
        return bean;
    }

}
