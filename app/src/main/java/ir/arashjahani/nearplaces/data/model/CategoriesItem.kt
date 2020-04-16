package ir.arashjahani.nearplaces.data.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.google.gson.annotations.SerializedName

@Entity
data class CategoriesItem(

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    @SerializedName("id")
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
        parentColumns = ["id"],
        childColumns = ["venueId"],
        onDelete = CASCADE
    )
    var venueId: Long? = null
)
