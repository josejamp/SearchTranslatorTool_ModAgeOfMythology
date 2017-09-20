package main.scala

import scala.collection.mutable.HashMap
import scala.collection.mutable.ListMap

import scala.io.Source
import java.io.IOException
import java.io.File
import java.io.FileWriter
import java.io.BufferedWriter

class FileReplacer(path_of_original : String, path_of_differences : String, path_to_write : String) {
  
  var path_diff = path_of_differences
  var path_orig = path_of_original
  var path_result = path_to_write
  
  var fileMap : HashMap[String, String] = new HashMap()
  
  def this(path_of_original : String, table : HashMap[String, String], path_to_write : String) = {
    this(path_of_original, "", path_to_write)
    fileMap = table
  }
  
  /*
  def replace() = {
    val filename = path_orig
    var acc = ""
    for (line <- Source.fromFile(filename)(scala.io.Codec("UTF-16")).getLines) {
      val split = line.split("\\s+",2)
      if(!line.startsWith("//") && split.length==2){
        val diff_val = fileMap.get(split(0))
        if(diff_val.nonEmpty){
          acc += split(0) + "\t\t" + diff_val.get + "\r\n"
        }
        else acc += line + "\r\n"
      }
      else acc += line + "\r\n"
    }
    val file = new File(this.path_result)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(acc)
    bw.close()
  }
  */
  
  def replace() = {
    val filename = path_orig
    var acc = ""
    var hashMap = new HashMap[String, String]()
    for (line <- Source.fromFile(filename)(scala.io.Codec("UTF-16")).getLines) {
      val split = line.split("\\s+",2)
      if(!line.startsWith("//") && split.length==2){
        hashMap.put(split(0), split(1))
      }
    }
    var dif = new HashMap[String, String]()
    dif = hashMap ++ this.fileMap
    
    var numbers = new HashMap[Int, String]()
    var constants = new HashMap[String, String]()
    for((k,v)<-dif){
      if(FileReplacer.isNumber(k)) numbers.put(k.toInt, v)
      else constants.put(k, v)
    }
    
    for((k,v)<-numbers.toSeq.sortBy(_._1)){
      acc += k + "\t\t" + v + "\r\n"
    }
    
    for((k,v)<-constants.toSeq.sortBy(_._1)){
      acc += k + "\t\t" + v + "\r\n"
    }
    
    val file = new File(this.path_result)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(acc)
    bw.close()
  }
  
}

object FileReplacer {
  
  def isNumber(chain : String) : Boolean = (chain != "") && (chain forall Character.isDigit)
  
}

