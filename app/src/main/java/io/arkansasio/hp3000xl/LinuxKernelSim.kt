package io.arkansasio.hp3000xl

/**
 * Simulated Linux kernel boot for the HP 3000 XL recreation.
 *
 * This is NOT a real Linux kernel. It is a scripted, text-only reenactment of
 * the kind of messages a Linux kernel prints while bringing up an ARM
 * handheld, so the boot loader / splash experience feels authentic.
 * No kernel code, modules, or images are loaded or executed.
 */
object LinuxKernelSim {

    data class Line(val text: String, val delayMs: Long)

    val version: String = "Linux version 6.1.0-hp3000xl (simulated) #1 SMP"

    /** Typical early-boot console output, in order. */
    fun bootLog(device: String, memKb: Long, cores: Int): List<Line> = listOf(
        Line("[    0.000000] Booting Linux on physical CPU 0x0", 60),
        Line(version, 40),
        Line("[    0.000000] CPU: ARMv8 Processor rev 0 (v8l) — $cores cores detected", 50),
        Line("[    0.004000] Memory: ${memKb / 1024}MB available (simulated map)", 70),
        Line("[    0.010000] Console: colour dummy device 80x25", 40),
        Line("[    0.050000] Calibrating delay loop (skipped, simulated timer) fine..", 90),
        Line("[    0.120000] pinctrl core: initialized pinctrl subsystem", 45),
        Line("[    0.180000] NET: Registered protocol family 16", 40),
        Line("[    0.230000] DMA: preallocated 256 KiB pool for atomic coherent allocations", 55),
        Line("[    0.310000] SCSI subsystem initialized", 45),
        Line("[    0.400000] i2c-core: bus 0 registered (simulated)", 50),
        Line("[    0.520000] mmc0: SDHCI controller on simulated-host [mmc0] at 0x0", 60),
        Line("[    0.700000] mmc0: new SD card at address abcd", 80),
        Line("[    0.760000] mmcblk0: p1 p2 (root filesystem found)", 70),
        Line("[    0.900000] input: HP 3000 XL touchscreen as /devices/virtual/input/input0", 60),
        Line("[    1.050000] rtc: simulated real-time clock registered", 50),
        Line("[    1.200000] EXT4-fs (mmcblk0p2): mounted filesystem with ordered data mode", 90),
        Line("[    1.350000] VFS: Mounted root (ext4 filesystem) readonly on device 179:2", 60),
        Line("[    1.500000] Freeing unused kernel memory: 512K", 70),
        Line("[    1.620000] Run /sbin/init as init process", 80),
    )

    fun initLog(): List<Line> = listOf(
        Line("init: reading /etc/inittab (simulated)", 60),
        Line("init: mounting virtual filesystems (proc, sysfs, devtmpfs)", 80),
        Line("init: starting system services", 100),
        Line("init: starting hp3000xl-desktop ...", 120),
        Line("hp3000xl-desktop: shell starting, handing off to CE-style UI", 150),
    )

    /** Rough plausible device stats used to decorate the boot log. */
    fun runtimeStats(cores: Int, memKb: Long): Triple<String, Int, Long> =
        Triple("hp3000xl-board", cores.coerceAtLeast(1), memKb)
}
