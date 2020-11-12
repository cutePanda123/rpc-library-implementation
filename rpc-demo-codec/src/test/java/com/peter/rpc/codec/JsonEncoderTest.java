package com.peter.rpc.codec;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsonEncoderTest {

    @Test
    public void encode() {
        Encoder encoder = new JsonEncoder();
        TestClass obj = new TestClass("peter", 100);
        byte[] bytes = encoder.encode(obj);

        assertNotNull(bytes);
    }
}