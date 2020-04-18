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

//    public fun getAllVenues(): List<Venue> {
//        var venueItems: List<VenueItem> = _loadVenues()
//        var venues: ArrayList<Venue> = ArrayList<Venue>(venueItems.size)
//
//        venueItems.forEach {
//            //it.venue.categories=
//            it.venue.categories = it.categories
//            venues.add(it.venue)
//        }
//        return venues
//    }

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
    @Query("SELECT * from venue")
    abstract fun _loadVenues(): DataSource.Factory<Int, VenueWithCategoryItem>

}