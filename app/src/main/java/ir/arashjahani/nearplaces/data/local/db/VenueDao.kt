package ir.arashjahani.nearplaces.data.local.db

import androidx.room.*
import ir.arashjahani.nearplaces.data.model.CategoriesItem
import ir.arashjahani.nearplaces.data.model.VenueItem
import ir.arashjahani.nearplaces.data.model.Venue

/**
 * Created By ArashJahani on 2020/04/16
 */
@Dao
abstract class VenueDao {

    public fun insertAll(venues: List<Venue>) {
        venues.forEach { venue: Venue ->
            venue.categories?.let {
                insertCategoriesForVenue(venue, it)
            }
        }
        _saveVenues(venues)
    }

    public fun getAllVenues(): List<Venue> {
        var venueItems: List<VenueItem> = _loadVenues()
        var venues: ArrayList<Venue> = ArrayList<Venue>(venueItems.size)

        venueItems.forEach {
            //it.venue.categories=
            it.venue.categories = it.categories
            venues.add(it.venue)
        }
        return venues
    }

    private fun insertCategoriesForVenue(venue: Venue, categoriesItem: List<CategoriesItem>) {
        categoriesItem.forEach {
            it.venueId = venue.id
        }
        _saveVenueCategories(categoriesItem);
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _saveVenues(venues: List<Venue>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _saveVenueCategories(categoriesItem: List<CategoriesItem>)

    @Transaction
    @Query("SELECT * from venue")
    abstract fun _loadVenues(): List<VenueItem>

}