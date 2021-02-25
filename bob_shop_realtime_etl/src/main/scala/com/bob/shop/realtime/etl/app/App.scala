package com.bob.shop.realtime.etl.app

import org.apache.flink.api.common.restartstrategy.RestartStrategies
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.CheckpointingMode
import org.apache.flink.streaming.api.environment.CheckpointConfig
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._

/**
 * Created by Bob on 2021-02-25
 */
object App {
  def main(args: Array[String]): Unit = {
    // 一、初始化Flink运行环境
    // 1. 获取StreamExecutionEnvironment运行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    // 2. 将Flink默认的开发环境并行度设置为1
    env.setParallelism(1)
    // 3. 配置Checkpoint
    env.enableCheckpointing(5000)
    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)
    // checkpoint的HDFS保存位置
    env.setStateBackend(new FsStateBackend("hdfs://node1:8020/flink/checkpoint/"))
    // 配置两次checkpoint的最小时间间隔
    env.getCheckpointConfig.setMinPauseBetweenCheckpoints(1000)
    // 配置最大checkpoint的并行度
    env.getCheckpointConfig.setMaxConcurrentCheckpoints(1)
    // 配置checkpoint的超时时长
    env.getCheckpointConfig.setCheckpointTimeout(60000)
    // 当程序关闭，触发额外的checkpoint
    env.getCheckpointConfig.enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION)
    env.setRestartStrategy(RestartStrategies.fixedDelayRestart(1, 1000))
    // 4. 编写测试代码，测试Flink程序是否能够正确执行
    env.fromCollection(
      List("hadoop", "hive", "spark")
    ).print()

    env.execute("runtime_etl")
  }
}