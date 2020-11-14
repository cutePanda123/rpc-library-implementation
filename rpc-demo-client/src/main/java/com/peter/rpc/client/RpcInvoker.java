package com.peter.rpc.client;

import com.peter.rpc.codec.Decoder;
import com.peter.rpc.codec.Encoder;
import com.peter.rpc.proto.ResponseStatus;
import com.peter.rpc.proto.RpcRequest;
import com.peter.rpc.proto.RpcResponse;
import com.peter.rpc.proto.ServiceDescriptor;
import com.peter.rpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class RpcInvoker implements InvocationHandler {
    private Class serviceClass;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector transportSelector;

    public RpcInvoker(Class serviceClass,
                      Encoder encoder,
                      Decoder decoder,
                      TransportSelector transportSelector) {
        this.serviceClass = serviceClass;
        this.decoder = decoder;
        this.encoder = encoder;
        this.transportSelector = transportSelector;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setService(
                ServiceDescriptor.getInstance(serviceClass, method)
        );
        request.setParameters(args);

        RpcResponse response = invokeService(request);
        if (response == null || response.getCode() == ResponseStatus.FAILED) {
            throw  new IllegalStateException("fail to invoke remote: " + response);
        }
        return response.getPayload();
    }

    private RpcResponse invokeService(RpcRequest request) {
        RpcResponse response = null;
        TransportClient transportClient = null;
        try {
            transportClient = transportSelector.select();
            byte[] outBytes = encoder.encode(request);
            InputStream receives = transportClient.write(new ByteArrayInputStream(outBytes));
            byte[] inBytes = IOUtils.readFully(receives, receives.available());
            response = decoder.decode(inBytes, RpcResponse.class);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            response = new RpcResponse();
            response.setCode(ResponseStatus.FAILED);
            response.setMessage("RpcClient error: " + e.getClass() + " : " + e.getMessage());
        } finally {
            if (null != transportClient) {
                transportSelector.release(transportClient);
            }
        }
        return  response;
    }
}
