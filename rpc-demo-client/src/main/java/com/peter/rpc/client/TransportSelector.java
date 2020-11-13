package com.peter.rpc.client;

import com.peter.rpc.proto.Endpoint;
import com.peter.rpc.transport.TransportClient;

import java.util.List;

public interface TransportSelector {
    public void init(
            List<Endpoint> serverEndpoints,
            int connectionNum,
            Class<? extends TransportClient> clazz);

    public TransportClient select();

    public void release(TransportClient client);

    public void close();
}
