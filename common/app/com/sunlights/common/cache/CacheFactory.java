package com.sunlights.common.cache;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;

/**
 * 支持注解式配置缓存
 * <p/>
 * Created by tangweiqun on 2014/12/6.
 */
public class CacheFactory {
    private static final MethodHandler methodHandler = new CacheMethodHandler();

    private static final MethodFilter methodFilter = new CacheMethodFilter();

    public static <T> T getProxyCacheObject(Class<T> clazz) {
        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(clazz);
        //设置过滤器，判断哪些方法调用需要被拦截
        factory.setFilter(methodFilter);
        Class<T> c = factory.createClass();
        try {
            T object = c.newInstance();
            ((Proxy) object).setHandler(methodHandler);
            return object;
        } catch (Exception e) {
            throw new RuntimeException("缓存强转类型错误", e);
        }
    }

}
