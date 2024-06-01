package org.example;


import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

    @Setter
    private ObjectFactory factory;
    private Map<Class, Object> cache = new ConcurrentHashMap<>();
    @Getter
    private Config config;

    public ApplicationContext(Config config) {
        this.config = config;
    }

    @SneakyThrows
    public <T> T getObject(Class<T> type)  {
        if(cache.containsKey(type)) {
            return (T) cache.get(type);
        }
        Class<? extends T> implClass = type;

        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }
        T t = factory.createObject(implClass);

        if(implClass.isAnnotationPresent(Singletone.class)) {
            cache.put(type, t);
        }
        return t;
    }
}
