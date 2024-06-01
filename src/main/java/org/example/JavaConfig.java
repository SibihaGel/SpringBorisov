package org.example;

import lombok.Getter;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

public class JavaConfig implements Config {


    private Reflections scanner;
    private Map<Class, Class> interfToImplClass;

    public JavaConfig(String packageToScan, Map<Class, Class> interfToImplClass) {
        this.interfToImplClass = interfToImplClass;
        this.scanner = new Reflections(packageToScan);
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> interf) {
        return interfToImplClass.computeIfAbsent(interf, aClass -> {

            Set<Class<? extends T>> classes = scanner.getSubTypesOf(interf);
            if(classes.size() != 1) {
                throw new RuntimeException(interf + "Имеет более одной реализации или 0 ");
            }
            return classes.iterator().next();
        });
    }
    @Override
    public Reflections getScanner() {
        return scanner;
    }
}
