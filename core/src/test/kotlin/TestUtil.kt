package me.avo.ebook.notes

import me.avo.ebook.notes.config.Configuration
import java.nio.file.Path
import java.nio.file.Paths

internal object TestUtil {
    val configuration = Configuration(
        getTestResourcePath(), "My Clippings.txt",
        getTestResourcePath(), "Output.csv", "==========")

    internal fun getTestResourcePath(): Path {
        val filePath = getResourcePath("application.yml").trimStart('/')
        return Paths.get(filePath).parent.toAbsolutePath()
    }

    internal fun getResourcePath(name: String) = this::class.java.classLoader.getResource(name)?.path
        ?: throw getResourceNotFoundException(name)

    internal fun readResource(name: String): String {
        return this::class.java.classLoader.getResource(name)?.readText() ?: throw getResourceNotFoundException(name)
    }

    private fun getResourceNotFoundException(name: String) =
        IllegalArgumentException("Resource $name could not be found")
}