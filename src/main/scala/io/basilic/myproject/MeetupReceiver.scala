package io.basilic.myproject

import java.io.{PrintWriter, BufferedReader, BufferedWriter, OutputStreamWriter, InputStreamReader}
import java.net.Socket
import java.nio.charset.StandardCharsets

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver

class MeetupReceiver extends Receiver[String](StorageLevel.MEMORY_AND_DISK_2) {
  val host = "stream.meetup.com"
  val port = 80

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

  /** Create a socket connection and receive data until receiver is stopped */
  private def receive() {
    try {
      // Connect to host:port
      val socket = new Socket(host, port)

      // Necessary headers
      val headers = """GET /2/open_events HTTP/1.1
                      |Host: stream.meetup.com
                      |Connection: keep-alive
                      |""".stripMargin
      val out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
      out.println(headers)
      out.flush()

      // Until stopped or connection broken continue reading
      val reader = new BufferedReader(
        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
      )
      var userInput: String = null
      userInput = reader.readLine()
      while(!isStopped && userInput != null) {
        store(userInput)
        userInput = reader.readLine()
      }
      reader.close()
      socket.close()

      // Restart in an attempt to connect again when server is active again
      restart("Trying to connect again")
    } catch {
      case e: java.net.ConnectException =>
        // restart if could not connect to server
        restart("Error connecting to " + host + ":" + port, e)
      case t: Throwable =>
        // restart if there is any other error
        restart("Error receiving data", t)
    }
  }
}