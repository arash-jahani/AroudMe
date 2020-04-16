package ir.arashjahani.nearplaces.data.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created By ArashJahani on 2020/04/16
 */

class VenueItem (

    @Embedded
    val venue: Venue,

    @Relation(parentColumn = "id", entityColumn = "venueId", entity = CategoriesItem::class)
    val categories: List<CategoriesItem?>? = null

)