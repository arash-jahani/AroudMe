package ir.arashjahani.nearplaces.data.model.api

/**
 * Created By ArashJahani on 2020/04/16
 */
class ApiGeneralResponse<T>(

    val meta: Meta,
    val response: Response<T>

)
