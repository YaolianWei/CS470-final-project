package wordcount

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WordCount01 {
  def main(args: Array[String]): Unit = {

    // TODO Establish a connection with the Spark framework
    // JDBC : Connection
    val sparConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(sparConf)

    // TODO Perform operations

    // 1. Read the file, get the data line by line
    //    hello world
    val lines: RDD[String] = sc.textFile("TutorialExample/data")

    // 2. Split a row of data to form word by word (word segmentation)
    //    Flattening: the operation of splitting the whole into individual
    //   "hello world" => hello, world, hello, world
    val words: RDD[String] = lines.flatMap(_.split(" "))

    // 3. Group data according to words for easy statistics
    //    (hello, hello, hello), (world, world)
    val wordGroup: RDD[(String, Iterable[String])] = words.groupBy(word=>word)

    // 4. Convert the grouped data
    //    (hello, hello, hello), (world, world)
    //    (hello, 3), (world, 2)
    val wordToCount = wordGroup.map {
      case ( word, list ) => {
        (word, list.size)
      }
    }

    // 5. Collect the conversion result to the console and print it out
    val array: Array[(String, Int)] = wordToCount.collect()
    array.foreach(println)

    // TODO Close the connection
    sc.stop()
  }
}
