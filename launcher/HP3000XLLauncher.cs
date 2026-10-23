// HP 3000 XL 2.0 — Windows launcher executable
//
// Builds and runs the HP 3000 XL Android app on a connected device or emulator.
// Compiled with the .NET Framework 4 C# compiler that ships with Windows:
//   csc /target:winexe /out:output\HP3000XL-2.0.exe launcher\HP3000XLLauncher.cs
//
// What it does, in order:
//   1. Locates adb (ANDROID_HOME env var or C:\Android\android-sdk).
//   2. Checks for a connected device; if none, lists available AVDs and
//      boots the first one.
//   3. Builds the debug APK with Gradle if the APK in output\ is missing.
//   4. Installs the APK and launches the main activity.
//   5. Shows a live status window with adb logcat output from the app.

using System;
using System.Diagnostics;
using System.IO;
using System.Threading;
using System.Windows.Forms;

namespace HP3000XL
{
    public class Launcher : Form
    {
        private readonly TextBox log;
        private readonly Button runButton;
        private string adb;
        private string projectDir;

        public Launcher()
        {
            Text = "HP 3000 XL 2.0 — Launcher";
            Width = 720;
            Height = 480;
            StartPosition = FormStartPosition.CenterScreen;

            log = new TextBox
            {
                Multiline = true,
                ReadOnly = true,
                ScrollBars = ScrollBars.Vertical,
                Dock = DockStyle.Fill,
                Font = new System.Drawing.Font("Consolas", 9f),
                BackColor = System.Drawing.Color.FromArgb(12, 12, 20),
                ForeColor = System.Drawing.Color.FromArgb(180, 255, 180),
            };

            runButton = new Button
            {
                Text = "Build & Run",
                Dock = DockStyle.Bottom,
                Height = 40,
            };
            runButton.Click += (s, e) => { runButton.Enabled = false; RunAll(); };

            var header = new Label
            {
                Text = "HP 3000 XL 2.0 Color Mobile Edition — build, install and run on device",
                Dock = DockStyle.Top,
                Height = 28,
                TextAlign = System.Drawing.ContentAlignment.MiddleCenter,
            };

            Controls.Add(log);
            Controls.Add(runButton);
            Controls.Add(header);
        }

        private void Say(string line)
        {
            BeginInvoke((MethodInvoker)delegate
            {
                log.AppendText(line + Environment.NewLine);
            });
        }

        private int Run(string file, string args, bool showOutput = true)
        {
            var psi = new ProcessStartInfo
            {
                FileName = file,
                Arguments = args,
                UseShellExecute = false,
                CreateNoWindow = true,
                RedirectStandardOutput = true,
                RedirectStandardError = true,
                WorkingDirectory = projectDir,
            };
            using (var p = Process.Start(psi))
            {
                p.OutputDataReceived += (s, e) => { if (showOutput && e.Data != null) Say(e.Data); };
                p.ErrorDataReceived += (s, e) => { if (showOutput && e.Data != null) Say(e.Data); };
                p.BeginOutputReadLine();
                p.BeginErrorReadLine();
                p.WaitForExit();
                return p.ExitCode;
            }
        }

        private void RunAll()
        {
            new Thread(delegate()
            {
                try
                {
                    RunAllInner();
                }
                catch (Exception ex)
                {
                    Say("ERROR: " + ex.Message);
                }
                BeginInvoke((MethodInvoker)delegate { runButton.Enabled = true; });
            }).Start();
        }

