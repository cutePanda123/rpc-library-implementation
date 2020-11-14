package com.peter.rpc.server;

import com.peter.rpc.codec.Decoder;
import com.peter.rpc.codec.Encoder;
import com.peter.rpc.common.utils.ReflectionUtils;
import com.peter.rpc.proto.ResponseStatus;
import com.peter.rpc.proto.RpcRequest;
import com.peter.rpc.proto.RpcResponse;
import com.peter.rpc.transport.RequestHandler;
import com.peter.rpc.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import sun.misc.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class RpcServer {
    private RpcServerConfig config;
    private TransportServer transportServer;
    private Encoder encoder;
    private Decoder decoder;
    private ServiceManager serviceManager;
    private ServiceInvoker serviceInvoker;
    private RequestHandler handler = new RequestHandler() {
        @Override
        public void onRequest(InputStream inbound, OutputStream toResp) {
            RpcResponse response = new RpcResponse();
            try {
                byte[] payload = IOUtils.readFully(inbound, inbound.available(), true);
                RpcRequest request = decoder.decode(payload, RpcRequest.class);
                log.info("get request: {}", request);

                ServiceInstance serviceInstance = serviceManager.lookup(request);
                Object ret = serviceInvoker.invoke(serviceInstance, request);
                response.setPayload(ret);
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
                response.setCode(ResponseStatus.FAILED);
                response.setMessage("RpcServer error: " +
                        e.getClass().getName() + " : " + e.getMessage());
            } finally {
                try {
                    byte[] outBytes = encoder.encode(response);
                    toResp.write(outBytes);
                    log.info("send client an response");
                } catch (IOException e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
    };

    public <T> void register(Class<T> serviceInterface, T serviceInstance) {
        serviceManager.register(serviceInterface, serviceInstance);
    }

    public void start() {
        transportServer.start();
    }

    public void stop() {
        transportServer.stop();
    }

    public RpcServer(RpcServerConfig config) {
        this.config = config;
        transportServer = ReflectionUtils.newInstance(config.getTransportClass());
        transportServer.init(config.getPort(), this.handler);
        encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        decoder = ReflectionUtils.newInstance(config.getDecoderClass());

        serviceInvoker = new ServiceInvoker();
        serviceManager = new ServiceManager();
    }

    public RpcServer() {
        this(new RpcServerConfig());
    }
}
