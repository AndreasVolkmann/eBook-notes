package me.avo.ebook.notes.input.device.oreilly

import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import me.avo.ebook.notes.config.Configuration
import me.avo.ebook.notes.data.DeviceNoteParseResult
import me.avo.ebook.notes.data.ParsedNote
import me.avo.ebook.notes.input.device.Device
import java.nio.file.Files

class OReilly(config: Configuration) : Device {
    private val path = config.outputPath.resolve(config.outputFileName)
    private val noteExtractor = OReillyNoteExtractor(path)

    init {
        require(Files.exists(path.parent))
    }

    override fun getDeviceNotes(): DeviceNoteParseResult {
        val oReillyNotes = noteExtractor.extract()
        val parsedNotes = oReillyNotes.map { it.toParsedNote() }
        return DeviceNoteParseResult(parsedNotes, emptyList())
    }

    private fun OReillyNote.toParsedNote() = ParsedNote(
        null, title, "", metaData, creationDate.atStartOfDayIn(TimeZone.UTC), content
    )
}