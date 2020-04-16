package ir.arashjahani.nearplaces.data.remote

import io.reactivex.Single
import ir.arashjahani.nearplaces.data.model.api.ApiGeneralResponse
import ir.arashjahani.nearplaces.data.model.api.VenuesResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created By ArashJahani on 2020/04/16
 */
interface ApiService {

    @GET("venues/search")
    fun getNearestVenue(
        @Query("||") location: String,
        @Query("llAcc") accuracy: Int,
        @Query("limit") limit: Int
    ): Single<ApiGeneralResponse<VenuesResponse>>
}