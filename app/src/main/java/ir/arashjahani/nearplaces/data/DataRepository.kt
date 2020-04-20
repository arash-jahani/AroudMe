package ir.arashjahani.nearplaces.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ir.arashjahani.nearplaces.data.model.VenueListResult

/**
 * Created By ArashJahani on 2020/04/17
 */
interface DataRepository {

    public fun getNearestVenues(location:String): VenueListResult

    public fun trackLocation()

    public fun getLiveLocation(): MutableLiveData<String>

    public fun getLastSavedLocation():String

    public fun savedLocation(location:String)

    public fun clearAllVenues()

    public fun onClearResources()

    public fun stopLocationTracked()
}