package com.tbum.prayertv.Activities

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.tbum.prayertv.Utils.Functions
import com.tbum.prayertv.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    val context = this
    lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Functions.setToolbar(this)

    }

    fun infoClick(view: View) {
        startActivity(Intent(context, InfoActivity::class.java))
    }

    fun runningClick(view: View) {
        startActivity(Intent(context, RunningTextActivity::class.java))

    }

    fun reminderClick(view: View) {
        startActivity(Intent(context, ReminderActivity::class.java))
    }

    fun datesClick(view: View) {
        startActivity(Intent(context, ImportantDatesActivity::class.java))
    }

    fun soundClick(view: View) {
        startActivity(Intent(context, SoundListActivity::class.java))

    }

    fun zoneClick(view: View) {
        startActivity(Intent(context, PrayerZoneActivity::class.java))

    }

    fun bannerClick(view: View) {
        checkPermissions()
    }

    private fun checkPermissions() {

        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                startActivity(Intent(context, BannerListActivity::class.java))
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
        TedPermission.with(context)
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
            .check()
    }

    fun scrollClick(view: View) {
        startActivity(Intent(context, ScrollUpTextActivity::class.java))

    }

    fun colorClick(view: View) {
        startActivity(Intent(context, ColorPickerActivity::class.java))

    }

}