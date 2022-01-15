import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Demo {
  def main(args: Array[String]) = {
      val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
      val sc = new SparkContext(sparkConf)

      // 1. read file, get data by line
      val lines: RDD[String] = sc.parallelize(Array("Hello World Jack", "Hello World Luna", "Hello World Jack"))

    // 2. break each line to words
      val words: RDD[String] = lines.flatMap(_.split(" "))

      // 3. split data based on words
      val wordGroup: RDD[(String, Iterable[String])] = words.groupBy(word => word)

      // 4. convert data after splitting
      val wordToCount = wordGroup.map {
        case (word, list) => {
          (word, list.size)
        }
      }

      // 5. collect the results and print
      val array: Array[(String, Int)] = wordToCount.collect()
      array.foreach(println)

      // close connection
      sc.stop()
  }
}
