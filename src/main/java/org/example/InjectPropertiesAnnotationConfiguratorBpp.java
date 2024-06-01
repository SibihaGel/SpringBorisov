package org.example;

import lombok.SneakyThrows;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toMap;

public class InjectPropertiesAnnotationConfiguratorBpp implements ObjectConfiguratorBPP{
    private Map<String, String> propertiesMap;

    @SneakyThrows
    public InjectPropertiesAnnotationConfiguratorBpp()  {
        String path = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("application.properties")).getPath();
        Stream<String> lines = new BufferedReader(new FileReader(path)).lines();
        propertiesMap =  lines.map(line -> line.split("=")).collect(toMap(arr -> arr[0], arr -> arr[1]));
    }

    @Override
    @SneakyThrows
    public void configure(Object t, ApplicationContext context)  {
        Class<?> implClass = t.getClass();
        for (Field field: implClass.getDeclaredFields()) {
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);

            String value;
            if (annotation != null) {
                if (annotation.value().isEmpty()) {
                    value =  propertiesMap.get(field.getName());
                } else {
                    value = propertiesMap.get(annotation.value());
                }
                field.setAccessible(true);
                field.set(t, value);
            }

        }

    }
}
