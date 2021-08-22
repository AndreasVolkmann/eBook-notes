package me.avo.ebook.notes.input.device

import me.avo.ebook.notes.data.DeviceNoteParseResult

/**
 * A generic eReader device, from which notes can be extracted.
 */
interface Device {
    fun getDeviceNotes(): DeviceNoteParseResult
}