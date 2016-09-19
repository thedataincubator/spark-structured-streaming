package com.thedataincubator.TwitterStreaming

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.ReceiverInputDStream

// To create credentials, goto https://apps.twitter.com/app/
// To load crednetials, create a twitter4j.properties in the same folder as twitter4j.properties.sample
// For more information, see http://twitter4j.org/en/configuration.html

object TwitterDStream {
  def apply(ssc: StreamingContext) = ssc.receiverStream(new TwitterReceiver)
}