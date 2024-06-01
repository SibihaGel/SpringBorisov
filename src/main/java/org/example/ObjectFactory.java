package org.example;

import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class ObjectFactory {

    private final ApplicationContext context;
    private List<ObjectConfiguratorBPP> configurators = new ArrayList<>();
    private List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();


    @SneakyThrows
    public ObjectFactory(ApplicationContext context)  {
        this.context = context;
        for(Class<? extends ObjectConfiguratorBPP> aClass : context.getConfig().getScanner().getSubTypesOf(ObjectConfiguratorBPP.class)) {
           configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
        for(Class<? extends ProxyConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ProxyConfigurator.class)) {
            proxyConfigurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }
    @SneakyThrows
    public <T> T createObject(Class<T> implClass) {
        T t = create(implClass);
        configure(t);
        invokeInit(implClass, t);

        t = wrapProxyIfNeeded(implClass, t);

        return t;
    }


    private <T> T wrapProxyIfNeeded(Class<T> implClass, T t) {
        for (ProxyConfigurator proxyConfigurator : proxyConfigurators) {
            t = (T) proxyConfigurator.replaceProxyIfNeeded(t, implClass);
        }
        return t;
    }

    @SneakyThrows
    private static <T> void invokeInit(Class<T> implClass, T t)  {
        for (Method method : implClass.getMethods()) {
            if(method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(t);
            }
        }
    }

    @SneakyThrows
    private <T> T create(Class<T> implClass) {
        return implClass.getDeclaredConstructor().newInstance();
    } 

    private <T> void configure(T t) {
        for (ObjectConfiguratorBPP objectConfiguratorBPP : configurators) {
           objectConfiguratorBPP.configure(t, context);
    }
    }
}
