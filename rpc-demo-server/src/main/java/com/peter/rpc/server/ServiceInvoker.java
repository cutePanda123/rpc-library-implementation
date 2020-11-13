package com.peter.rpc.server;

import com.peter.rpc.common.utils.ReflectionUtils;
import com.peter.rpc.proto.RpcRequest;

public class ServiceInvoker {
    public Object invoke(ServiceInstance serviceInstance, RpcRequest request) {
        return ReflectionUtils.invoke(
                serviceInstance.getServiceProviderInstance(),
                serviceInstance.getServiceProviderMethod(),
                request.getParameters()
        );
    }
}
