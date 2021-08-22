package me.avo.ebook.notes.notes

import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.Instant
import me.avo.ebook.notes.infrastructure.NoteImporter
import me.avo.ebook.notes.config.MergeOptions
import me.avo.ebook.notes.data.ParsedNote
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.map
import strikt.assertions.single
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
internal class NoteMergerTest {
    private val existingId = 7L
    private val timestamp = Instant.DISTANT_PAST + Duration.microseconds(1)
    private val incomingContent = "incoming"
    private val existingContent = "existing"
    private val existingNote = ParsedNote(existingId, "a", "b", "c", timestamp, existingContent)

    @Test fun `merge - new timestamp - included`() {
        // Arrange
        val merger = getMerger(listOf())
        val incomingNotes = listOf(ParsedNote(null, "a", "b", "c", timestamp, incomingContent),)

        // Act
        val result = merger.merge(incomingNotes)

        // Assert
        expectThat(result) { single().get { content } isEqualTo incomingContent }
    }

    @Test fun `merge - old timestamp - excluded`() {
        // Arrange
        val merger = getMerger(listOf(existingNote))
        val incomingNotes = listOf(ParsedNote(2, "a", "b", "c", timestamp, incomingContent))

        // Act
        val result = merger.merge(incomingNotes)

        // Assert
        expectThat(result) { single().get { content } isEqualTo existingContent }
    }

    @Test fun `merge - multiple - unique ids`() {
        // Arrange
        val newerTimestamp = timestamp + Duration.microseconds(1)
        val merger = getMerger(listOf(existingNote))
        val incomingNotes = listOf(
            ParsedNote(null, "a", "b", "c", newerTimestamp, incomingContent),
            ParsedNote(null, "a", "b", "c", newerTimestamp, incomingContent),
        )

        // Act
        val result = merger.merge(incomingNotes)

        // Assert
        expectThat(result) { map(ParsedNote::id) isEqualTo listOf(7, 8, 9) }
    }

    @Test fun `merge - ignoreOldNotes false - included`() {
        // Arrange
        val merger = getMerger(listOf(existingNote), MergeOptions(ignoreOldNotes = false))
        val incomingNotes = listOf(ParsedNote(2, "a", "b", "c", timestamp, incomingContent))

        // Act
        val result = merger.merge(incomingNotes)

        // Assert
        expectThat(result) { hasSize(2) }
    }

    private fun getMerger(
        existingNotes: List<ParsedNote>,
        mergeOptions: MergeOptions = MergeOptions(true)
    ): NoteMerger {
        val importer = mockk<NoteImporter> { every { import() } returns existingNotes }
        return NoteMerger(importer, mergeOptions)
    }
}