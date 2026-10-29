package io.arkansasio.hp3000xl.emulator

import android.content.ContentResolver
import android.net.Uri
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

/**
 * Copies a user-selected disk/tape image from Android's Storage Access Framework
 * into the private per-machine workspace. It never interprets media contents
 * and never executes files. The source URI must come from an explicit user action.
 */
class SimhMediaImporter(
    private val contentResolver: ContentResolver,
    private val runtime: SimhRuntime,
    private val maxBytes: Long = DEFAULT_MAX_BYTES
) {
    init {
        require(maxBytes > 0) { "Maximum media size must be positive." }
    }

    @Throws(IOException::class)
    fun importImage(source: Uri, machine: SimhMachine = SimhMachine.HP3000): ImportedSimhMedia {
        val directory = runtime.machineDataDirectory(machine)
        val safeDirectory = directory.canonicalFile
        val target = File(safeDirectory, "media-${UUID.randomUUID()}.img").canonicalFile
        require(target.parentFile == safeDirectory) { "Invalid media destination." }

        val input = contentResolver.openInputStream(source)
            ?: throw IOException("Android could not open the selected media file.")

        val copied: Long
        try {
            input.use { stream ->
                target.outputStream().buffered().use { output ->
                    copied = SimhMediaCopy.copy(stream, output, maxBytes)
                }
            }
        } catch (error: Exception) {
            target.delete()
            throw error
        }

        return ImportedSimhMedia(machine = machine, file = target, bytesCopied = copied)
    }

    companion object {
        const val DEFAULT_MAX_BYTES: Long = 8L * 1024L * 1024L * 1024L
    }
}

/** Bounded stream copy kept Android-independent so its edge cases can be unit tested. */
internal object SimhMediaCopy {
    @Throws(IOException::class)
    fun copy(input: InputStream, output: OutputStream, maxBytes: Long): Long {
        require(maxBytes > 0) { "Maximum media size must be positive." }
        var copied = 0L
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        while (true) {
            val count = input.read(buffer)
            if (count < 0) break
            if (count == 0) continue
            if (copied > maxBytes - count) {
                throw IOException("Selected media exceeds the configured import limit.")
            }
            output.write(buffer, 0, count)
            copied += count
        }
        if (copied == 0L) {
            throw IOException("The selected media file is empty.")
        }
        output.flush()
        return copied
    }
}

data class ImportedSimhMedia(
    val machine: SimhMachine,
    val file: File,
    val bytesCopied: Long
)
