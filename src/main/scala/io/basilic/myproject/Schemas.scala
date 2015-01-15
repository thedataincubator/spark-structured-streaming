package io.basilic.myproject

import org.apache.spark.sql.types._

import scala.collection.mutable.ArrayBuffer

/*
It's highly recommended to specify schemas (represented by a `StructType`) when working with SchemaRDDs:
- to retrieve only the fields we need
- to have fields available in SQL queries even if they're not present in the given JSON files (they'll have a NULL value)
- to avoid a crash with Hive if two JSON keys have the same name, but with a different case (e.g. *name* and *NaMe*)
*/
object Schemas {

  val mySchema =
    StructType(ArrayBuffer(
      StructField("field1", StringType, true),
      StructField("field2", IntegerType, true)
    ))

}
