package com.google.api.gax.util;

/* Wrapper class for Final Class methods that can not be mocked */
public class ClassLoaderWrapper implements IClassLoaderWrapper {
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return Class.forName(name);
    }

    @Override
    public Object getFieldValue(Class<?> clazz, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        return clazz.getField(fieldName).get(null);
    }
}
