package me.avo.ebook.notes.data

data class RawNote(
    override val content: String,
    val exception: Exception?
) : Note