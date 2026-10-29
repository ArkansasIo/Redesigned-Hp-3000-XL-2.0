package io.arkansasio.hp3000xl.emulator

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class SimhMediaCopyTest {
    @Test
    fun copiesBytesAndReturnsExactLength() {
        val expected = ByteArray(4096) { index -> (index % 251).toByte() }
        val output = ByteArrayOutputStream()

        val copied = SimhMediaCopy.copy(
            input = ByteArrayInputStream(expected),
            output = output,
            maxBytes = expected.size.toLong()
        )

        assertEquals(expected.size.toLong(), copied)
        assertArrayEquals(expected, output.toByteArray())
    }

    @Test
    fun acceptsInputExactlyAtLimit() {
        val output = ByteArrayOutputStream()
        val copied = SimhMediaCopy.copy(
            input = ByteArrayInputStream(byteArrayOf(1, 2, 3, 4)),
            output = output,
            maxBytes = 4
        )

        assertEquals(4L, copied)
        assertArrayEquals(byteArrayOf(1, 2, 3, 4), output.toByteArray())
    }

    @Test
    fun rejectsInputThatExceedsLimit() {
        val output = ByteArrayOutputStream()

        assertThrows(IOException::class.java) {
            SimhMediaCopy.copy(
                input = ByteArrayInputStream(byteArrayOf(1, 2, 3, 4, 5)),
                output = output,
                maxBytes = 4
            )
        }
    }

    @Test
    fun rejectsEmptyInput() {
        assertThrows(IOException::class.java) {
            SimhMediaCopy.copy(
                input = ByteArrayInputStream(byteArrayOf()),
                output = ByteArrayOutputStream(),
                maxBytes = 10
            )
        }
    }

    @Test
    fun rejectsNonPositiveLimit() {
        assertThrows(IllegalArgumentException::class.java) {
            SimhMediaCopy.copy(
                input = ByteArrayInputStream(byteArrayOf(1)),
                output = ByteArrayOutputStream(),
                maxBytes = 0
            )
        }
    }
}
