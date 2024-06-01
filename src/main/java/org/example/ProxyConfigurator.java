package org.example;

public interface ProxyConfigurator {
    Object replaceProxyIfNeeded(Object t, Class implClass);
}
