package org.javawebstack.injector;

public interface Injector {

    <T> T inject(T object);
    <T> T getInstance(Class<T> clazz);
    <T> void setInstance(Class<T> clazz, T instance);

}
