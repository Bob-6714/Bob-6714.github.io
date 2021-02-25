package com.bob.canal_client.kafka;

import com.bob.canal.bean.RowData;
import com.bob.canal_client.util.ConfigUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * Created by Bob on 2021-02-24
 * Kafka生产者
 */
public class KafkaSender {
    private Properties kafkaProps = new Properties();
    private KafkaProducer<String, RowData> kafkaProducer;

    public KafkaSender() {
        kafkaProps.put("bootstrap.servers", ConfigUtil.kafkaBootstrap_servers_config());
        kafkaProps.put("acks", ConfigUtil.kafkaAcks());
        kafkaProps.put("retries", ConfigUtil.kafkaRetries());
        kafkaProps.put("batch.size", ConfigUtil.kafkaBatch_size_config());
        kafkaProps.put("key.serializer", ConfigUtil.kafkaKey_serializer_class_config());
        kafkaProps.put("value.serializer", ConfigUtil.kafkaValue_serializer_class_config());

        kafkaProducer = new KafkaProducer<>(kafkaProps);
    }

    public void send(RowData rowData) {
        kafkaProducer.send(new ProducerRecord<>(ConfigUtil.kafkaTopic(), null, rowData));
    }
}