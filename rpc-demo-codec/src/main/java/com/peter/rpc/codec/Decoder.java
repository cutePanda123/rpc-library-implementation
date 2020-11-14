package com.peter.rpc.codec;

public interface Decoder {
    public <T> T decode(byte[] bytes, Class<T> clazz);
}
