package com.java.banksystemproject.annotation;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.util.Set;

public class DeprecatedMethodReflection {
    public static Set<Method> findDeprecateMethods() {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder().setUrls(
                        ClasspathHelper.forPackage( "com.java.banksystemproject" ) ).setScanners(
                        new MethodAnnotationsScanner() ) );
        return reflections.getMethodsAnnotatedWith(DeprecatedMethod.class);
    }

}
