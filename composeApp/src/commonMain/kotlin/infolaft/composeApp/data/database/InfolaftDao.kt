package infolaft.composeApp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import infolaft.composeApp.data.SessionEntity
import kotlinx.coroutines.flow.Flow

/*
@Dao
interface InfolaftDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNewSession(session: SessionEntity)

    @Query("SELECT * FROM SessionEntity WHERE active = 1 LIMIT 1")
    fun fetchActiveSession(): Flow<SessionEntity?>
}*/