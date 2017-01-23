package com.demosoft.life.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by andrii_korkoshko on 23.01.17.
 */
public class SpringContextTools {


    private static ApplicationContext context;

    public static void initContext() {
        //if(context == null) {
            context = new AnnotationConfigApplicationContext(AppConfiguration.class);
       // }
    }


    public static <T> T getBean(String beanName, Class<T> type) {
        return context.getBean(beanName, type);
    }

    public static <T> T getBean(Class<T> type) {
        return context.getBean(type);
    }
}
