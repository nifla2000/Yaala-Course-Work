package org.example;

import redis.clients.jedis.Jedis;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");

        System.out.println("Server started: "+ jedis.ping());
        jedis.close();

    }
}