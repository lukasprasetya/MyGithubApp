package com.example.mygithubapp.helper

import android.database.Cursor
import com.example.mygithubapp.db.DatabaseContract
import com.example.mygithubapp.entity.Favorite
import java.util.ArrayList

object MappingHelper {

    fun mapCursorToArrayList(favoriteCursor: Cursor?): ArrayList<Favorite>{
        val favoriteList = ArrayList<Favorite>()

        favoriteCursor?.apply {
            while (moveToNext()){
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.NAME))
                val photo = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.PHOTO))
                val location = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.LOCATION))
                val company = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.COMPANY))
                val repository = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.REPOSITORY))
                val favorite = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.FAVORITE))
                favoriteList.add(
                    Favorite(
                        username,
                        name,
                        photo,
                        location,
                        company,
                        repository,
                        favorite
                    )
                )
            }
        }
        return favoriteList
    }
}