package com.example.mygithubapp.widget

import android.content.Intent
import android.widget.RemoteViewsService

class FavoriteWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory =
        FavoriteRemoteViewsFactory(this.applicationContext)
}