package me.avo.ebook.notes.input.device.kindle

import me.avo.ebook.notes.config.Configuration
import me.avo.ebook.notes.data.DeviceNoteParseResult
import me.avo.ebook.notes.input.device.Device
import me.avo.ebook.notes.input.device.GenericDeviceNoteProcessor
import java.nio.file.Files

class Kindle(config: Configuration) : Device {
    private val extractor = KindleLineExtractor(config.delimiter)
    private val parser = KindleNoteParser(config.ignoreParsingError, config.dateConfiguration)
    private val processor = GenericDeviceNoteProcessor(extractor, parser)
    private val notesPath = config.resolveDeviceFilePath()

    override fun getDeviceNotes(): DeviceNoteParseResult {
        val input = Files.readString(notesPath)
        return processor.getNotes(input)
    }
}