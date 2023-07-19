package com.example.concertbuddy.application

import androidx.room.DatabaseConfiguration
import androidx.room.Entity
import androidx.room.InvalidationTracker
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.concertbuddy.events.Event
import java.util.UUID


@Entity
class LocalDatabase: RoomDatabase() {

    @PrimaryKey
    private lateinit var user_id: UUID
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var attendingEvents: List<Event>
    private lateinit var token : String // TODO: Implement token


    override fun clearAllTables() {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }

    override fun createOpenHelper(config: DatabaseConfiguration): SupportSQLiteOpenHelper {
        TODO("Not yet implemented")
    }
}