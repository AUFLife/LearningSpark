package sql

import org.apache.spark.sql.types._
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

//
//
//
object Types {
  case class Cust(id: Integer, name: String, sales: Double, discount: Double, state: String)

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("SQL-Types").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    import sqlContext.implicits._

    val numericRows = Seq(
      Row(1, 2, 3, 4, 1.0, 2.0, 3.0, 4.0)
    )
    val numericRowsRDD = sc.parallelize(numericRows, 4)

    val numericSchema = StructType(
      Seq(
        StructField("a", ByteType, true),
        StructField("b", ShortType, true),
        StructField("c", IntegerType, true),
        StructField("d", LongType, true),
        StructField("e", DecimalType(10, 5), true),
        StructField("f", DecimalType(20, 10), true),
        StructField("g", FloatType, true),
        StructField("h", DoubleType, true)
      )
    )

    val numericDF = sqlContext.createDataFrame(numericRowsRDD, numericSchema)

    numericDF.printSchema()

    numericDF.show()

    numericDF.registerTempTable("numeric")

    sqlContext.sql("SELECT * from numeric").show()

    val miscSchema = StructType(
      Seq(
        StructField("a", BooleanType, true),
        StructField("b", NullType, true),
        StructField("c", StringType, true),
        StructField("d", BinaryType, true)
      )
    )

    val complexScehma = StructType(
        Seq(
          StructField("a", StructType(
            Seq(
              StructField("u", StringType, true),
              StructField("v", StringType, true),
              StructField("w", StringType, true)
            )
          ), true),
          StructField("b", NullType, true),
          StructField("c", StringType, true),
          StructField("d", BinaryType, true)
        )
      )

  }
}
