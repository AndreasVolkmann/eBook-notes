package me.avo.ebook.notes.input.device.kindle

import me.avo.ebook.notes.input.device.DeviceLineExtractor

class KindleLineExtractor(
    private val delimiter: String
) : DeviceLineExtractor {

    override fun extractLines(input: String): List<String> {
        return input
            .split(delimiter)
            .filter(String::isNotBlank)
            .map(String::trim)
    }

}