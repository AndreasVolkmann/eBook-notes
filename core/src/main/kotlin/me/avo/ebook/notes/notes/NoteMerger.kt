package me.avo.ebook.notes.notes

import kotlinx.datetime.Instant
import me.avo.ebook.notes.infrastructure.NoteImporter
import me.avo.ebook.notes.config.MergeOptions
import me.avo.ebook.notes.data.ParsedNote

class NoteMerger(
    private val noteImporter: NoteImporter,
    private val mergeOptions: MergeOptions) {

    fun merge(incomingNotes: Collection<ParsedNote>): List<ParsedNote> {
        val existingNotes = noteImporter.import()
        println("Found ${existingNotes.size} existing notes")
        return mergeNotes(existingNotes, incomingNotes)
    }

    private fun mergeNotes(
        existingNotes: Collection<ParsedNote>,
        incomingNotes: Collection<ParsedNote>
    ): List<ParsedNote> {
        val currentMaxTimeStamp = findMaxTimeStamp(existingNotes)
        var lastId = findMaxId(existingNotes)
        return existingNotes + incomingNotes
            .filter { it.creationTimeStamp > currentMaxTimeStamp }
            .onEach { it.id = ++lastId }
            .also { println("Found ${it.size} incoming notes") }
    }

    private fun findMaxId(notes: Collection<ParsedNote>): Long =
        notes.maxOfOrNull { it.id ?: 1 } ?: 1

    private fun findMaxTimeStamp(notes: Collection<ParsedNote>): Instant = when {
        mergeOptions.ignoreOldNotes -> {
            notes.maxOfOrNull(ParsedNote::creationTimeStamp) ?: Instant.DISTANT_PAST
        }
        else -> Instant.DISTANT_PAST
    }
}