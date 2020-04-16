package ir.arashjahani.nearplaces.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.arashjahani.nearplaces.data.model.Venue

/**
 * Created By ArashJahani on 2020/04/16
 */
@Dao
interface VenueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveVenues(venues:List<VenueDao>)

    @Query("SELECT * from venue")
    fun getVeues():List<Venue>

}