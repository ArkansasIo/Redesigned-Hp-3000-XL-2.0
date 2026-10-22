package io.arkansasio.hp3000xl

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/** Independent CE-style PIM/settings database backed by Android SQLite. */
class CeDatabase(context: Context) : SQLiteOpenHelper(context, "hp3000xl.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE settings (key TEXT PRIMARY KEY, value TEXT NOT NULL)")
        db.execSQL("CREATE TABLE contacts (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, phone TEXT, email TEXT)")
        db.execSQL("CREATE TABLE calendar_events (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, event_time TEXT NOT NULL, notes TEXT)")
        db.execSQL("CREATE TABLE notes (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, body TEXT NOT NULL, updated_at INTEGER NOT NULL)")
        db.execSQL("CREATE TABLE favorites (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, target TEXT NOT NULL)")
        db.execSQL("CREATE TABLE installed_apps (id TEXT PRIMARY KEY, name TEXT NOT NULL, version TEXT NOT NULL, enabled INTEGER NOT NULL DEFAULT 1)")
        seedDefaults(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Schema migrations are intentionally versioned for future releases.
    }

    private fun seedDefaults(db: SQLiteDatabase) {
        putSetting(db, "theme", "classic-blue")
        putSetting(db, "sound", "true")
        putSetting(db, "firstRun", "true")
    }

    fun putSetting(key: String, value: String) {
        writableDatabase.use { putSetting(it, key, value) }
    }

    private fun putSetting(db: SQLiteDatabase, key: String, value: String) {
        db.insertWithOnConflict("settings", null, ContentValues().apply {
            put("key", key)
            put("value", value)
        }, SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun getSetting(key: String, fallback: String? = null): String? =
        readableDatabase.query("settings", arrayOf("value"), "key = ?", arrayOf(key), null, null, null).use { c ->
            if (c.moveToFirst()) c.getString(0) else fallback
        }

    fun addContact(name: String, phone: String = "", email: String = ""): Long =
        writableDatabase.insert("contacts", null, ContentValues().apply {
            put("name", name); put("phone", phone); put("email", email)
        })

    fun addNote(title: String, body: String): Long =
        writableDatabase.insert("notes", null, ContentValues().apply {
            put("title", title); put("body", body); put("updated_at", System.currentTimeMillis())
        })
}
