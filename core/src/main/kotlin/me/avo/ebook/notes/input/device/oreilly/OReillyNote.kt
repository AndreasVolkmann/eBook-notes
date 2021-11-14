package me.avo.ebook.notes.input.device.oreilly

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.avo.ebook.notes.data.Note

@Serializable
class OReillyNote(
    @SerialName("Book Title")
    val title: String,

    @SerialName("Chapter Title")
    val metaData: String,

    @SerialName("Date of Highlight")
    val creationDate: LocalDate,

    @SerialName("Highlight")
    override val content: String
) : Note