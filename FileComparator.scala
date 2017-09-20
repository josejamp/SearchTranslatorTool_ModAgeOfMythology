package main.scala

import scala.collection.mutable.HashMap

import scala.io.Source
import java.io.IOException
import java.io.File
import java.io.FileWriter
import java.io.BufferedWriter


class FileComparator(path_old : String, path_mod : String, path_to_write : String) {
  
  var path1 : String = path_old
  var path2 : String = path_mod
  var path_diff : String = path_to_write
  
  var file1 : HashMap[String, String] = new HashMap()
  var file2 : HashMap[String, String] = new HashMap()
  
  def initFile1() ={
    FileComparator.initHashMap(path1, file1)
  }
  
  def initFile2() ={
    FileComparator.initHashMap(path2, file2)
  }
  
  def differences() : HashMap[String, String] = {
    var dif = new HashMap[String, String]()
    for((k,v)<-this.file2){
      val original = this.file1.get(k)
      if(original.isEmpty || (!original.isEmpty && original.get!=v)){
        dif.put(k,v)
      }
    }
    return dif
  }
  
  def writeDifferences() = {
    val file = new File(this.path_to_write)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(FileComparator.hashToString(this.differences()))
    bw.close()
  }
  
  override def toString() : String = {
    var acc = ""
    acc += "File1: " + path1 + "\n"
    acc += "Contents:" + "\n"
    for((k,v)<-file1){
      acc += "Key: " + k + " ,value: " + v + "\n"
    }
    acc += "\n \n"
    acc += "File2: " + path2 + "\n"
    acc += "Contents:" + "\n"
    for((k,v)<-file2){
      acc += "Key: " + k + " ,value: " + v + "\n"
    }
    return acc
  }
  
}

object FileComparator {
  
  def initHashMap(path : String, hashMap : HashMap[String, String]) = {
    val filename = path
    for (line <- Source.fromFile(filename)(scala.io.Codec("UTF-16")).getLines) {
      
      val split = line.split("\\s+",2)
      if(!line.startsWith("//") && split.length==2){
        hashMap.put(split(0), split(1))
      }
      
    }
  }
  
  def hashToString(tabla : HashMap[String, String]) : String = {
    var acc = ""
    for((k,v)<-tabla){
      acc += (k + "      " + v + "\n")
    }
    return acc
  }
  
}