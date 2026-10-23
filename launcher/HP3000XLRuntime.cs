// HP 3000 XL 2.0 — Windows Runtime Environment
//
// Runs the HP 3000 XL boot experience natively on Windows without the
// Android emulator: replays the same POST / kernel / init scripts as the
// APK (ported 1:1 from BootLoader.kt and LinuxKernelSim.kt), then shows
// a CE-style desktop shell with taskbar and Start menu.
//
//   csc /target:winexe /out:output\HP3000XLRuntime.exe launcher\HP3000XLRuntime.cs

using System;
using System.Collections.Generic;
using System.Drawing;
using System.Threading;
using System.Windows.Forms;

namespace HP3000XL
{
    // ---- boot scripts (ported from BootLoader.kt / LinuxKernelSim.kt) ----
    public static class BootScripts
    {
        public class Step { public string Label, Detail; public int DelayMs;
            public Step(string l, string d, int ms) { Label = l; Detail = d; DelayMs = ms; } }

        public static List<Step> Post()
        {
            return new List<Step>
            {
            new Step("Power-on self test", "CPU / bus check", 250),
            new Step("Memory test", "RAM map, okay", 400),
            new Step("Display init", "colour LCD, okay", 200),
            new Step("Input devices", "touch + keyboard, okay", 200),
            new Step("Storage", "internal flash + SD slot, okay", 300),
            new Step("Loading kernel image", "zImage-hp3000xl (simulated)", 350),
            new Step("Verifying image", "checksum ok (simulated)", 250),
                    new Step("Starting kernel", "handing control to LinuxKernelSim", 200),
                };
            }

            public static List<string[]> Kernel()
            {
                return new List<string[]>
                {
            new[]{"[    0.000000] Booting Linux on physical CPU 0x0"},
            new[]{"[    0.000000] Linux version 6.1.0-hp3000xl (simulated) #1 SMP"},
            new[]{"[    0.000000] CPU: ARMv8 Processor rev 0 (v8l) — " + Environment.ProcessorCount + " cores detected"},
            new[]{"[    0.010000] Console: colour dummy device 80x25"},
            new[]{"[    0.120000] pinctrl core: initialized pinctrl subsystem"},
            new[]{"[    0.180000] NET: Registered protocol family 16"},
            new[]{"[    0.400000] i2c-core: bus 0 registered (simulated)"},
            new[]{"[    0.520000] mmc0: SDHCI controller on simulated-host [mmc0] at 0x0"},
            new[]{"[    0.700000] mmc0: new SD card at address abcd"},
            new[]{"[    0.760000] mmcblk0: p1 p2 (root filesystem found)"},
            new[]{"[    0.900000] input: HP 3000 XL touchscreen as /devices/virtual/input/input0"},
            new[]{"[    1.200000] EXT4-fs (mmcblk0p2): mounted filesystem with ordered data mode"},
            new[]{"[    1.350000] VFS: Mounted root (ext4 filesystem) readonly on device 179:2"},
            new[]{"[    1.620000] Run /sbin/init as init process"},
            };
        }

        public static List<string[]> Init()
        {
            return new List<string[]>
            {
            new[]{"init: reading /etc/inittab (simulated)"},
            new[]{"init: mounting virtual filesystems (proc, sysfs, devtmpfs)"},
            new[]{"init: starting system services"},
            new[]{"init: starting hp3000xl-desktop ..."},
            new[]{"hp3000xl-desktop: shell starting, handing off to CE-style UI"},
            };
        }
    }

    public class Runtime : Form
    {
        private Panel stage;
        private TextBox console;
        private System.Windows.Forms.Timer clock;
        private readonly Label clockLabel;

        private static readonly string[] Programs = {
            "Pocket Word", "Pocket Excel", "Pocket Outlook", "Contacts",
            "Calendar", "Calculator", "Internet", "Terminal",
            "File Explorer", "Solitaire", "Minesweeper" };

