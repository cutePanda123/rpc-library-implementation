package com.peter.rpc.transport;
import com.peter.rpc.proto.Endpoint;
import java.io.InputStream;

public interface TransportClient {
    public void connect(Endpoint endpoint);
    public InputStream write(InputStream data);
    public void close();
}
