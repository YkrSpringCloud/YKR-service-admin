package com.YKR.service.admin.context;


import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Author:ykr
 * Date:2020/1/16
 * Description:
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware, DisposableBean {
    private static final Logger logger= LoggerFactory.getLogger(ApplicationContextHolder.class);
    private static ApplicationContext applicationContext;
    /*获取存储在静态变量中的ApplicationContext*/
    public static ApplicationContext getApplicationContext(){
        assertContextInjected();
        return applicationContext;
    }
    /*从静态变量applicationContext中获取Bean,自动转型成所赋值对象的类型*/
    public static <T> T getBean(String name){
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }
    /*从静态变量application中获取Bean,自动转型为所赋值对象的类型*/
    public static <T> T getBean(Class<T> clazz){
        assertContextInjected();
        return applicationContext.getBean(clazz);
    }
    /*实现DisposeableBean接口，在Context关闭时清理静态变量*/
    @Override
    public void destroy() throws Exception {
        logger.debug("清除SpringContext中的ApplicationContext: {}",applicationContext);
        applicationContext=null;
    }

    @Override  /*实现ApplicationAware接口的context注入函数，将其存入静态变量*/
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.applicationContext=applicationContext;

    }
    /*断言Context已经注入*/
    private static void assertContextInjected(){
       Validate.validState(applicationContext != null, "applicaitonContext属性未" +
                "注入, 请在spring-context.xml中定义ApplicationContextHolder.");
    }
}
