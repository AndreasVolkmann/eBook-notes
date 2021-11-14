package me.avo.ebook.notes.input.device.oreilly

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.csv.Csv
import java.nio.file.Files
import java.nio.file.Path

@OptIn(ExperimentalSerializationApi::class)
class OReillyNoteExtractor(
    private val path: Path
) {
    private val serializer = ListSerializer(OReillyNote.serializer())
    private val csv = Csv {
        hasHeaderRecord = true
        ignoreUnknownColumns = true
    }

    init {
        require(Files.exists(path.parent))
    }

    fun extract(): List<OReillyNote> {
        val content = Files.readString(path)
        return csv.decodeFromString(serializer, content)
    }
}