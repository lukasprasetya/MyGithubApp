package com.example.mygithubapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubapp.R
import com.example.mygithubapp.adapter.ListUserAdapter
import com.example.mygithubapp.model.MainViewModel
import com.example.mygithubapp.model.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    private var listUser: ArrayList<User> = ArrayList()
    private lateinit var adapter: ListUserAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ListUserAdapter(listUser)
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        showRecyclerList()
        searchListUser()
        getSetListUser()
        configMainViewModel(adapter)
    }

    private fun configMainViewModel(adapter: ListUserAdapter) {
        mainViewModel.getListUser().observe(this, Observer { userList ->
            if (userList != null) {
                adapter.setData(userList)
                showLoading(false)
            }
        })
    }

    private fun getSetListUser() {
        mainViewModel.setListUser(applicationContext)
        showLoading(true)
    }


    private fun showLoading(b: Boolean) {
        if (b) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.INVISIBLE
        }

    }

    private fun searchListUser() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    return true
                } else {
                    listUser.clear()
                    showRecyclerList()
                    mainViewModel.setListUserSearch(query, applicationContext)

                    configMainViewModel(adapter)
                    showLoading(true)

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun showRecyclerList() {
        rv_user.layoutManager = LinearLayoutManager(this)
        rv_user.setHasFixedSize(true)

        adapter.notifyDataSetChanged()
        rv_user.adapter = adapter

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
