package ir.arashjahani.nearplaces.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Icon(
	val prefix: String? = null,
	val suffix: String? = null
): Parcelable
