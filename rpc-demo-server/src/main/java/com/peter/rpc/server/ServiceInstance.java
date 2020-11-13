package com.peter.rpc.server;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

@Data
@AllArgsConstructor
public class ServiceInstance {
    private Object serviceProviderInstance;
    private Method serviceProviderMethod;
}
