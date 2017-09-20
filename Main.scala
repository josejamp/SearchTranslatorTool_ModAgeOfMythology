package main.scala

object Main extends App{
  
  override def main(args: Array[String]): Unit = {
    /*
    var fileComp = new FileComparator("src/main/resources/english/en-language.txt","src/main/resources/english/en-language_mod.txt","src/main/resources/english/en-language_diff.txt")
    fileComp.initFile1()
    fileComp.initFile2()
    
    var fileReplacer = new FileReplacer("src/main/resources/spanish/es-language.txt",fileComp.differences(),"src/main/resources/spanish/es-language_mod.txt")
    fileReplacer.replace()
    * */
    
    var fileUpdater = new FileUpdater("src/main/resources/spanish/es-language-mod-def.txt","src/main/resources/english/en-language_diff.txt","src/main/resources/spanish/es-language-mod-def_v2.txt")
    fileUpdater.update()
    
  }
  
}