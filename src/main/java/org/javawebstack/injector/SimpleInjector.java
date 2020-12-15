package org.javawebstack.injector;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SimpleInjector implements Injector {

    private final Map<Class<?>, Map<String, Object>> instances = new HashMap<>();

    public <T> T inject(T object) {
        inject(object.getClass(), object);
        return object;
    }

    private void inject(Class<?> clazz, Object object){
        if(clazz.getSuperclass() != null && !Object.class.equals(clazz.getSuperclass()))
            inject(clazz.getSuperclass(), object);
        for(Field field : clazz.getDeclaredFields()){
            Inject[] injects = field.getDeclaredAnnotationsByType(Inject.class);
            if(injects.length != 0){
                if(instances.containsKey(field.getType())){
                    try {
                        field.setAccessible(true);
                        field.set(object, getInstance(field.getType(), injects[0].value()));
                    } catch (IllegalAccessException ignored) {
                        ignored.printStackTrace();
                    }
                }
            }
        }
    }

    public <T> T getInstance(Class<T> clazz){
        return getInstance(clazz, "");
    }

    public <T> T getInstance(Class<T> clazz, String name) {
        if(!instances.containsKey(clazz))
            return null;
        Map<String, Object> values = instances.get(clazz);
        if(!values.containsKey(name))
            return null;
        return (T) values.get(name);
    }

    public <T> void setInstance(Class<T> clazz, T instance){
        setInstance(clazz, "", instance);
    }

    public <T> void setInstance(Class<T> clazz, String name, T instance) {
        setInstanceUnsafe(clazz, name, instance);
    }

    public void setInstanceUnsafe(Class<?> clazz, Object instance){
        setInstanceUnsafe(clazz, "", instance);
    }

    public void setInstanceUnsafe(Class<?> clazz, String name, Object instance){
        Map<String, Object> values = instances.containsKey(clazz) ? instances.get(clazz) : new HashMap<>();
        values.put(name, instance);
        instances.put(clazz, values);
        inject(instance);
    }

}
