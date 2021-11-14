package me.avo.ebook.notes.device.oreilly

import kotlinx.datetime.LocalDate
import me.avo.ebook.notes.TestUtil
import me.avo.ebook.notes.input.device.oreilly.OReillyNoteExtractor
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.all
import strikt.assertions.get
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import java.nio.file.Paths
import java.time.Month

class OReillyNoteExtractorTest {
    private val pathString = TestUtil.getResourcePath("oreilly-annotations.csv").trimStart('/')
    private val path = Paths.get(pathString)
    private val noteExtractor = OReillyNoteExtractor(path)

    @Test fun `extract - correct size`() {
        val actual = noteExtractor.extract()
        expectThat(actual) { hasSize(7) }
    }

    @Test fun `extract - correct title`() {
        val expectedTitle = "Clean Code: A Handbook of Agile Software Craftsmanship"
        val actual = noteExtractor.extract()
        expectThat(actual) {
            all {
                get { title } isEqualTo expectedTitle
            }
        }
    }

    @Test fun `extract - correct date`() {
        val expectedDate = LocalDate(2021, Month.OCTOBER, 28)
        val actual = noteExtractor.extract()
        expectThat(actual) {
            all {
                get { creationDate } isEqualTo expectedDate
            }
        }
    }

    @Test fun `extract - correct content`() {
        val expectedContent = listOf(
            "Few practices are as odious as commenting-out code. Don’t do this!",
            "Truth can only be found in one place: the code. Only the code can truly tell you what it does. It is the only source of truly accurate information. Therefore, though comments are sometimes necessary, we will expend significant energy to minimize them.")
        val actual = noteExtractor.extract()
        expectThat(actual) {
            get(0).get { content } isEqualTo expectedContent[0]
            get(1).get { content } isEqualTo expectedContent[1]
        }
    }
}