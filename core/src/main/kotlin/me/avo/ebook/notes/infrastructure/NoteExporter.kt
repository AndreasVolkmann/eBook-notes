package me.avo.ebook.notes.infrastructure

import me.avo.ebook.notes.data.ParsedNote
import me.avo.ebook.notes.data.RawNote

interface NoteExporter {
    fun export(notes: List<ParsedNote>)
    fun exportFailedNotes(notes: List<RawNote>)
}