package ir.arashjahani.nearplaces.data.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import ir.arashjahani.nearplaces.data.model.CategoriesItem
import ir.arashjahani.nearplaces.data.model.Location

@Entity
data class Venue(

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    @SerializedName("id")
    var venueId: String? = null,
    var hasPerk: Boolean? = null,
    var referralId: String? = null,
    var name: String? = null,

    @Embedded(prefix = "Loc_")
    var location: Location? = null,

    @Ignore
    var categories: List<CategoriesItem?>? = null
)
