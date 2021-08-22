package me.avo.ebook.notes.input.device

import me.avo.ebook.notes.data.DeviceNoteParseResult

interface DeviceNoteProcessor {
    fun getNotes(input: String): DeviceNoteParseResult
}