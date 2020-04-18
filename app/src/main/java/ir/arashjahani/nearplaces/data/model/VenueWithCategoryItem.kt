package ir.arashjahani.nearplaces.data.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created By ArashJahani on 2020/04/16
 */

class VenueWithCategoryItem (

    @Embedded
    var venue: Venue,

    @Relation(parentColumn = "_id", entityColumn = "venueId", entity = CategoriesItem::class)
    val categories: List<CategoriesItem>? = null

)