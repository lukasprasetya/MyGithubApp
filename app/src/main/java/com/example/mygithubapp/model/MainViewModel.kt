package com.example.mygithubapp.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubapp.BuildConfig
import com.example.mygithubapp.activity.MainActivity
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel() {

    private val listData = ArrayList<User>()
    private val listUser = MutableLiveData<ArrayList<User>>()

    fun setListUser(context: Context) {

        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token ${BuildConfig.GITHUB_TOKEN}")
        val url = "https://api.github.com/users"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {

                val result = String(responseBody)
                Log.d(MainActivity.TAG, result)
                try {

                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val responseObject = jsonArray.getJSONObject(i)
                        val username: String = responseObject.getString("login")
                        setListUserDetail(username, context)
                    }

                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun setListUserSearch(data: String, context: Context) {

        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token ${BuildConfig.GITHUB_TOKEN}")
        val url = "https://api.github.com/search/users?q=$data"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {

                val result = String(responseBody)
                Log.d(MainActivity.TAG, result)

                try {
                    listData.clear()
                    val jsonArray = JSONObject(result)
                    val item = jsonArray.getJSONArray("items")
                    for (i in 0 until item.length()) {
                        val responseObject = item.getJSONObject(i)
                        val username: String = responseObject.getString("login")
                        setListUserDetail(username, context)
                    }

                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {

                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()

            }

        })
    }

    fun setListUserDetail(data: String, context: Context) {
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token ${BuildConfig.GITHUB_TOKEN}")
        val url = "https://api.github.com/users/$data"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {

                val result = String(responseBody)
                Log.d(MainActivity.TAG, result)

                try {

                    val responseObject = JSONObject(result)
                    val photo: String = responseObject.getString("avatar_url").toString()
                    val name: String = responseObject.getString("name").toString()
                    val username: String = responseObject.getString("login").toString()
                    val location: String = responseObject.getString("location").toString()
                    val company: String = responseObject.getString("company").toString()
                    val repository: String? = responseObject.getString("public_repos")
                    val follower: String? = responseObject.getString("followers")
                    val following: String? = responseObject.getString("following")
                    listData.add(
                        User(
                            photo = photo,
                            name = name,
                            username = username,
                            location = location,
                            company = company,
                            repository = repository,
                            follower = follower,
                            following = following
                        )
                    )

                    listUser.postValue(listData)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"

                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getListUser(): LiveData<ArrayList<User>> {
        return listUser
    }
}