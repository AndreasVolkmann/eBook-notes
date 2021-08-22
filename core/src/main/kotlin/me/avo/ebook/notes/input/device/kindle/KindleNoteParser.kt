package me.avo.ebook.notes.input.device.kindle

import kotlinx.datetime.Instant
import me.avo.ebook.notes.UnknownAuthor
import me.avo.ebook.notes.config.DateConfiguration
import me.avo.ebook.notes.data.Note
import me.avo.ebook.notes.data.ParsedNote
import me.avo.ebook.notes.data.RawNote
import me.avo.ebook.notes.input.device.DeviceNoteParser
import java.time.ZoneOffset

class KindleNoteParser(
    private val ignoreParsingError: Boolean,
    private val dateConfiguration: DateConfiguration
) : DeviceNoteParser {

    override fun parseRawNote(rawContent: String): Note {
        val lines = rawContent
            .split('\n')
            .map(String::trim)
            .filter(String::isNotBlank)
        return try {
            linesToNote(lines) ?: RawNote(rawContent, null)
        } catch (ex: IndexOutOfBoundsException) {
            handleParsingException(ex, rawContent)
        }
    }

    private fun linesToNote(lines: List<String>): Note? {
        val metadataSplit = lines[1].split(dateConfiguration.timestampDelimiter)
        return when {
            metadataSplit.size != 2 -> null
            else -> {
                val sourceLine = splitSourceLine(lines[0])
                ParsedNote(
                    title = sourceLine.first,
                    author = sourceLine.second,
                    metaData = metadataSplit[0].removePrefix("- ").removeSuffix(" |"),
                    creationTimeStamp = parseCreationDateTime(metadataSplit[1]),
                    content = lines[2],
                )
            }
        }
    }

    private fun splitSourceLine(sourceLine: String): Pair<String, String> =
        when (val lastOpeningIndex = sourceLine.lastIndexOf('(')) {
            -1 -> sourceLine to UnknownAuthor
            else -> Pair(
                sourceLine.substring(0, lastOpeningIndex - 1),
                sourceLine.substring(lastOpeningIndex + 1, sourceLine.length - 1))
        }

    private fun parseCreationDateTime(dateTimeString: String): Instant {
        val epochSeconds = java.time.LocalDateTime
            .parse(dateTimeString, dateConfiguration.dateTimeFormatter)
            .toEpochSecond(ZoneOffset.UTC)
        return Instant.fromEpochSeconds(epochSeconds)
    }

    private fun handleParsingException(exception: Exception, rawContent: String): RawNote = when {
        ignoreParsingError -> RawNote(rawContent, exception)
        else -> throw KindleNoteParsingException(rawContent, exception)
    }
}