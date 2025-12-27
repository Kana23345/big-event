package com.myproject;

import org.junit.jupiter.api.Test;

/**
 * ThreadLocal测试类
 * 用于演示ThreadLocal在多线程环境下的使用特性
 * 每个线程都有自己独立的ThreadLocal变量副本
 */
public class ThreadLocalTest {

    /**
     * 测试ThreadLocal的设置和获取功能
     * 演示在不同线程中ThreadLocal变量的独立性
     * 每个线程对ThreadLocal的设置和获取操作互不影响
     */
    @Test
    public void testThreadLocalSetAndGet(){
        // 创建ThreadLocal实例，用于在不同线程间存储独立的数据
        ThreadLocal tl=new ThreadLocal<>();

        // 创建第一个线程"blue"，设置并获取ThreadLocal中的值
        new Thread(()->{
            tl.set("person11");
            System.out.println(Thread.currentThread().getName()+" : "+tl.get());
            System.out.println(Thread.currentThread().getName()+" : "+tl.get());
            System.out.println(Thread.currentThread().getName()+" : "+tl.get());
            System.out.println(Thread.currentThread().getName()+" : "+tl.get());
        },"blue").start();

        // 创建第二个线程"green"，设置并获取ThreadLocal中的值
        // 该线程中的ThreadLocal与"blue"线程中的ThreadLocal是独立的
        new Thread(()->{
            tl.set("person22");
            System.out.println(Thread.currentThread().getName()+" : "+tl.get());
            System.out.println(Thread.currentThread().getName()+" : "+tl.get());
            System.out.println(Thread.currentThread().getName()+" : "+tl.get());
            System.out.println(Thread.currentThread().getName()+" : "+tl.get());
        },"green").start();

    }
}