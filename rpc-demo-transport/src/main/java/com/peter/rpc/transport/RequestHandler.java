package com.peter.rpc.transport;

import java.io.InputStream;
import java.io.OutputStream;

public interface RequestHandler {
    public void onRequest(InputStream inbound, OutputStream toResp);
}
