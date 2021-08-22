package me.avo.ebook.notes.input.device

interface DeviceLineExtractor {
    fun extractLines(input: String): List<String>
}