package com.example.print.template.jmx.servlet;

import com.example.print.template.jmx.bean.Hello;
import com.example.print.template.jmx.manage.ManageMBeanInfo;
import org.springframework.stereotype.Component;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.management.ManagementFactory;

/*
 *@Author BieFeNg
 *@Date 2019/8/1 11:58
 *@DESC
 */

@Component
public class MBeanServlet extends HttpServlet {

    private MBeanServer server;

    @Override
    public void init(ServletConfig config) {
        try {
            server = ManagementFactory.getPlatformMBeanServer();
            ObjectName helloName = new ObjectName("jmxBean:name=hello");
            server.registerMBean(new Hello(), helloName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        ManageMBeanInfo manageMBeanInfo = new ManageMBeanInfo(server, "jmxBean:name=hello");
        System.out.println(manageMBeanInfo);
    }
}
