package infolaft.composeApp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import infolaft.composeApp.data.SessionEntity

const val DATABASE_NAME = "infolaft_database"

/*
@Database(entities = [SessionEntity::class], version = 1)
abstract class InfolaftDatabase: RoomDatabase() {
    abstract fun infolaftDao(): InfolaftDao

}*/