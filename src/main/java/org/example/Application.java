package org.example;

import lombok.SneakyThrows;

import java.util.Map;

public class Application {

    @SneakyThrows
    public static ApplicationContext run(String packageToScan, Map<Class, Class> ifc2Impl)  {
        JavaConfig config = new JavaConfig(packageToScan, ifc2Impl);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory objectFactory = new ObjectFactory(context);
        // tod0 инит все синглтоны которые не ленивые
        context.setFactory(objectFactory); //setFactory(objectFactory);
        return context;
    }
}
