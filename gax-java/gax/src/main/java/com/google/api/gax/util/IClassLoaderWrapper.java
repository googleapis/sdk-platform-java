package com.google.api.gax.util;

/* Interface that allows for unit testing reflection logic. */
public interface IClassLoaderWrapper {
    /* Wraps @java.lang.Class#loadClass method  */
    Class<?> loadClass(String name) throws ClassNotFoundException;

    /*
     * Consolidates retrieving a field on a Class object via reflection and retrieving the value of that field
     * Expected field is of type int.
     */
    Object getFieldValue(Class<?> clazz, String fieldName)
            throws NoSuchFieldException, IllegalAccessException;
}
