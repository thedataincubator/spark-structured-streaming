package com.thedataincubator.MeetupStreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{StreamingContext, Seconds}

object Main {

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("SparkProjectTemplate")
    val ssc = new StreamingContext(conf, Seconds(1))
    val meetupStream = MeetupDStream(ssc)
    meetupStream.saveAsTextFiles("output/output", "part")

    ssc.start()
    ssc.awaitTermination()
  }

}
