package com.xin.adsystem.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 在使用SpringBoot构建项目时，我们通常有一些预先数据的加载。
 * 那么SpringBoot提供了一个简单的方式来实现–CommandLineRunner。
 * @Order(value = 2) 表示执行顺序，数字越小越优先执行
 */
@Component
@Order(value = 1)
public class MyStartupRunner2 implements CommandLineRunner {
    @Override
    public void run(String... strings) throws Exception {
        System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作 MyStartupRunner2 order 1 <<<<<<<<<<<<<");
    }
}