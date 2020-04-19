package ir.arashjahani.nearplaces.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ir.arashjahani.nearplaces.data.model.VenueListResult

/**
 * Created By ArashJahani on 2020/04/17
 */
interface DataRepository {

    public fun getNearestVenues(location:String): VenueListResult

    public fun saveLocation()

    public fun getLocation(): MutableLiveData<String>

    public fun onClearResources()

}