        public Runtime()
        {
            Text = "HP 3000 XL 2.0 — Runtime Environment (Color Mobile Edition)";
            Width = 640; Height = 520;
            StartPosition = FormStartPosition.CenterScreen;
            BackColor = Color.FromArgb(200, 205, 210); // ce_silver desktop
            FormBorderStyle = FormBorderStyle.FixedSingle;
            MaximizeBox = false;

            // 640x480 stage (the device screen)
            stage = new Panel { Dock = DockStyle.Fill, BackColor = Color.FromArgb(200, 205, 210) };
            Controls.Add(stage);

            // taskbar
            var taskbar = new Panel { Dock = DockStyle.Bottom, Height = 34, BackColor = Color.FromArgb(18, 59, 99) };
            var start = new Button
            {
                Text = "START",
                Dock = DockStyle.Left,
                Width = 84,
                BackColor = Color.FromArgb(10, 34, 58),
                ForeColor = Color.White,
                FlatStyle = FlatStyle.Flat,
            };
            start.Click += (s, e) => ShowStartMenu();
            clockLabel = new Label
            {
                Dock = DockStyle.Right,
                Width = 74,
                TextAlign = ContentAlignment.MiddleCenter,
                ForeColor = Color.White,
                Text = DateTime.Now.ToString("HH:mm"),
            };
            taskbar.Controls.Add(start);
            taskbar.Controls.Add(clockLabel);
            Controls.Add(taskbar);

            clock = new System.Windows.Forms.Timer { Interval = 1000 };
            clock.Tick += (s, e) => { clockLabel.Text = DateTime.Now.ToString("HH:mm"); };
            clock.Start();

            RunBoot();
        }

        private void RunBoot()
        {
            console = new TextBox
            {
                Multiline = true,
                ReadOnly = true,
                ScrollBars = ScrollBars.Vertical,
                Dock = DockStyle.Fill,
                BorderStyle = BorderStyle.None,
                Font = new Font("Consolas", 9f),
                BackColor = Color.FromArgb(12, 12, 20),
                ForeColor = Color.FromArgb(180, 255, 180),
            };
            stage.Controls.Add(console);
            new Thread(delegate ()
            {
                Say("HP3K-BL 0.2 (recreation, simulated) — POST");
                foreach (var st in BootScripts.Post())
                {
                    Thread.Sleep(st.DelayMs);
                    Say("  " + st.Label + " ... " + st.Detail);
                }
                foreach (var l in BootScripts.Kernel()) { Thread.Sleep(60); Say(l[0]); }
                foreach (var l in BootScripts.Init()) { Thread.Sleep(80); Say(l[0]); }
                Thread.Sleep(400);
                BeginInvoke((MethodInvoker)delegate { ShowDesktop(); });
            }).Start();
        }

        private void Say(string line)
        {
            BeginInvoke((MethodInvoker)delegate { console.AppendText(line + Environment.NewLine); });
        }

        private void ShowDesktop()
        {
            stage.Controls.Clear();
            var title = new Label
            {
                Text = "HP 3000 XL — Color Mobile Edition",
                AutoSize = false,
                Bounds = new Rectangle(16, 12, 400, 28),
                Font = new Font("Segoe UI", 14f, FontStyle.Bold),
                ForeColor = Color.FromArgb(18, 59, 99),
            };
            var sub = new Label
            {
                Text = "Runtime environment — desktop shell ready",
                Bounds = new Rectangle(18, 42, 400, 22),
                ForeColor = Color.FromArgb(90, 100, 110),
            };
            var grid = new FlowLayoutPanel
            {
                Bounds = new Rectangle(16, 80, 590, 330),
                BackColor = Color.Transparent,
            };
            foreach (var name in Programs)
            {
                var b = new Button
                {
                    Text = name,
                    Width = 130,
                    Height = 46,
                    Margin = new Padding(8),
                    FlatStyle = FlatStyle.Flat,
                    BackColor = Color.White,
                    ForeColor = Color.FromArgb(18, 59, 99),
                };
                b.FlatAppearance.BorderColor = Color.FromArgb(150, 160, 170);
                b.Click += (s, e) => MessageBox.Show(this, name + " — app window placeholder.\n" +
                    "This runtime replays the shell; install the APK on a device for the full Compose UI.",
                    name, MessageBoxButtons.OK, MessageBoxIcon.Information);
                grid.Controls.Add(b);
            }
            stage.Controls.Add(title);
            stage.Controls.Add(sub);
            stage.Controls.Add(grid);
        }

        private void ShowStartMenu()
        {
            var menu = new ContextMenuStrip();
            foreach (var name in Programs) menu.Items.Add(name);
            menu.Items.Add(new ToolStripSeparator());
            menu.Items.Add("Control Panel");
            menu.Items.Add("Task Manager");
            menu.Items.Add("Help");
            menu.Show(this, new Point(0, Height - 34 - menu.Height * 2));
        }

        [STAThread]
        public static void Main()
        {
            Application.EnableVisualStyles();
            Application.Run(new Runtime());
        }
    }
}
