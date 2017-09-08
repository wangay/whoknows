package com.whoknows;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.context.embedded.EmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by nobody on 17/9/8.
 * 自定义内置的tomcat，方法一， 类的优先级大于配置文件（配置文件的相关配置是否还有效？没试过）
 * 使用方法二（注释掉Component），保证配置优先
 */
//@Component()
public class MyTomcatEmbeddedServletContainerFactory extends TomcatEmbeddedServletContainerFactory {

    @Override
    public EmbeddedServletContainer getEmbeddedServletContainer(
            ServletContextInitializer... initializers) {
        //设置端口
        //this.setPort(8080);
        return super.getEmbeddedServletContainer(initializers);
    }

    @Override
    protected void customizeConnector(Connector connector)
    {
        connector.setProperty("address","localhost");//设置只能localhost访问，外网无法通过ip访问（即使知道真实ip）
        super.customizeConnector(connector);
//        System.out.println("开始配置tomcat参数");
//        Http11NioProtocol protocol = (Http11NioProtocol)connector.getProtocolHandler();
//        //设置最大连接数
//        protocol.setMaxConnections(2000);
//        // Tomcat初始化时创建的线程数
//        protocol.setMinSpareThreads(400);
//        //设置最大线程数
//        protocol.setMaxThreads(2000);
//        protocol.setConnectionTimeout(20000);
//        System.out.println("配置结束");
    }
}
