package com.whoknows;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by nobody on 17/9/8.
 * 自定义配置tomcat,方法二（方法一见MyTomcatEmbeddedServletContainerFactory）
 * 默认读配置文件，然后再这个类
 */
@Configuration
public class MyServerConfiguration {

    @Bean
    public EmbeddedServletContainerFactory createEmbeddedServletContainerFactory()
    {
        TomcatEmbeddedServletContainerFactory tomcatFactory = new TomcatEmbeddedServletContainerFactory();
        //tomcatFactory.setPort(8081);
        tomcatFactory.addConnectorCustomizers(new MyTomcatConnectorCustomizer());
        return tomcatFactory;
    }
}
class MyTomcatConnectorCustomizer implements TomcatConnectorCustomizer
{
    public void customize(Connector connector)
    {
        //connector.setProperty("address","localhost");//设置只能localhost访问，外网无法通过ip访问（即使知道真实ip）
        //docker下实现不了想要的功能。先注释掉
        //Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        //设置最大连接数
//        protocol.setMaxConnections(2000);
//        //设置最大线程数
//        protocol.setMaxThreads(2000);
//        protocol.setConnectionTimeout(30000);
    }
}