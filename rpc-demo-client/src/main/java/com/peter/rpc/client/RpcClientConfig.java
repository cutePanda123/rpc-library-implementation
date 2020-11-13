package com.peter.rpc.client;

import com.peter.rpc.codec.Deconder;
import com.peter.rpc.codec.Encoder;
import com.peter.rpc.codec.JsonDecoder;
import com.peter.rpc.codec.JsonEncoder;
import com.peter.rpc.proto.Endpoint;
import com.peter.rpc.transport.HttpTransportClient;
import com.peter.rpc.transport.TransportClient;

import java.util.Arrays;
import java.util.List;

public class RpcClientConfig {
    private Class<? extends TransportClient> transportClientClass = HttpTransportClient.class;
    private Class<? extends Encoder> encoder = JsonEncoder.class;
    private Class<? extends Deconder> decoder = JsonDecoder.class;
    private Class<? extends TransportSelector> transportSelectorClass = RandomTransportSelector.class;
    private int connectionNum = 1;
    private List<Endpoint> serverEndpoints = Arrays.asList(new Endpoint[] {
            new Endpoint("127.0.0.1", 3000)
    });
}
