package org.example;

import lombok.SneakyThrows;
import java.lang.reflect.Field;

public class InjectByTypeObjectConfigurator implements ObjectConfiguratorBPP{

    @SneakyThrows
    public void configure(Object t, ApplicationContext context) {
        for (Field field : t.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectByTipe.class)) {
                field.setAccessible(true);
                Object object = context.getObject(field.getType());
                field.set(t, object);
            }
        }
    }
}
