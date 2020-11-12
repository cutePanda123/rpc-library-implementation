package com.peter.rpc.codec;

public interface Deconder {
    public <T> T decode(byte[] bytes, Class<T> clazz);
}
