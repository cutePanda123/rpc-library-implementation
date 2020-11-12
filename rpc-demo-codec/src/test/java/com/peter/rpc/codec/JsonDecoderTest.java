package com.peter.rpc.codec;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsonDecoderTest {

    @Test
    public void decode() {
        Encoder encoder = new JsonEncoder();
        TestClass obj = new TestClass("peter", 100);
        byte[] bytes = encoder.encode(obj);

        Deconder decoder = new JsonDecoder();
        TestClass decodedObj = decoder.decode(bytes, TestClass.class);
        assertEquals(decodedObj.getAge(), 100);
        assertEquals(decodedObj.getName(), "peter");
    }
}