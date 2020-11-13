package com.peter.rpc.server;

import com.peter.rpc.common.utils.ReflectionUtils;
import com.peter.rpc.proto.RpcRequest;
import com.peter.rpc.proto.ServiceDescriptor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ServiceManager {
    private Map<ServiceDescriptor, ServiceInstance> serviceMap;

    public ServiceManager() {
        serviceMap = new ConcurrentHashMap<>();
    }

    public <T> void register(Class<T> serviceInterface, Object serviceInstance) {
        Method[] methods = ReflectionUtils.getPublicMethods(serviceInterface);
        for (Method method : methods) {
            ServiceInstance instance = new ServiceInstance(serviceInstance, method);
            ServiceDescriptor serviceDescriptor = ServiceDescriptor.getInstance(serviceInterface, method);

            serviceMap.put(serviceDescriptor, instance);
            log.info("register service: {} {}", serviceDescriptor.getClazz(), serviceDescriptor.getMethod());
        }
    }

    public ServiceInstance lookup(RpcRequest request) {
        return serviceMap.get(request.getService());
    }
}
