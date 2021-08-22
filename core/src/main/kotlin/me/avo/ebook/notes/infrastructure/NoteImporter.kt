package me.avo.ebook.notes.infrastructure

import me.avo.ebook.notes.data.ParsedNote

interface NoteImporter {
    fun import(): List<ParsedNote>
}