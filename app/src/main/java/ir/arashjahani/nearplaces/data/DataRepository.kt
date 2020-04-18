package ir.arashjahani.nearplaces.data

import ir.arashjahani.nearplaces.data.model.VenueListResult

/**
 * Created By ArashJahani on 2020/04/17
 */
interface DataRepository {

    public fun getNearestVenues(location:String): VenueListResult

    public fun onClearResources()

}