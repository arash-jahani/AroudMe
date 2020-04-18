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

    @GET("venues/explore")
    fun getNearestVenue(
        @Query("ll") location: String,
        @Query("radius") radius: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int

    ): Single<ApiGeneralResponse<VenuesResponse>>
}