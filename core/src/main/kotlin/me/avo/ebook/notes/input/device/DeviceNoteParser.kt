package me.avo.ebook.notes.input.device

import me.avo.ebook.notes.data.Note

interface DeviceNoteParser {
    fun parseRawNote(rawContent: String): Note
}