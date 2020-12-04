package nl.skipdegroot.advent.shared

import java.io.File
import java.nio.charset.Charset

class DataLoader {

    fun loadData(filename: String): List<String> {
        val file = File(this.javaClass.classLoader.getResource(filename).toURI())
        return file.readLines(Charset.defaultCharset())
    }
}
