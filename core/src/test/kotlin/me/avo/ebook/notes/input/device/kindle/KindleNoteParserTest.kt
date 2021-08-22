package me.avo.ebook.notes.me.avo.ebook.notes.input.device.kindle

import kotlinx.datetime.Instant
import me.avo.ebook.notes.UnknownAuthor
import me.avo.ebook.notes.config.DateConfiguration
import me.avo.ebook.notes.data.ParsedNote
import me.avo.ebook.notes.input.device.kindle.KindleNoteParser
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo

internal class KindleNoteParserTest {
    private val dateConfiguration = DateConfiguration("作成日: ", "yyyy年M月d日E曜日 H:mm:ss", "ja")
    private val parser = KindleNoteParser(false, dateConfiguration)

    @Test fun `parseRawNote - ja`() {
        // Arrange
        val rawNote = """
            千羽鶴 (川端康成)
            - 位置No. 38-39のハイライト |作成日: 2017年8月28日月曜日 17:51:55

            てからも、ちか子は少しぼんやり茶をたてた。 　それから十日ばかり後に菊治
        """

        // Act
        val note = parser.parseRawNote(rawNote)

        // Assert
        expectThat(note).isA<ParsedNote>() and {
            get { title } isEqualTo "千羽鶴"
            get { author } isEqualTo "川端康成"
            get { metaData } isEqualTo "位置No. 38-39のハイライト"
            get { creationTimeStamp } isEqualTo Instant.parse("2017-08-28T17:51:55.000Z")
            get { content } isEqualTo "てからも、ちか子は少しぼんやり茶をたてた。 　それから十日ばかり後に菊治"
        }
    }

    @Test fun `parseRawNote - en`() {
        // Arrange
        val rawNote = """
            How Not to Die (Michael Greger MD)
            - 220ページ|位置No. 3363-3364のハイライト |作成日: 2020年6月29日月曜日 13:45:16

            The relationship between national per capita pork consumption and deaths from liver disease correlates as tightly as per capita alcohol consumption and liver fatalities.
        """.trimIndent()

        // Act
        val note = parser.parseRawNote(rawNote)

        // Assert
        expectThat(note).isA<ParsedNote>() and {
            get { title } isEqualTo "How Not to Die"
            get { author } isEqualTo "Michael Greger MD"
            get { metaData } isEqualTo "220ページ|位置No. 3363-3364のハイライト"
            get { creationTimeStamp } isEqualTo Instant.parse("2020-06-29T13:45:16.000Z")
            get { content } isEqualTo "The relationship between national per capita pork consumption and deaths from liver disease correlates as tightly as per capita alcohol consumption and liver fatalities."
        }
    }

    @Test fun `parseRawNote - complex source`() {
        // Arrange
        val rawNote = """
            ハリー・ポッターと賢者の石 - Harry Potter and the Philosopher's Stone (ハリー・ポッターシリーズ) (Rowling, J.K.)
            - 位置No. 4080-4081のハイライト |作成日: 2019年12月16日月曜日 17:14:03
            
            箱を開けると、大きさの違うボールが四個あった。
        """.trimIndent()

        // Act
        val note = parser.parseRawNote(rawNote)

        // Assert
        expectThat(note).isA<ParsedNote>() and {
            get { title } isEqualTo "ハリー・ポッターと賢者の石 - Harry Potter and the Philosopher's Stone (ハリー・ポッターシリーズ)"
            get { author } isEqualTo "Rowling, J.K."
            get { metaData } isEqualTo "位置No. 4080-4081のハイライト"
            get { creationTimeStamp } isEqualTo Instant.parse("2019-12-16T17:14:03.000Z")
            get { content } isEqualTo "箱を開けると、大きさの違うボールが四個あった。"
        }
    }

    @Test fun `parseRawNote - no author`() {
        // Arrange
        val rawNote = """
            Thoughts of Marcus Aurelius  
            - 位置No. 1050-1050のハイライト |作成日: 2019年6月24日月曜日 17:36:20
            
            It is not right to vex ourselves at things, For they care nought about it.
        """.trimIndent()

        // Act
        val note = parser.parseRawNote(rawNote)

        // Assert
        expectThat(note).isA<ParsedNote>() and {
            get { title } isEqualTo "Thoughts of Marcus Aurelius"
            get { author } isEqualTo UnknownAuthor
            get { metaData } isEqualTo "位置No. 1050-1050のハイライト"
            get { creationTimeStamp } isEqualTo Instant.parse("2019-06-24T17:36:20.000Z")
            get { content } isEqualTo "It is not right to vex ourselves at things, For they care nought about it."
        }
    }
}