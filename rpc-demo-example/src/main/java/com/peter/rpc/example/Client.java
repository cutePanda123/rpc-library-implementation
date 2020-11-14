package com.peter.rpc.example;

import com.peter.rpc.client.RpcClient;

public class Client {
    public static void main(String[] args) {
        RpcClient client = new RpcClient();
        CalcService service = client.getServiceProxy(CalcService.class);

        int num1 = service.add(1, 2);
        int num2 = service.minus(1, 2);

        System.out.println("Result from CalcService add method: " + num1);
        System.out.println("Result from CalcService minus method: " + num2);
    }
}
