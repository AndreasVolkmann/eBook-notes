package me.avo.ebook.notes.infrastructure

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.csv.Csv
import me.avo.ebook.notes.data.ParsedNote
import me.avo.ebook.notes.data.RawNote
import java.nio.file.Files
import java.nio.file.Path

@OptIn(ExperimentalSerializationApi::class)
class CsvNoteRepository(
    outputPath: Path,
    outputFileName: String
) : NoteImporter, NoteExporter {
    private val path = outputPath.resolve(outputFileName)
    private val failedPath = outputPath.resolve("failed_$outputFileName")
    private val csv = Csv {
        hasHeaderRecord = true
    }
    private val serializer = ListSerializer(ParsedNote.serializer())

    init {
        require(Files.exists(path.parent))
    }

    override fun export(notes: List<ParsedNote>) {
        val serialized = csv.encodeToString(serializer, notes)
        Files.writeString(path, serialized)
    }

    override fun exportFailedNotes(notes: List<RawNote>) {
        val lines = notes.map { it.content + " \n{${it.exception?.message}}" }
        Files.write(failedPath, lines)
    }

    override fun import(): List<ParsedNote> = when {
        Files.exists(path) -> readFromFile()
        else -> listOf()
    }

    private fun readFromFile(): List<ParsedNote> {
        val content = Files.readString(path)
        return csv.decodeFromString(serializer, content)
    }
}