package com.example.mygithubapp.activity

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.mygithubapp.R
import com.example.mygithubapp.adapter.SectionsPagerAdapter
import com.example.mygithubapp.db.DatabaseContract.FavColumns.Companion.USERNAME
import com.example.mygithubapp.db.DatabaseContract.FavColumns.Companion.COMPANY
import com.example.mygithubapp.db.DatabaseContract.FavColumns.Companion.CONTENT_URI
import com.example.mygithubapp.db.DatabaseContract.FavColumns.Companion.FAVORITE
import com.example.mygithubapp.db.DatabaseContract.FavColumns.Companion.LOCATION
import com.example.mygithubapp.db.DatabaseContract.FavColumns.Companion.NAME
import com.example.mygithubapp.db.DatabaseContract.FavColumns.Companion.PHOTO
import com.example.mygithubapp.db.DatabaseContract.FavColumns.Companion.REPOSITORY
import com.example.mygithubapp.db.FavoriteHelper
import com.example.mygithubapp.entity.Favorite
import com.example.mygithubapp.model.User
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_FAV = "extra_fav"
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
    }

    private var isFavorite = false
    private lateinit var favHelper: FavoriteHelper
    private var favorites: Favorite? = null
    private lateinit var imagePhoto: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        favHelper = FavoriteHelper.getInstance(applicationContext)
        favHelper.open()

        favorites = intent.getParcelableExtra(EXTRA_NOTE)
        if (favorites != null) {
            setDataObject()
            isFavorite = true
            val checked: Int = R.drawable.ic_favorite_white_48dp
            btn_favorite.setImageResource(checked)
        } else {
            setData()
        }

        btn_favorite.setOnClickListener(this)
        viewPagerConfig()

    }

    private fun showLoading(state: Boolean) {
        progressBar_detail.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }

    private fun viewPagerConfig() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = sectionsPagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        supportActionBar?.elevation = 0f
    }

    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            this.title = title
        }
    }

    private fun setData() {
        val user = intent.getParcelableExtra(EXTRA_USER) as User

        tv_name_detail.text = user.name
        tv_username_detail.text = user.username
        Glide.with(this)
            .load(user.photo)
            .into(cv_avatar_detail)
        imagePhoto = user.photo.toString()
        tv_location_detail.text = user.location
        tv_company_detail.text = user.company
        tv_repo_detail.text = user.repository
        showLoading(false)
    }

    private fun setDataObject() {
        val favoriteUser = intent.getParcelableExtra(EXTRA_NOTE) as Favorite
        favoriteUser.name?.let { setActionBarTitle(it) }
        tv_username_detail.text = favoriteUser.username
        tv_name_detail.text = favoriteUser.name
        tv_location_detail.text = favoriteUser.location
        tv_company_detail.text = favoriteUser.company
        tv_repo_detail.text = favoriteUser.repository
        Glide.with(this)
            .load(favoriteUser.photo)
            .into(cv_avatar_detail)
        imagePhoto = favoriteUser.photo.toString()
        showLoading(false)
    }

    override fun onClick(v: View?) {
        val checked: Int = R.drawable.ic_favorite_white_48dp
        val unChecked: Int = R.drawable.ic_favorite_border_white_48dp
        if (v?.id == R.id.btn_favorite) {
            if (isFavorite) {
                favHelper.deleteById(favorites?.username.toString())
                Toast.makeText(this, "Deleted Favorite", Toast.LENGTH_SHORT).show()
                btn_favorite.setImageResource(unChecked)
                isFavorite = false

            } else {
                val dataUsername = tv_username_detail.text.toString()
                val dataName = tv_name_detail.text.toString()
                val dataPhoto = imagePhoto
                val dataLocation = tv_location_detail.text.toString()
                val dataCOmpany = tv_company_detail.text.toString()
                val dataRepository = tv_repo_detail.text.toString()
                val dataFavorite = "1"

                val values = ContentValues()
                values.put(USERNAME, dataUsername)
                values.put(NAME, dataName)
                values.put(PHOTO, dataPhoto)
                values.put(LOCATION, dataLocation)
                values.put(COMPANY, dataCOmpany)
                values.put(REPOSITORY, dataRepository)
                values.put(FAVORITE, dataFavorite)

                isFavorite = true
                contentResolver.insert(CONTENT_URI, values)
                Toast.makeText(this, "Added Favorite", Toast.LENGTH_SHORT).show()
                btn_favorite.setImageResource(checked)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        favHelper.close()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_change_setting -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
            R.id.action_favorite -> {
                val mIntent = Intent(this, FavoriteActivity::class.java)
                startActivity(mIntent)
            }
            R.id.action_change_notification -> {
                val mIntent = Intent(this, NotificationSettingActivity::class.java)
                startActivity(mIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}