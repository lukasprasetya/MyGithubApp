package com.example.mygithubapp.widget

import android.content.Context
import android.database.Cursor
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.mygithubapp.db.DatabaseContract.FavColumns.Companion.CONTENT_URI
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mygithubapp.R
import com.example.mygithubapp.entity.Favorite
import com.example.mygithubapp.helper.MappingHelper

class FavoriteRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private var cursor: Cursor? = null
    private var items = ArrayList<Favorite>()

    override fun onDataSetChanged() {
        val identityToken = Binder.clearCallingIdentity()
        val cursor = mContext.contentResolver?.query(
            CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val item = MappingHelper.mapCursorToArrayList(cursor)
        items.clear()
        items = item

        Binder.restoreCallingIdentity(identityToken)
    }

    override fun onCreate() {}


    override fun getViewAt(position: Int): RemoteViews {
        val views = RemoteViews(mContext.packageName, R.layout.favorite_widget_item)

        return try {
            val bitmap = Glide.with(mContext)
                .asBitmap()
                .load(items[position].photo)
                .apply(RequestOptions().centerCrop())
                .submit(800, 550)
                .get()
            views.setImageViewBitmap(R.id.img_favWidget, bitmap)
            views.setTextViewText(R.id.tv_name_favWidget, items[position].name)
            views.setTextViewText(R.id.tv_username_favWidget, items[position].username)

            views
        } catch (e: Exception) {
            views
        }
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(p0: Int): Long = 0

    override fun hasStableIds(): Boolean = false

    override fun getCount(): Int = items.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
        cursor?.close()
        items = arrayListOf()
    }
}