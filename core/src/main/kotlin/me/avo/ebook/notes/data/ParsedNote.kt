package me.avo.ebook.notes.data

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ParsedNote(
    var id: Long? = null,
    val title: String,
    val author: String,
    val metaData: String,
    val creationTimeStamp: Instant,
    override val content: String
) : Note
