package ir.arashjahani.nearplaces.data.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity
data class Venue(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @SerializedName("id")
	@ColumnInfo(name = "venue_id")
    val venueId: String? = null,
    val hasPerk: Boolean? = null,
    val referralId: String? = null,
    val name: String? = null,

	@Embedded(prefix = "Loc_")
    val location: Location? = null,

	@Relation(parentColumn = "id", entityColumn = "v_id", entity = CategoriesItem::class)
    val categories: List<CategoriesItem?>? = null
)
