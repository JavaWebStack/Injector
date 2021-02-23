package org.javawebstack.injector;

public interface Injector {

    <T> T inject(T object);
    <T> T make(Class<T> clazz);
    <T> T getInstance(Class<T> clazz);
    <T> T getInstance(Class<T> clazz, String name);
    <T> void setInstance(Class<T> clazz, T instance);
    <T> void setInstance(Class<T> clazz, String name, T instance);
}
