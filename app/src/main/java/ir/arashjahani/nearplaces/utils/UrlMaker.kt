package ir.arashjahani.nearplaces.utils

import ir.arashjahani.nearplaces.data.model.Icon

/**
 * Created By ArashJahani on 2020/04/18
 */

fun Icon.getUrl(size:Int):String{
    return String.format("%s%d%s", this.prefix,size, this.suffix)
}