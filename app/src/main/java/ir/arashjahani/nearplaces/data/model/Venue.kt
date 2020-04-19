package ir.arashjahani.nearplaces.data.model

import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import ir.arashjahani.nearplaces.data.model.CategoriesItem
import ir.arashjahani.nearplaces.data.model.Location
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull

@Parcelize
@Entity(tableName = "venue")
data class Venue(

    @PrimaryKey(autoGenerate = true)
    @NotNull
    var _id:Long = 0,


    @SerializedName("id")
    @ColumnInfo(name="venue_id")
    var venueId: String?=null,
    var hasPerk: Boolean? = null,
    var referralId: String? = null,
    var name: String = "",

    @Embedded(prefix = "Loc_")
    var location: Location? = null,

    @Ignore
    var categories: List<CategoriesItem>? = null
): Parcelable
