package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import redis.clients.jedis.Jedis;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerRedisStore {
    static final String TOPIC = "message";
    static final String GROUP = "consumer_group";

    public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        Jedis jedis = new Jedis("localhost");

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", GROUP);
        props.put("auto.offset.reset", "latest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Arrays.asList(TOPIC));

            while(true) {
                ConsumerRecords<String, String> records = consumer.poll(1000L);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("Received a record: " + record.key() + ":" + record.value());
//                    Thread.sleep(2000);
                    byte[] mdresult = messageDigest.digest((record.value()).getBytes());
                    System.out.println("MD5 hashed value: " + mdresult);

//                    get the hex value of MD5 hashed value
                    BigInteger bigInt = new BigInteger(1, mdresult);
                    String hash = bigInt.toString(16);
                    while (hash.length() < 32) {
                        hash = "0" + hash;
                    }
                    System.out.println("MD5 hashed value in hex: " + hash);
                    jedis.set(record.key(), hash);
                }

            }
        }
    }
}