package ir.arashjahani.nearplaces.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
	val cc: String? = null,
	val country: String? = null,
	val address: String? = null,
	val lng: Double? = null,
	val distance: Int? = null,
	val city: String? = null,
	val neighborhood: String? = null,
	val state: String? = null,
	val lat: Double? = null
): Parcelable
