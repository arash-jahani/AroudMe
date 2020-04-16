package ir.arashjahani.nearplaces.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.arashjahani.nearplaces.data.model.CategoriesItem
import ir.arashjahani.nearplaces.data.model.Venue

/**
 * Created By ArashJahani on 2020/04/16
 */

@Database(entities = arrayOf(
    Venue::class,
    CategoriesItem::class),version = 1)
abstract class AppDatabase:RoomDatabase() {

abstract fun venueDao():VenueDao

}