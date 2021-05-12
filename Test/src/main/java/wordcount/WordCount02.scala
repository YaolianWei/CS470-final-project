package wordcount

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WordCount02 {
  def main(args: Array[String]): Unit = {

    // TODO Establish a connection with the Spark framework
    // JDBC : Connection
    val sparConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(sparConf)

    // TODO 执行业务操作

    // 1. Read the file, get the data line by line
    //    hello world
    val lines: RDD[String] = sc.textFile("Test/data")

    // 2. Split a row of data to form word by word (word segmentation)
    //    Flattening: the operation of splitting the whole into individual
    //   "hello world" => hello, world, hello, world
    val words: RDD[String] = lines.flatMap(_.split(" "))

    // 3. Convert the word structure to facilitate statistics
    //    word => (word, 1)
    val wordToOne = words.map(word=>(word,1))

    // 4. Group and aggregate the converted data
    //    reduceByKey: The value of the same key is aggregated
    // (word, 1) => (word, sum)
    val wordToSum: RDD[(String, Int)] = wordToOne.reduceByKey(_+_)

    // 5. Collect the conversion result to the console and print it out
    val array: Array[(String, Int)] = wordToSum.collect()
    array.foreach(println)

    // TODO 关闭连接
    sc.stop()
  }
}
