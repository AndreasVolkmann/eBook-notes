package me.avo.ebook.notes.input.device

import me.avo.ebook.notes.data.DeviceNoteParseResult
import me.avo.ebook.notes.data.Note
import me.avo.ebook.notes.data.ParsedNote
import me.avo.ebook.notes.data.RawNote

class GenericDeviceNoteProcessor(
    private val extractor: DeviceLineExtractor,
    private val parser: DeviceNoteParser
) : DeviceNoteProcessor {

    override fun getNotes(input: String): DeviceNoteParseResult {
        val notes = extractor
            .extractLines(input)
            .map(parser::parseRawNote)
        return partitionNotes(notes)
    }

    private fun partitionNotes(notes: List<Note>): DeviceNoteParseResult {
        val parsedNotes = mutableListOf<ParsedNote>()
        val failedNotes = mutableListOf<RawNote>()

        notes.forEach { note ->
            when (note) {
                is ParsedNote -> parsedNotes.add(note)
                is RawNote -> failedNotes.add(note)
            }
        }

        return DeviceNoteParseResult(
            removeDuplicates(parsedNotes), failedNotes
        )
    }

    private fun removeDuplicates(parsedNotes: List<ParsedNote>): List<ParsedNote> {
        return parsedNotes.distinctBy { it.title + it.content }
    }
}