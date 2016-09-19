package com.thedataincubator.TwitterStreaming

import scala.io.Source
import twitter4j._
import twitter4j.auth.Authorization
import twitter4j.conf.ConfigurationBuilder
import twitter4j.auth.OAuthAuthorization

import java.io.{PrintWriter, BufferedReader, BufferedWriter, OutputStreamWriter, InputStreamReader}
import java.net.Socket
import java.nio.charset.StandardCharsets

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver


// To create credentials, goto https://apps.twitter.com/app/
// To load crednetials, create a twitter4j.properties in the same folder as twitter4j.properties.sample
// For more information, see http://twitter4j.org/en/configuration.html

class TwitterReceiver(language: String = "en") extends Receiver[Status](StorageLevel.MEMORY_AND_DISK_2) {
  private def simpleStatusListener = new StatusListener() {
    def onStatus(status: Status) { store(status) }
    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}
    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}
    def onException(ex: Exception) { ex.printStackTrace }
    def onScrubGeo(arg0: Long, arg1: Long) {}
    def onStallWarning(warning: StallWarning) {}
  }

  def onStart() {
    // Start the thread that receives data over a connection
    new Thread("Socket Receiver") {
      override def run() { receive() }
    }.start()
  }

  def onStop() {
    // There is nothing much to do as the thread calling receive()
    // is designed to stop by itself if isStopped() returns false
  }

  private def receive() {
    val twitterStream = new TwitterStreamFactory().getInstance()
    twitterStream.addListener(simpleStatusListener)
    twitterStream.sample(language)
    while(!isStopped) { }
    twitterStream.cleanUp
    twitterStream.shutdown
  }
}
