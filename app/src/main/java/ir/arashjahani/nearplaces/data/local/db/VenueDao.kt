package ir.arashjahani.nearplaces.data.local.db

import androidx.room.*
import ir.arashjahani.nearplaces.data.model.VenueItem
import ir.arashjahani.nearplaces.data.model.Venue

/**
 * Created By ArashJahani on 2020/04/16
 */
@Dao
interface VenueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveVenues(venues:List<Venue>)

    @Transaction
    @Query("SELECT * from venue")
    fun getVeues():List<VenueItem>

}