import com.apurebase.arkenv.Arkenv
import com.apurebase.arkenv.feature.ProfileFeature
import com.apurebase.arkenv.feature.YamlFeature
import com.apurebase.arkenv.util.parse
import me.avo.ebook.notes.config.Configuration
import me.avo.ebook.notes.infrastructure.CsvNoteRepository
import me.avo.ebook.notes.input.device.kindle.Kindle
import me.avo.ebook.notes.notes.NoteMerger

fun main(args: Array<String>) {
    val configuration = parseConfiguration(args)

    when {
        configuration.headless -> getApp(configuration).run()
        else -> println("UI not supported yet")
    }
}

private fun parseConfiguration(args: Array<String>) = Arkenv.parse<Configuration>(args) {
    +ProfileFeature(parsers = listOf(::YamlFeature))
}

private fun getApp(configuration: Configuration): EBookNotesApp {
    val device = Kindle(configuration)
    val noteRepository = CsvNoteRepository(configuration.outputPath, configuration.outputFileName)
    val merger = NoteMerger(noteRepository, configuration.mergeOptions)
    return EBookNotesApp(device, merger, noteRepository)
}