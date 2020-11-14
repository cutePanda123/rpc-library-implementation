package com.peter.rpc.client;

import com.peter.rpc.codec.Decoder;
import com.peter.rpc.codec.Encoder;
import com.peter.rpc.codec.JsonDecoder;
import com.peter.rpc.codec.JsonEncoder;
import com.peter.rpc.proto.Endpoint;
import com.peter.rpc.transport.HttpTransportClient;
import com.peter.rpc.transport.TransportClient;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class RpcClientConfig {
    private Class<? extends TransportClient> transportClientClass = HttpTransportClient.class;
    private Class<? extends Encoder> encoderClass = JsonEncoder.class;
    private Class<? extends Decoder> decoderClass = JsonDecoder.class;
    private Class<? extends TransportSelector> transportSelectorClass = RandomTransportSelector.class;
    private int connectionNum = 1;
    private List<Endpoint> serverEndpoints = Arrays.asList(new Endpoint[] {
            new Endpoint("127.0.0.1", 3000)
    });
}
