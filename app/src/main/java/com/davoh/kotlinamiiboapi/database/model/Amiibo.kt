package com.davoh.kotlinamiiboapi.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.davoh.kotlinamiiboapi.vo.Resource
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Amiibo(
    @SerializedName("head")
    val head: String = "",
    @SerializedName("tail")
    val tail: String = "",
    @SerializedName("amiiboSeries")
    val amiiboSeries: String = "",
    @SerializedName("character")
    val character: String = "",
    @SerializedName("gameSeries")
    val gameSeries: String = "",
    @SerializedName("image")
    val image: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("type")
    val type: String = ""
) : Parcelable

data class AmiiboList(
    @SerializedName("amiibo")
    val amiiboList:List<Amiibo>
)

@Entity(tableName = "amiibos_entity")
data class AmiiboEntity(
    @PrimaryKey
    val amiiboId: String,
    @ColumnInfo(name="amiibo_head")
    val head: String = "",
    @ColumnInfo(name="amiibo_tail") //asi es como lo buscara en el json
    val tail: String = "",
    @ColumnInfo(name="amiibo_series")
    val amiiboSeries: String = "",
    @ColumnInfo(name="amiibo_character")
    val character:String = "",
    @ColumnInfo(name="amiibo_gameSeries")
    val gameSeries: String = "",
    @ColumnInfo(name="amiibo_image")
    val image:String ="",
    @ColumnInfo(name="amiibo_name")
    val name:String = "",
    @ColumnInfo(name="amiibo_type")
    val type:String = ""
)

fun List<AmiiboEntity>.asFavoriteAmiiboList(): List<Amiibo> = this.map {
    Amiibo(it.head, it.tail, it.amiiboSeries, it.character, it.gameSeries, it.image, it.name, it.type)
}

fun Amiibo.asAmiiboEntity(): AmiiboEntity =
    AmiiboEntity(this.head+ this.tail,this.head, this.tail, this.amiiboSeries, this.character, this.gameSeries,
    this.image, this.name, this.type)
