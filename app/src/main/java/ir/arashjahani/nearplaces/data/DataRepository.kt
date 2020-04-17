package ir.arashjahani.nearplaces.data

/**
 * Created By ArashJahani on 2020/04/17
 */
interface DataRepository {

    public fun getNearestVenues(location:String,accuracy:Int,limit:Int)

    public fun onClearResources()

}