package com.example.rk_app

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class ResponseData(
    val Response: String,
    //val RateLimit: String,
    val Type: Int,
    val Message: String,
    val HasWarning: Boolean,
    public val Data: Data
    )

data class Data(
    val Aggregated:Boolean,
    val TimeFrom:Int,
    val TimeTo:Int,
    @Json(name="Data") val DataList: List<DataListItem>
)

@Parcelize
data class DataListItem(
    @Transient
    var currency : String = "",
    val time:Int,
    val high: Double,
    val low: Double,
    val open:Double,
    val volumefrom:Double,
    val volumeto:Double,
    val close:Double,
    val conversionType:String,
    val conversionSymbol:String
) : Parcelable
