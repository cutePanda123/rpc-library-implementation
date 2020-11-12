package com.peter.rpc.transport;

import com.peter.rpc.proto.Endpoint;
import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.http.HttpMethod;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpTransportClient implements TransportClient {
    private String url;
    @Override
    public void connect(Endpoint endpoint) {
        url = "http://" + endpoint.getHostname() + ":" + endpoint.getPort();
    }

    @Override
    public InputStream write(InputStream data) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(url).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod(HttpMethod.POST.toString());

            httpURLConnection.connect();
            IOUtils.copy(data, httpURLConnection.getOutputStream());

            int resultCode = httpURLConnection.getResponseCode();
            if (resultCode == HttpURLConnection.HTTP_OK) {
                return httpURLConnection.getInputStream();
            } else {
                return  httpURLConnection.getErrorStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {

    }
}
