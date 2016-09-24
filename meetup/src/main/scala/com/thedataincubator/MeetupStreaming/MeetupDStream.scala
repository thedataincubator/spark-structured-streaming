package com.thedataincubator.MeetupStreaming

import net.liftweb.json.{parse, DefaultFormats}
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.ReceiverInputDStream

case class Venue(venue_name: String, lat: Double, lon: Double)
case class Member(member_name: String, photo: Option[String])
case class Topic(topic_name: String, urlkey: String)
case class Event(event_name: String, event_url: String, time: Int)
case class Group(group_name: String, group_city: Option[String], group_country: Option[String], group_topics: List[Topic])
case class RSVP(
  venue: Option[Venue],
  member: Member,
  group: Group,
  event: Event,
  response: String
)

object MeetupDStream {
  implicit val formats = DefaultFormats
  def apply(ssc: StreamingContext) = {
    val meetupStream = ssc.receiverStream(new MeetupReceiver)
    meetupStream
      .filter(line => line.startsWith("{"))
      .flatMap(line => try {
        Some(parse(line).extract[RSVP])
      } catch {
        case _: Throwable => None
      })
  }
}
