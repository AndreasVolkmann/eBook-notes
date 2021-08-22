package me.avo.ebook.notes.config

import com.apurebase.arkenv.module.module
import java.nio.file.Files
import java.nio.file.Path

data class Configuration(
    val devicePath: Path,
    val deviceFileName: String,
    val outputPath: Path,
    val outputFileName: String,
    val delimiter: String,
    val headless: Boolean = true,
    val ignoreParsingError: Boolean = true,
) {
    init {
        require(Files.exists(devicePath)) { "Provided device path does not exist" }
        require(deviceFileName.isNotBlank()) { "Provided notes file name cannot be blank" }
        require(Files.exists(outputPath)) { "Provided output path does not exist" }
        require(outputFileName.isNotBlank()) { "Provided output file name cannot be blank" }
        require(delimiter.isNotBlank()) { "Provided delimiter is blank" }
    }

    val dateConfiguration by module<DateConfiguration>()
    val mergeOptions by module<MergeOptions>()
}