package com.peter.rpc.proto;

import lombok.Data;

@Data
public class RpcRequest {
    private ServiceDescriptor service;
    private Object[] parameters;
}
