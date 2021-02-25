package com.bob.canal.protobuf;

/**
 * Created by Bob on 2021-02-24
 * ProtoBuf序列化接口
 * 所有能够使用ProtoBuf序列化的bean都应该实现该接口
 */
public interface ProtoBufable {
    /**
     * 将对象转换为字节数组
     * @return 字节数组
     */
    byte[] toByte();
}
