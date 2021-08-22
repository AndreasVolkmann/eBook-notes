package me.avo.ebook.notes.input.device.kindle

class KindleNoteParsingException(rawNote: String, cause: Exception) : RuntimeException(
    "Parsing exception encountered for the following raw note: $rawNote", cause
)