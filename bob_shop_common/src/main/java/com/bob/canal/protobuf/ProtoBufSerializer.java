package com.bob.canal.protobuf;

import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Created by Bob on 2021-02-24
 * 用于Kafka消息序列化
 */
public class ProtoBufSerializer implements Serializer<ProtoBufable> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public byte[] serialize(String topic, ProtoBufable data) {
        return data.toByte();
    }

    @Override
    public void close() {}
}