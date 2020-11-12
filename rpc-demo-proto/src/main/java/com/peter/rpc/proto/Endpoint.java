package com.peter.rpc.proto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Endpoint {
    private String hostname;
    private int port;
}