        private void RunAllInner()
        {
            projectDir = AppDomain.CurrentDomain.BaseDirectory;
            while (!File.Exists(Path.Combine(projectDir, "settings.gradle.kts")) &&
                   projectDir.Length > 3)
                projectDir = Path.GetFullPath(Path.Combine(projectDir, ".."));
            Say("Project: " + projectDir);

            string sdk = Environment.GetEnvironmentVariable("ANDROID_HOME");
            if (string.IsNullOrEmpty(sdk) || !Directory.Exists(sdk)) sdk = @"C:\Android\android-sdk";
            adb = Path.Combine(sdk, "platform-tools", "adb.exe");
            if (!File.Exists(adb))
            {
                Say("adb.exe not found under " + sdk);
                return;
            }

            // 1. device check
            var devices = Run2(adb, "devices");
            var lines = devices.Split(new[] { '\n' }, StringSplitOptions.RemoveEmptyEntries);
            bool hasDevice = false;
            foreach (var l in lines)
                if (l.Contains("\tdevice")) hasDevice = true;

            if (!hasDevice)
            {
                Say("No device attached — looking for an emulator to boot...");
                var emulator = Path.Combine(sdk, "emulator", "emulator.exe");
                string avd = null;
                if (File.Exists(emulator))
                {
                    var avds = Run2(emulator, "-list-avds");
                    foreach (var name in avds.Split(new[] { '\n', '\r' }, StringSplitOptions.RemoveEmptyEntries))
                    {
                        if (avd == null) { avd = name.Trim(); }
                    }
                }
                if (avd == null)
                {
                    Say("No AVDs found and no device attached. Connect a device with USB debugging, or create an emulator in Android Studio.");
                    return;
                }
                Say("Booting emulator: " + avd);
                Process.Start(new ProcessStartInfo
                {
                    FileName = emulator,
                    Arguments = "-avd " + avd,
                    UseShellExecute = false,
                    CreateNoWindow = true,
                });
                // wait for boot
                for (int i = 0; i < 120; i++)
                {
                    Thread.Sleep(2000);
                    var boot = Run2(adb, "shell getprop sys.boot_completed").Trim();
                    if (boot == "1") { Say("Emulator booted."); break; }
                    if (i % 5 == 0) Say("Waiting for emulator boot... (" + (i * 2) + "s)");
                }
            }
            else
            {
                Say("Device found.");
            }

            // 2. build if the APK is missing
            string apk = Path.Combine(projectDir, "output", "HP3000XL-2.0-debug.apk");
            if (!File.Exists(apk))
            {
                Say("APK missing — running Gradle assembleDebug...");
                string gradle = FindGradle();
                Run(gradle, "assembleDebug --console=plain");
                string built = Path.Combine(projectDir, "app", "build", "outputs", "apk", "debug", "app-debug.apk");
                if (!File.Exists(built))
                {
                    Say("Build failed — no APK produced.");
                    return;
                }
                Directory.CreateDirectory(Path.GetDirectoryName(apk));
                File.Copy(built, apk, true);
                Say("APK copied to output\\HP3000XL-2.0-debug.apk");
            }

            // 3. install + launch
            Say("Installing APK...");
            Run(adb, "install -r \"" + apk + "\"");
            Say("Launching app...");
            Run(adb, "shell am start -n io.arkansasio.hp3000xl/.MainActivity", false);
            Say("Started io.arkansasio.hp3000xl/.MainActivity");

            // 4. live logcat
            Say("--- live device log (Ctrl+C or close window to stop) ---");
            Run(adb, "logcat -d -s HP3K AndroidRuntime System.out");
        }

        private string Run2(string file, string args)
        {
            var psi = new ProcessStartInfo
            {
                FileName = file,
                Arguments = args,
                UseShellExecute = false,
                CreateNoWindow = true,
                RedirectStandardOutput = true,
                RedirectStandardError = true,
            };
            using (var p = Process.Start(psi))
            {
                string outp = p.StandardOutput.ReadToEnd();
                p.StandardError.ReadToEnd();
                p.WaitForExit();
                return outp;
            }
        }

        private string FindGradle()
        {
            string wrapper = Path.Combine(projectDir, "gradlew.bat");
            if (File.Exists(wrapper)) return wrapper;
            // fall back to the cached wrapper distribution used on this machine
            string dists = Path.Combine(
                Environment.GetFolderPath(Environment.SpecialFolder.UserProfile),
                ".gradle", "wrapper", "dists");
            if (Directory.Exists(dists))
            {
                string best = null;
                foreach (var dir in Directory.GetDirectories(dists))
                {
                    foreach (var g in Directory.GetFiles(dir, "gradle.bat", SearchOption.AllDirectories))
                        best = g;
                }
                if (best != null) return best;
            }
            return "gradle.bat";
        }

        [STAThread]
        public static void Main()
        {
            Application.EnableVisualStyles();
            Application.Run(new Launcher());
        }
    }
}
