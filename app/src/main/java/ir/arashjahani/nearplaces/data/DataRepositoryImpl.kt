package ir.arashjahani.nearplaces.data

import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import com.google.android.gms.location.*
import ir.arashjahani.nearplaces.data.local.db.VenueDao
import ir.arashjahani.nearplaces.data.local.shared.PreferencesHelper
import ir.arashjahani.nearplaces.data.model.VenueListResult
import ir.arashjahani.nearplaces.data.remote.ApiService
import ir.arashjahani.nearplaces.data.utils.VenueBoundaryCondition
import ir.arashjahani.nearplaces.utils.AppConstants.KEY_LAST_OFFSET
import ir.arashjahani.nearplaces.utils.AppConstants.KEY_LOCATION
import ir.arashjahani.nearplaces.utils.AppConstants.PAGE_SIZE
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
        VenueBoundaryCondition(mApiService, mVenueDAO, sharedPreferencesHelper)
    }

    val newLocationLiveData = MutableLiveData<String>()
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    override fun getNearestVenues(location: String): VenueListResult {

        val networkErrors = boundaryCallback.networkErrors

        boundaryCallback.freshRequest(location)
        // Get the paged list
        val data = LivePagedListBuilder(mVenueDAO._loadVenues(), PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        // Get the network errors exposed by the boundary callback
        return VenueListResult(data, networkErrors)

    }

    override
    fun fetchLocation() {

        if (this::fusedLocationProviderClient.isInitialized) {
            fusedLocationProviderClient?.removeLocationUpdates(mLocationCallback)
        }

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context);

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    var mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {

            var loc: String =
                "${p0!!.lastLocation.latitude}, ${p0.lastLocation.longitude}"
            Log.v("Location Finder:", "fetchLocation")
            newLocationLiveData.postValue(loc)

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

    override fun clearAllVenues() {
        sharedPreferencesHelper.putInt(KEY_LAST_OFFSET, 1)
        mVenueDAO.clearAllVenue()
        mVenueDAO.clearAllCategory()

    }

    override fun onClearResources() {
        boundaryCallback.onClear()
    }

    override fun stopLocationTracked() {
        if (this::fusedLocationProviderClient.isInitialized)
            fusedLocationProviderClient.removeLocationUpdates(mLocationCallback)
    }

}