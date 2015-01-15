package io.basilic.myproject

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.storage.StorageLevel

object Main {

  def main(args: Array[String]) {
  
    /* Configuring Spark: https://spark.apache.org/docs/latest/configuration.html */
    val conf = new SparkConf().setAppName("SparkProjectTemplate")
    if (conf.get("spark.master", null) == null)
      conf.setMaster("local")

    val sc = new SparkContext(conf)

    /* Using HiveContext instead of SparkContext adds a lot more SQL features (CAST(), PERCENTILE(), ...) */
    // val sqlc = new org.apache.spark.sql.hive.HiveContext(sc)
    val sqlc = new org.apache.spark.sql.SQLContext(sc)
    

    /*
    Sources are standard URIs.
    Wildcard support depends on the URI scheme (not supported by file://, supported by s3n://).
    Gzip support is automatic.
     */
    val source = getClass.getResource("/data.json").toURI.toString
    // val source = "file:///tmp/data.json.gz"
    // val source = "s3n://bucket.name/access_logs/*/130/*.gz"

    /*
    Load the source data in a SchemaRDD (ready for SQL queries)
    and persist it on RAM, and DISK if not enough RAM
     */
    val sourceRdd = sqlc
      .jsonFile(source, Schemas.mySchema)
      .persist(StorageLevel.MEMORY_AND_DISK_SER)
    println(sourceRdd.take(1).toList)

    /* Register the RDD as a table */
    sourceRdd.registerTempTable("mytable")

    /* Make an SQL query */
    val resultRdd = sqlc.sql("SELECT * FROM mytable LIMIT 1")
    println(resultRdd.take(1).toList)
  }

}
