package com.peter.rpc.server;

import com.peter.rpc.codec.Decoder;
import com.peter.rpc.codec.Encoder;
import com.peter.rpc.codec.JsonDecoder;
import com.peter.rpc.codec.JsonEncoder;
import com.peter.rpc.transport.HttpTransportServer;
import com.peter.rpc.transport.TransportServer;
import lombok.Data;

@Data
public class RpcServerConfig {
    private Class<? extends TransportServer> transportClass = HttpTransportServer.class;
    private Class<? extends Encoder> encoderClass = JsonEncoder.class;
    private Class<? extends Decoder> decoderClass = JsonDecoder.class;
    private int port = 3000;
}
