package com.example.mygithubapp.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import com.example.mygithubapp.AlarmReceiver
import com.example.mygithubapp.R
import kotlinx.android.synthetic.main.activity_notification_setting.*

class NotificationSettingActivity : AppCompatActivity() {

    companion object {
        const val PREFS_NAME = "prefs_name"
        private const val REPEATING = "repeating"
    }

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var mSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_setting)

        alarmReceiver = AlarmReceiver()
        mSharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        controlSwitch()
        switchNotif.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alarmReceiver.setRepeatingAlarm(
                    this, AlarmReceiver.TYPE_REPEATING,
                    getString(R.string.repeat_message)
                )
            } else {
                alarmReceiver.cancelAlarm(this)
            }
            saveChange(isChecked)
        }
    }

    private fun saveChange(checked: Boolean) {
        val editor = mSharedPreferences.edit()
        editor.putBoolean(REPEATING, checked)
        editor.apply()
    }

    private fun controlSwitch() {
        switchNotif.isChecked = mSharedPreferences.getBoolean(REPEATING, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }
}