package me.avo.ebook.notes.config

import com.apurebase.arkenv.module.module
import me.avo.ebook.notes.input.device.DeviceType
import java.nio.file.Files
import java.nio.file.Path

data class Configuration(
    private val deviceType: String,
    val devicePath: Path,
    val deviceFileName: String,
    val outputPath: Path,
    val outputFileName: String,
    val delimiter: String,
    val headless: Boolean = true,
    val ignoreParsingError: Boolean = true,
) {
    init {
        require(Files.exists(outputPath)) { "Provided output path does not exist" }
        require(outputFileName.isNotBlank()) { "Provided output file name cannot be blank" }
        require(delimiter.isNotBlank()) { "Provided delimiter is blank" }
    }

    val dateConfiguration by module<DateConfiguration>()
    val mergeOptions by module<MergeOptions>()

    fun getDeviceType() = DeviceType.valueOf(deviceType)

    fun resolveDeviceFilePath(): Path {
        val path = devicePath.resolve(deviceFileName)
        require(Files.exists(path)) { "Device file path does not exist: $path" }
        return path
    }
}