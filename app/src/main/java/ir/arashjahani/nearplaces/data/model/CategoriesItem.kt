package ir.arashjahani.nearplaces.data.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(
    foreignKeys = [ForeignKey(
        entity = Venue::class,
        parentColumns = ["id"],
        childColumns = ["v_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CategoriesItem(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @SerializedName("id")
    val categoryId: String? = null,

    @ColumnInfo(name = "v_id") // actually it is auto generated id in Venue.class
    val venueId: Long? = null,
    val pluralName: String? = null,
    val name: String? = null,
    @Embedded
    val icon: Icon? = null,
    val shortName: String? = null,
    val primary: Boolean? = null
)
