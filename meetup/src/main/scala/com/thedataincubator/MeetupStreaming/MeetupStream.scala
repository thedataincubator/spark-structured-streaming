package com.thedataincubator.MeetupStreaming

import net.liftweb.json.{parse, DefaultFormats}
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.ReceiverInputDStream

case class Venue(name: String, lat: Option[Double], lon: Option[Double], city: String, country: String)
case class Category(name: String, shortname: String, id: Long)
case class Group(name: String, id: Long, category: Option[Category])
case class Event(name: String, venue: Venue, group: Group)

object MeetupDStream {
  implicit val formats = DefaultFormats
  def apply(ssc: StreamingContext) = {
    val meetupStream = ssc.receiverStream(new MeetupReceiver)
    meetupStream
      .filter(line => line.startsWith("{"))
      .flatMap(line => try{
        Some(parse(line).extract[Event])
      } catch {
        case e: Throwable => None
      })
  }
}
