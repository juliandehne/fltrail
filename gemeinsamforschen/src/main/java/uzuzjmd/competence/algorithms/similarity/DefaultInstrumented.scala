package uzuzjmd.competence.algorithms.similarity

import java.util.HashMap

import scala.collection.JavaConverters._
/**
  * Created by dehne on 21.09.2017.
  */
trait DefaultInstrumented {
  val startTimes = new HashMap[String, java.lang.Long]
  val stopTimes = new HashMap[String, java.lang.Long]

  def startTime( name: String): Unit = {
    startTimes.put(name, System.currentTimeMillis())
  }

  def stopTime ( name: String): Unit ={
    stopTimes.put(name, System.currentTimeMillis())
  };

  def showTime ( name:String ): Unit = {
    if (startTimes.containsKey(name) && stopTimes.containsKey(name)) {
      System.out.println(name + " took: " + (stopTimes.get(name) - startTimes.get(name)));
    }
  }

  def showTimes (): Unit = {
    stopTimes.keySet().asScala.foreach(showTime(_));
  }

}
