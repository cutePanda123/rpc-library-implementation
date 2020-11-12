package com.peter.rpc.common.utils;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ReflectionUtilsTest {

    @Test
    public void newInstance() {
        TestClass1 testObj = ReflectionUtils.newInstance(TestClass1.class);
        assertNotNull(testObj);
    }

    @Test
    public void getPublicMethods() {
        Method[] methods = ReflectionUtils.getPublicMethods(TestClass1.class);
        assertEquals(1, methods.length);
        assertEquals(methods[0].getName(), "b");
    }

    @Test
    public void invoke() {
        try {
            String str = (String) ReflectionUtils.invoke(new TestClass1(), TestClass1.class.getMethod("b"));
            assertEquals(str, "b");
        } catch (Exception e) {
            assertEquals(1, 2);
        }
    }
}