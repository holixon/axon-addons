package io.toolisticon.addons.axon.mapdb

import org.mapdb.DBMaker
import org.mapdb.Serializer
import java.io.File


fun main() {

  val file = File("./temp.db")
  println("file=${file.absolutePath}")

  val db = DBMaker.fileDB(file.absolutePath).make()


 // println("f: ${file.readText()}")



  val map = db.hashMap("foo", Serializer.STRING, Serializer.INTEGER).createOrOpen()

  map["foo"] = 7

  println("--- ${map.keys}")


  println("f: ${file.readText()}")
  db.commit()
  println("f: ${file.readText()}")

  db.close()


}
