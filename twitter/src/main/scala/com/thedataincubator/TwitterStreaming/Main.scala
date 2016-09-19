package com.thedataincubator.TwitterStreaming

import org.apache.log4j.{Logger, Level}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{StreamingContext, Seconds}

object Main {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("TwitterStreaming")
    val ssc = new StreamingContext(conf, Seconds(1))
    val twitterDStream = TwitterDStream(ssc)
    	.map(s => String.format("%16s: %s", "@" + s.getUser.getScreenName, s.getText))
    	
    args match {
      case Array() =>
        // Disable logging to make messages more clear
        Logger.getLogger("org").setLevel(Level.OFF)
        Logger.getLogger("akka").setLevel(Level.OFF)
        twitterDStream.print
      case Array(output) => twitterDStream.saveAsTextFiles(s"output/$output", "txt")
      case _ => throw new IllegalArgumentException("Expecting at most one argument");
    }
    ssc.start()
    ssc.awaitTermination()
  }
}
