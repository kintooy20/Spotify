package com.gimenez.kent.spotify.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Kent on 1/22/2018.
 */

data class SongModel(var Name:String="",var  Singer:String="", var Album: String="", var mSongPath: String=""): Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Name)
        parcel.writeString(Singer)
        parcel.writeString(Album)
        parcel.writeString(mSongPath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SongModel> {
        override fun createFromParcel(parcel: Parcel): SongModel {
            return SongModel(parcel)
        }

        override fun newArray(size: Int): Array<SongModel?> {
            return arrayOfNulls(size)
        }
    }
}