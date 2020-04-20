package ir.arashjahani.nearplaces.utils

import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import java.lang.StringBuilder
import java.util.*

/**
 * Created By ArashJahani on 2020/04/18
 */

fun Context.isLocationEnabled(): Boolean {
    var locationManager: LocationManager =
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER
    )
}

fun Context.checkLocationPermission(): Boolean =
    this.checkCallingOrSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED


fun String.toLocation(): Location? {

    if (this.isNullOrEmpty()) {
        return null
    }
    val locList = this.split(",")
    if (locList.size != 2)
        return null

    val location = Location("provider")
    location.latitude = locList.first().toDouble()
    location.longitude = locList.last().toDouble()

    return location
}

fun String.toAddress(context: Context): String {

    val lat: Double = this.toLocation()?.latitude ?: 0.0
    val lng: Double = this.toLocation()?.longitude ?: 0.0

    val geocoder = Geocoder(context)
    try {
        val list = geocoder.getFromLocation(lat, lng, 1)
        var address=StringBuilder()
        address.append(list[0].featureName ?:"")
        address.append(" ")
        address.append(list[0].subAdminArea ?:"")
        address.append(" ")
        return list[0].getAddressLine(0)
    } catch (ex: Exception) {
        return this
    }
}


fun Double.locationRound(): Double {
    return Math.round(this * 100000.0) / 100000.0
}

