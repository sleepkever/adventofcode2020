package nl.skipdegroot.advent.shared

import java.io.File
import java.nio.charset.Charset

class DataLoader {

    fun loadData(filename: String): List<String> =
            getFile(filename).readLines(Charset.defaultCharset())


    fun getFile(filename: String) = File(this.javaClass.classLoader.getResource(filename).toURI())

}
