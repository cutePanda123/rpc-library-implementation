package com.peter.rpc.transport;

public interface TransportServer {
    public void init(int port, RequestHandler requestHandler);
    public void start();
    public void stop();
}
