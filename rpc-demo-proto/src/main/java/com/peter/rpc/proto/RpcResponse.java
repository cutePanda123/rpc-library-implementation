package com.peter.rpc.proto;

import lombok.Data;

@Data
public class RpcResponse {
    private ResponseStatus code = ResponseStatus.SUCCEED;
    private String message = "OK";
    private Object payload;
}
