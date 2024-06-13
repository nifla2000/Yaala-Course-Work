package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

public class Producer {
    Properties prop = new Properties();
    Scanner scanner = new Scanner(System.in);

    private void init() throws InterruptedException{
        prop.setProperty("bootstrap.servers", "localhost:9092");
        prop.setProperty("kafka.topic.name", "message");
        KafkaProducer<String, byte[]> producer = new KafkaProducer<String, byte[]>(this.prop, new StringSerializer(), new ByteArraySerializer());
        while(true) {
            String inputMessage = scanner.nextLine();
            String key = "abc";
            byte[] payload = ( inputMessage +" :"+ new Date()).getBytes();
//            System.out.println(i+ "message from java code "+new Date());
            ProducerRecord<String, byte[]> record = new ProducerRecord<String, byte[]>(prop.getProperty("kafka.topic.name"), key, payload);
            producer.send(record);
            Thread.sleep(1000);
        }
//        producer.close();
    }
    public static void main(String[] args) throws InterruptedException{
        Producer producer = new Producer();
        producer.init();
    }
}
