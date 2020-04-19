package ir.arashjahani.nearplaces.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat

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

