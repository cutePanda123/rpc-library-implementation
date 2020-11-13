package com.peter.rpc.client;

import com.peter.rpc.common.utils.ReflectionUtils;
import com.peter.rpc.proto.Endpoint;
import com.peter.rpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Slf4j
public class RandomTransportSelector implements TransportSelector {
    private List<TransportClient> connectedClients;

    public RandomTransportSelector() {
        connectedClients = new ArrayList<>();
    }

    @Override
    public synchronized void init(List<Endpoint> serverEndpoints, int connectionNum, Class<? extends TransportClient> clazz) {
        connectionNum = Math.max(1, connectionNum);
        for (Endpoint endpoint : serverEndpoints) {
            for (int i = 0; i < connectionNum; ++i) {
                TransportClient client = ReflectionUtils.newInstance(clazz);
                client.connect(endpoint);
                connectedClients.add(client);
            }
            log.info("connect to server endpoint: {}", endpoint);
        }
    }

    @Override
    public synchronized TransportClient select() {
        Random r = new Random();
        int idx = r.nextInt(connectedClients.size());
        return connectedClients.remove(idx);
    }

    @Override
    public synchronized void release(TransportClient client) {
        connectedClients.add(client);
    }

    @Override
    public synchronized void close() {
        for (TransportClient client : connectedClients) {
            client.close();
        }
        connectedClients.clear();
    }
}
