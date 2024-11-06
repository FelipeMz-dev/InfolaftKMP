package infolaft.composeApp.data.database
/*
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import platform.Foundation.NSHomeDirectory

fun getDatabaseBuilder(): RoomDatabase.Builder<InfolaftDatabase> {
    val dbFile = NSHomeDirectory() + "/$DATABASE_NAME"
    return Room.databaseBuilder<InfolaftDatabase>(
        name = dbFile.absolutePath,
        factory = { InfolaftDatabase::class.instantiateImpl() }
    ).setDriver(BundledSQLiteDriver())
}*/