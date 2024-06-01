package org.example;

import lombok.SneakyThrows;
import net.sf.cglib.proxy.Enhancer;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DepricatedHandlerProxyConfigurator implements ProxyConfigurator {
    @Override
    @SneakyThrows
    public Object replaceProxyIfNeeded(Object t, Class implClass) {
        if (implClass.isAnnotationPresent(Transaction.class)) {

            if(implClass.getInterfaces().length==0){
                return Enhancer.create(implClass, new net.sf.cglib.proxy.InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("************** что же ты делаешь урод?!! ");
                        return method.invoke(t, args);
                    }
                });
            }

            return Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), new InvocationHandler() {

                @Override
                public Object invoke(Object o, Method method, Object[] args) throws Throwable {
                  System.out.println("************** что же ты делаешь урод?!! ");
                  return method.invoke(t, args);
                  }
                }
            );
        } else {
            return t;
        }
    }
}
