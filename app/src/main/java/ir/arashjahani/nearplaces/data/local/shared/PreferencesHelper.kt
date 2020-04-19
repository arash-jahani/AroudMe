package ir.arashjahani.nearplaces.data.local.shared

import android.content.SharedPreferences
import org.jetbrains.annotations.NotNull
import javax.inject.Inject

/**
 * Created By ArashJahani on 2020/04/18
 */
class PreferencesHelper @Inject constructor (private val sharedPreferences: SharedPreferences) {

    fun putString(@NotNull key:String,@NotNull value:String){
        sharedPreferences.edit().putString(key,value).apply()
    }

    fun getString(@NotNull key:String):String{
        return sharedPreferences.getString(key,"")
    }

    fun putInt(@NotNull key:String,@NotNull value:Int){
        sharedPreferences.edit().putInt(key,value).apply()
    }

    fun getInt(@NotNull key:String):Int{
        return sharedPreferences.getInt(key,1)
    }

}