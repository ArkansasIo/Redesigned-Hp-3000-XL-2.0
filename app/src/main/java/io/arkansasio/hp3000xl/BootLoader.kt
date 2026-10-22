package io.arkansasio.hp3000xl

/**
 * Simulated boot loader (BIOS/firmware stage) for the HP 3000 XL recreation.
 *
 * This is NOT a real bootloader and it never touches device partitions or
 * firmware. It produces a scripted POST-style output and hands control to the
 * simulated kernel stage, purely for the boot splash experience.
 */
object BootLoader {

    data class Step(val label: String, val detail: String, val delayMs: Long)

    val NAME = "HP3K-BL"
    val VERSION = "0.2 (recreation, simulated)"

    /** POST-like sequence. Progress values are percentages 0..100. */
    fun postSequence(): List<Step> = listOf(
        Step("Power-on self test", "CPU / bus check", 250),
        Step("Memory test", "RAM map, okay", 400),
        Step("Display init", "colour LCD, okay", 200),
        Step("Input devices", "touch + keyboard, okay", 200),
        Step("Storage", "internal flash + SD slot, okay", 300),
        Step("Loading kernel image", "zImage-hp3000xl (simulated)", 350),
        Step("Verifying image", "checksum ok (simulated)", 250),
        Step("Starting kernel", "handing control to LinuxKernelSim", 200),
    )

    /** Device facts shown on the splash. Pulled from the real device where available. */
    data class Info(val board: String, val cores: Int, val memMb: Long, val screen: String)

    fun info(cores: Int, memMb: Long): Info = Info("HP 3000 XL (virtual)", cores, memMb, "640x480 colour")
}
