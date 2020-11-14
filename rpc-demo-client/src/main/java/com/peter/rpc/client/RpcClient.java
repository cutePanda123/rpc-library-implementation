package com.peter.rpc.client;

import com.peter.rpc.codec.Decoder;
import com.peter.rpc.codec.Encoder;
import com.peter.rpc.common.utils.ReflectionUtils;
import com.peter.rpc.transport.TransportClient;

import java.lang.reflect.Proxy;

public class RpcClient {
    private RpcClientConfig config;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector transportSelector;

    public RpcClient() {
        this(new RpcClientConfig());
    }

    public RpcClient(RpcClientConfig config) {
        this.config = config;
        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecoderClass());
        this.transportSelector = ReflectionUtils.newInstance(config.getTransportSelectorClass());

        transportSelector.init(
                this.config.getServerEndpoints(),
                this.config.getConnectionNum(),
                this.config.getTransportClientClass()
        );
    }

    public <T> T getServiceProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] {clazz},
                new RpcInvoker(clazz, encoder, decoder, transportSelector)
        );
    }
}
