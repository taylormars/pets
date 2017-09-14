package com.mindflow.motan.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.concurrent.locks.LockSupport;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        LockSupport.park();
    }
}
