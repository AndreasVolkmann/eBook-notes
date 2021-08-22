package me.avo.ebook.notes.input.device.kindle

import me.avo.ebook.notes.TestUtil
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.*

internal class KindleLineExtractorTest {

    private val extractor = KindleLineExtractor(TestUtil.configuration.delimiter)
    private val rawNoteA = """
            ==========
            千羽鶴 (川端康成)
            - 位置No. 38-39のハイライト |作成日: 2017年8月28日月曜日 17:51:55

            てからも、ちか子は少しぼんやり茶をたてた。 　それから十日ばかり後に菊治
            ==========
            千羽鶴 (川端康成)
            - 位置No. 38-39のハイライト |作成日: 2017年8月28日月曜日 17:52:36

            てからも、ちか子は少しぼんやり茶をたてた。 　それから十日ばかり後に菊治は、母がさも
        """.trimIndent()

    @Test fun `extractLines - correct start and end`() {
        val source = "千羽鶴 (川端康成)"
        val lines = extractor.extractLines(rawNoteA)

        expectThat(lines) {
            get(0)
                .startsWith(source)
                .endsWith("それから十日ばかり後に菊治")
            get(1)
                .startsWith(source)
                .endsWith("母がさも")
        }
    }

    @Test fun `extractLines - empty notes should be filtered out`() {
        // Arrange
        val expectedSize = 2

        // Act
        val lines = extractor.extractLines(rawNoteA)

        // Assert
        println(lines)
        expectThat(lines) {
            hasSize(expectedSize)
        }
    }

}