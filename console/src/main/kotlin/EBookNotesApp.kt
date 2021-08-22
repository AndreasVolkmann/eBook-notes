import me.avo.ebook.notes.infrastructure.NoteExporter
import me.avo.ebook.notes.data.ParsedNote
import me.avo.ebook.notes.data.RawNote
import me.avo.ebook.notes.input.device.Device
import me.avo.ebook.notes.notes.NoteMerger

class EBookNotesApp(
    private val device: Device,
    private val noteMerger: NoteMerger,
    private val exporter: NoteExporter,
) {

    fun run() {
        val (parsedNotes, failedNotes) = device.getDeviceNotes()
        println("Found ${parsedNotes.size} parsed notes, ${failedNotes.size} failed notes")
        mergeParsedNotes(parsedNotes)
        exportFailedNotes(failedNotes)
    }

    private fun mergeParsedNotes(parsedNotes: List<ParsedNote>) {
        val mergedNotes = noteMerger.merge(parsedNotes)
        exporter.export(mergedNotes)
    }

    private fun exportFailedNotes(notes: List<RawNote>) {
        exporter.exportFailedNotes(notes)
    }
}