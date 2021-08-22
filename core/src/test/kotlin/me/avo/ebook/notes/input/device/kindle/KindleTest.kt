package me.avo.ebook.notes.me.avo.ebook.notes.input.device.kindle

import me.avo.ebook.notes.TestUtil
import me.avo.ebook.notes.input.device.kindle.Kindle
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.hasSize

@Disabled
internal class KindleTest {

    @Test fun `getDeviceNotes - has expected results`() {
        // Arrange
        val kindle = Kindle(TestUtil.configuration)

        // Act
        val notes = kindle.getDeviceNotes()

        // Assert
        expectThat(notes) {
            get { parsedNotes } hasSize 1436
            get { failedNotes } hasSize 1
        }
    }
}