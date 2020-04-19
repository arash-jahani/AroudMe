package ir.arashjahani.nearplaces.data

import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import ir.arashjahani.nearplaces.data.local.db.VenueDao
import ir.arashjahani.nearplaces.data.local.shared.PreferencesHelper
import ir.arashjahani.nearplaces.data.model.VenueListResult
import ir.arashjahani.nearplaces.data.remote.ApiService
import ir.arashjahani.nearplaces.data.utils.VenueBoundaryCondition
import ir.arashjahani.nearplaces.utils.AppConstants
import ir.arashjahani.nearplaces.utils.AppConstants.KEY_LOCATION
import ir.arashjahani.nearplaces.utils.AppConstants.PAGE_SIZE
import ir.arashjahani.nearplaces.utils.checkLocationPermission
import ir.arashjahani.nearplaces.utils.isLocationEnabled
import javax.inject.Inject

/**
 * Created By ArashJahani on 2020/04/17
 */
class DataRepositoryImpl @Inject constructor(
    private val context: Context,
    private val mApiService: ApiService,
    private val mVenueDAO: VenueDao,
    private val sharedPreferencesHelper: PreferencesHelper,
    private val locationRequest: LocationRequest
) : DataRepository {

    val boundaryCallback by lazy {
        VenueBoundaryCondition(mApiService, mVenueDAO,sharedPreferencesHelper)
    }

    val newLocationLiveData = MutableLiveData<String>()

    override fun getNearestVenues(location: String): VenueListResult {

        val networkErrors = boundaryCallback.networkErrors

        boundaryCallback.location = location

        // Get the paged list
        val data = LivePagedListBuilder(mVenueDAO._loadVenues(), PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        // Get the network errors exposed by the boundary callback
        return VenueListResult(data, networkErrors)

    }

    override
    fun fetchLocation() {
        /*
         * One time location request
         */
        if (context.isLocationEnabled() && context.checkLocationPermission()) {

            LocationServices.getFusedLocationProviderClient(context)
                .requestLocationUpdates(
                    locationRequest, object : LocationCallback() {
                        override fun onLocationResult(p0: LocationResult?) {

                            var loc: String =
                                "${p0!!.lastLocation.latitude}, ${p0.lastLocation.longitude}"
                            newLocationLiveData.postValue(loc)

                        }
                    },
                    Looper.myLooper()
                )

//            LocationServices.getFusedLocationProviderClient(context)
//                ?.lastLocation
//                ?.addOnSuccessListener { location: android.location.Location? ->
//                    if (location != null) {
//                        var loc: String = "${location.latitude}, ${location.longitude}"
//                        sharedPreferencesHelper.putString(AppConstants.KEY_LOCATION, loc)
//                        Log.v("Location Finder", loc)
//
//                    }
//                }
        }
    }

    override fun getLiveLocation(): MutableLiveData<String> {
        return newLocationLiveData
    }

    override fun getLastSavedLocation(): String {
        return sharedPreferencesHelper.getString(KEY_LOCATION)
    }

    override fun savedLocation(location: String) {
        sharedPreferencesHelper.putString(KEY_LOCATION, location)
    }

    override fun onClearResources() {
        boundaryCallback.onClear()
    }

}