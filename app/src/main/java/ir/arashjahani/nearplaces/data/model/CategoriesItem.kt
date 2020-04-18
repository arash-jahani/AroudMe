package ir.arashjahani.nearplaces.data.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.google.gson.annotations.SerializedName

@Entity
data class CategoriesItem(

    @PrimaryKey(autoGenerate = true)
    var _id: Long? = null,

    @SerializedName("id")
    @ColumnInfo(name="category_id")
    var categoryId: String? = null,
    var pluralName: String? = null,
    var name: String? = null,
    @Embedded
    var icon: Icon? = null,
    var shortName: String? = null,
    var primary: Boolean? = null,


    @ForeignKey
        (
        entity = Venue::class,
        parentColumns = ["_id"],
        childColumns = ["venueId"],
        onDelete = CASCADE
    )
    var venueId: Long? = null
)
