package me.avo.ebook.notes.device

import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.Instant
import me.avo.ebook.notes.data.ParsedNote
import me.avo.ebook.notes.input.device.DeviceLineExtractor
import me.avo.ebook.notes.input.device.DeviceNoteParser
import me.avo.ebook.notes.input.device.GenericDeviceNoteProcessor
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.count
import strikt.assertions.isEqualTo

internal class GenericDeviceNoteProcessorTest {

    @Test fun `getNotes - removes duplicates`() {
        // Arrange
        val input = ""
        val note = ParsedNote(null, "A", "B",  "D", Instant.DISTANT_PAST,"C")
        val lineExtractor = mockk<DeviceLineExtractor> {
            every { extractLines(input) } returns listOf("1", "2")
        }
        val deviceNoteParser = mockk<DeviceNoteParser> {
            every { parseRawNote(any()) } returns note andThen note.copy(metaData = "D")
        }
        val processor = GenericDeviceNoteProcessor(lineExtractor, deviceNoteParser)

        // Act
        val notes = processor.getNotes(input)

        // Assert
        expectThat(notes.parsedNotes) {
            count() isEqualTo 1
        }
    }
}