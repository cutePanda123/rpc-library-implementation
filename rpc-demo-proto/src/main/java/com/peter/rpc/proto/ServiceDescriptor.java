package com.peter.rpc.proto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDescriptor {
    private String clazz;
    private String method;
    private String returnType;
    private String[] parameterTypes;

    public static ServiceDescriptor getInstance(Class clazz, Method method) {
        ServiceDescriptor instance = new ServiceDescriptor();
        instance.setClazz(clazz.getName());
        instance.setMethod(method.getName());
        instance.setReturnType(method.getReturnType().getName());
        Class[] parameterClasses = method.getParameterTypes();
        String[] parameterTypes = new String[parameterClasses.length];
        for (int i = 0; i < parameterTypes.length; ++i) {
            parameterTypes[i] = parameterClasses[i].getName();
        }
        instance.setParameterTypes(parameterTypes);

        return instance;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || o.getClass() != getClass()) {
            return false;
        }
        ServiceDescriptor that = (ServiceDescriptor)o;
        return that.toString().equals(this.toString());
    }

    @Override
    public String toString() {
        return "clazz=" + clazz +
                ",method=" + method +
                ",returnType=" + returnType +
                ",parameterTyptes=" + Arrays.toString(parameterTypes);
    }
}
