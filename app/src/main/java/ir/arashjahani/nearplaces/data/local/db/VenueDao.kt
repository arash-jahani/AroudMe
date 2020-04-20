package ir.arashjahani.nearplaces.data.local.db

import androidx.paging.DataSource
import androidx.room.*
import ir.arashjahani.nearplaces.data.model.CategoriesItem
import ir.arashjahani.nearplaces.data.model.VenueWithCategoryItem
import ir.arashjahani.nearplaces.data.model.Venue
import ir.arashjahani.nearplaces.data.model.api.VenueItem

/**
 * Created By ArashJahani on 2020/04/16
 */
@Dao
abstract class VenueDao {

    public fun insertAll(venueItems: List<VenueItem>) {
        var venues: ArrayList<Venue> = ArrayList<Venue>();
        venueItems.forEach { venueItem: VenueItem ->
            venueItem.venue.categories?.let {
                insertCategoriesForVenue(venueItem.venue, it)
            }
            venues.add(venueItem.venue)
        }
         _saveVenues(venues)
    }

    private fun insertCategoriesForVenue(venue: Venue, categoriesItem: List<CategoriesItem>) {
        categoriesItem.forEach {
            it.venueId = venue.venueId
        }
        _saveVenueCategories(categoriesItem);
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _saveVenues(venues: List<Venue>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _saveVenueCategories(categoriesItem: List<CategoriesItem>)

    @Transaction
    @Query("SELECT venue.*, category.* FROM venue INNER JOIN category ON venue.venue_id = category.venueId")
    abstract fun _loadVenues(): DataSource.Factory<Int, VenueWithCategoryItem>

    @Transaction
    @Query("DELETE FROM venue")
    abstract fun clearAllVenue()

    @Transaction
    @Query("DELETE FROM category")
    abstract fun clearAllCategory()
}