package com.peter.rpc.server;

import com.peter.rpc.common.utils.ReflectionUtils;
import com.peter.rpc.proto.RpcRequest;
import com.peter.rpc.proto.ServiceDescriptor;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ServiceManagerTest {
    ServiceManager manager;

    @Before
    public void init() {
        manager = new ServiceManager();
    }

    @Test
    public void register() {
        TestInterface testObj = new TestClass();
        manager.register(TestInterface.class, testObj);
    }

    @Test
    public void lookup() {
        TestInterface testObj = new TestClass();
        manager.register(TestInterface.class, testObj);
        Method method = ReflectionUtils.getPublicMethods(TestInterface.class)[0];
        ServiceDescriptor descriptor = ServiceDescriptor.getInstance(TestInterface.class, method);
        RpcRequest request = new RpcRequest();
        request.setService(descriptor);
        ServiceInstance instance = manager.lookup(request);

        assertNotNull(instance);
        assertEquals(instance.getServiceProviderMethod().getName(), "hello");
        assertEquals(instance.getServiceProviderMethod().getName(), "hello");
    }
}