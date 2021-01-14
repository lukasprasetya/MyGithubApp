package com.example.mygithubapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mygithubapp.db.DatabaseContract.FavColumns.Companion.TABLE_NAME
import com.example.mygithubapp.db.DatabaseContract.FavColumns

internal class DatabaseHelper(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

   companion object{

       private const val DATABASE_NAME = "dbFav"
       private const val DATABASE_VERSION = 1
       private const val SQL_CREATE_TABLE_FAV = "CREATE TABLE $TABLE_NAME" +
               "(${FavColumns.USERNAME} TEXT PRIMARY KEY NOT NULL," +
               "${FavColumns.NAME} TEXT NOT NULL," +
               "${FavColumns.PHOTO} TEXT NOT NULL," +
               "${FavColumns.LOCATION} TEXT NOT NULL," +
               "${FavColumns.COMPANY} TEXT NOT NULL," +
               "${FavColumns.REPOSITORY} TEXT NOT NULL," +
               "${FavColumns.FAVORITE} TEXT NOT NULL)"
   }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_FAV)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}