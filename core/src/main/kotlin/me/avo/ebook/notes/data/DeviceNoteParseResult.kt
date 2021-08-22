package me.avo.ebook.notes.data

data class DeviceNoteParseResult(
  val parsedNotes: List<ParsedNote>,
  val failedNotes: List<RawNote>
)