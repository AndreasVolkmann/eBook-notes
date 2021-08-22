package me.avo.ebook.notes.config

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.*

internal class DateConfigurationTest {

    @Test fun `parse - ja`() {
        // Arrange
        val input = "2017年10月9日月曜日 8:06:17"
        val dtf = DateTimeFormatter.ofPattern("yyyy年M月d日E曜日 H:mm:ss", Locale.JAPAN)

        // Act
        val result = LocalDateTime.parse(input, dtf)

        // Assert
        expectThat(result) {
            get { year } isEqualTo 2017
            get { month } isEqualTo Month.OCTOBER
            get { dayOfMonth } isEqualTo 9
            get { hour } isEqualTo 8
            get { minute } isEqualTo 6
            get { second } isEqualTo 17
        }
    }

    @Test fun `parse - en`() {
        // Arrange
        val input = "Friday, March 6, 2020 7:57:42 PM"
        val dtf = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy K:mm:ss a", Locale.US)

        // Act
        val result = LocalDateTime.parse(input, dtf)

        // Assert
        expectThat(result) {
            get { year } isEqualTo 2020
            get { month } isEqualTo Month.MARCH
            get { dayOfMonth } isEqualTo 6
            get { hour } isEqualTo 19
            get { minute } isEqualTo 57
            get { second } isEqualTo 42
        }
    }
}