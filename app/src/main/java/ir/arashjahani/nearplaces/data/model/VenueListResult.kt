package ir.arashjahani.nearplaces.data.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

/**
 * Created By ArashJahani on 2020/04/18
 */

data class VenueListResult(

    val data: LiveData<PagedList<Venue>>,
    val networkErrors: LiveData<String>

)