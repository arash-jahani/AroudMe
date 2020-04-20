package ir.arashjahani.nearplaces

import android.location.Location
import ir.arashjahani.nearplaces.utils.toLocation
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created By ArashJahani on 2020/04/20
 */
class LocationConvertorTest {

    @Test
    fun isCorrect(){

        val location=Location("p")
        location.latitude=31.3434
        location.longitude=51.1298

        assertEquals("31.3434,51.1298".toLocation()?.latitude,location.latitude)
        assertEquals("31.3434,51.1298".toLocation()?.longitude,location.longitude)

    }

}