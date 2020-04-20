package ir.arashjahani.nearplaces.utils

/**
 * Created By ArashJahani on 2020/04/16
 */
object AppConstants {

    const val BASE_URL="https://api.foursquare.com/v2/"
    const val CLIENT_ID="1XQ24VJS2ACFQDJGNM2DIAWI1WFF2N34SANHLB34RNEPOIG2"
    const val CLIENT_SECRET="JGQS11LSICQQIEBNT3JVLT3AN05Q2ZPLUZTMI4QBVM1RTXYV"

    const val VERSION="20200414"

    const val PAGE_SIZE:Int = 40
    const val RADIUS:Int=500

    const val PERMISSION_ID=100

    const val DISATANCE:Int=250

    const val LOCATION_INTERVAL:Long=1000 * 45 //set the interval in which you want to get locations.
        const val LOCATION_FASTESTINTERVAL:Long=1000 * 30 //if a location is available sooner you can get it

    const val KEY_LOCATION= "location"
    const val KEY_LAST_OFFSET= "last_offset"
    const val KEY_VENUE="venue_item"



}