package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Consumer {
    static final String TOPIC = "message";
    static final String GROUP = "consumer_group";

    public static void main(String[] args) throws NoSuchAlgorithmException {
//        MessageDigest messageDigest = MessageDigest.getInstance("MD5");

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", GROUP);

        props.setProperty("enable.auto.commit", "false");

        
        // props.put("auto.offset.reset", "latest");
        // props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        try (KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(List.of(TOPIC));

            while(true) {
                ConsumerRecords<String, byte[]> records = consumer.poll(1000L);
//                System.out.println("Size: "+ records.count());
                for (ConsumerRecord<String, byte[]> record : records) {
                    System.out.println("Received a record: " + record.key() + ":" + record.value());
//                    byte[] mdresult = messageDigest.digest((record.value()).getBytes());
//                    System.out.println("MD5 hashed value: " + mdresult);

                    try{
                        consumer.commitSync();
                    }catch (CommitFailedException e){
                        System.out.println("Commit failed due to : "+ e);
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
