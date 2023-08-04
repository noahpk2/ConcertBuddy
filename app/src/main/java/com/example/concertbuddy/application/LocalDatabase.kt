package com.example.concertbuddy.application

import android.util.Log
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.Update
import com.example.concertbuddy.calendar.CalendarData
import com.example.concertbuddy.profile.Users
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import java.util.UUID


@Database(entities = [
    CalendarData.Event::class,
    CalendarData.Day::class, Users.User::class,
    Users.Friend::class], version = 1)
@TypeConverters(
    LocalDatabase.UUIDConverter::class,
    LocalDatabase.EventListConverter::class,
    LocalDatabase.UUIDListConverter::class)

abstract class LocalDatabase: RoomDatabase() {
    abstract fun eventDao(): EventDao
    abstract fun dayDao(): DayDao
    abstract fun userDao(): UserDao
    abstract fun friendDao(): FriendDao

    object UUIDConverter{
        @TypeConverter
        @JvmStatic
        fun toUUID(uuid: String): UUID{
            return UUID.fromString(uuid)
        }
        @TypeConverter
        @JvmStatic
        fun fromUUID(uuid: UUID): String{
            return uuid.toString()
        }
    }

    object EventListConverter {
        private val gson = Gson()

        @TypeConverter
        @JvmStatic
        fun fromEventList(events: List<CalendarData.Event>): String {
            return gson.toJson(events)
        }

        @TypeConverter
        @JvmStatic
        fun toEventList(eventsString: String): List<CalendarData.Event> {
            val listType = object : TypeToken<List<CalendarData.Event>>() {}.type
            return gson.fromJson(eventsString, listType)
        }
    }

    object UUIDListConverter {

        @TypeConverter
        @JvmStatic
        fun fromUUIDList(uuids: List<UUID>): String {
            return uuids.joinToString(",")
        }

        @TypeConverter
        @JvmStatic
        fun toUUIDList(uuidsString: String): List<UUID> {
            return uuidsString.split(",").map { UUID.fromString(it.trim()) }
        }
    }



}


@Dao
interface DayDao{
    @Insert
    suspend fun insertDay(day: CalendarData.Day): Long // returns the row id of the inserted day, or -1 if failed
    @Query("SELECT * FROM days")
    suspend fun getAllDays(): List<CalendarData.Day>

    @Query("SELECT * FROM days WHERE date = :date")
    suspend fun getDayByDate(date: String): CalendarData.Day? {
        val days = getAllDays()
        for (day in days) {
            if (day.date == date) {
                return day
            }
        }
        return null
    }

    @Query("SELECT * FROM days WHERE day_id = :id")
    suspend fun getDayById(id: UUID): CalendarData.Day? {
        val days = getAllDays()
        for (day in days) {
            if (day.day_id == id) {
                return day
            }
        }
        return null
    }
}
@Dao
interface EventDao{
    @Insert
    suspend fun insertEvent(event: CalendarData.Event): Long
    // returns the row id of the inserted event, or -1 if failed

    @Query("SELECT * FROM events WHERE date = :date")
    suspend fun getEventsForDay(date:String): List<CalendarData.Event>

    @Query("SELECT * FROM events ORDER BY date ASC")
    suspend fun getAllEvents(): MutableList<CalendarData.Event>

    //delete duplicate events by comparing the event name and date
    @Query("DELETE FROM events WHERE event_id NOT IN (SELECT MIN(event_id) FROM events GROUP BY title, date)")
    suspend fun deleteDuplicateEvents()




}

@Dao
interface UserDao{
 @Insert(onConflict = OnConflictStrategy.REPLACE) //you'd call this when you register/login
 fun insertUser(user: Users.User): Long // returns the row id of the inserted user, or -1 if failed

 @Query("SELECT * FROM users WHERE user_id = :id") // i dont think you'd ever call this
 fun getUser(id: UUID): Users.User?

 @Update                           //you'd call this when you update the user's info
 fun updateUser(user: Users.User)

 @Delete                            //you'd call this on logout
 fun deleteUser(user: Users.User)

 @Query ("DELETE FROM users")
    fun deleteAllUsers()  // call this on login or register to clear the database (only one user at a time)
}

@Dao
interface FriendDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE) // this is called when a user adds a friend and is accepted
    fun insertFriend(friend: Users.Friend): Long // returns the row id of the inserted friend, or -1 if failed

    @Query("SELECT * FROM friends WHERE user_id = :id") // youd call this from search
    fun getFriend(id: UUID): Users.Friend?

    @Update                           //you'd call this periodically to update the user's friends info
    fun updateFriend(friend: Users.Friend)

    @Query("SELECT * FROM friends")  // call this to display friends list
    fun getAllFriends(): List<Users.Friend>


    @Delete                            //you'd call this on logout, or when a user removes a friend
    fun deleteFriend(friend: Users.Friend)

    @Delete
    fun deleteAllFriends(friends: List<Users.Friend>) // you'd call this on logout (we dont want to persist any data that could be sensitive)
}