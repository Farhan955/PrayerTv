package com.tbum.prayertv.Activities

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.tbum.prayertv.Adapters.SliderPagerAdapter
import com.tbum.prayertv.Alarms.MyAlarmManager
import com.tbum.prayertv.R
import com.tbum.prayertv.Utils.Functions
import com.tbum.prayertv.Utils.SharedPref
import com.tbum.prayertv.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import java.util.*

@Suppress("all", "deprecation")
class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val context = this
    private lateinit var binding: ActivityMainBinding
    private val sp = SharedPref(context)
    private var fajar = ""
    private var zohar = ""
    private var asar = ""
    private var maghrib = ""
    private var isha = ""
    private var ishraq = ""
    private var sunrise = ""
    private var date = ""
    private lateinit var myAlarmManager: MyAlarmManager
    private lateinit var adapter: SliderPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        changeListener()
        automateViewPagerSwiping()
    }

    private fun changeListener() {
        binding.slider.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (sp.getSliderList("mySlider")[position].type == "video") {
                    if (!SliderPagerAdapter.videoView.isPlaying) {
                        SliderPagerAdapter.videoView.setVideoURI(Uri.parse(sp.getSliderList("mySlider")[position].uri))
                        SliderPagerAdapter.videoView.start()
                    }

                } else {
                    SliderPagerAdapter.videoView.stopPlayback()
                }
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun initUI() {
        myAlarmManager = MyAlarmManager(this@MainActivity)
//        Functions.checkNotificationPolicy(context)
//        Functions.checkOverlayPermission(context)
//          val millis: Long = System.currentTimeMillis() + 10000
//          myAlarmManager!!.setAlarm(millis)

        binding.tvHorizontal.isSelected = true
    }

    private fun setSlider() {
        /*  val url = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
          val video =
              "https://firebasestorage.googleapis.com/v0/b/eatlaa.appspot.com/o/videos%2F1651237784236?alt=media&token=fbc5cd3e-339e-4622-8b35-d8a5182428ac"
          val obj = MySlider(url, "image", "asfasfa")
          val obj1 = MySlider(video, "video", "asfasfa")

          var list = ArrayList<MySlider>()

          list.add(obj)
          list.add(obj)
          list.add(obj1)
          list.add(obj)
  */

        var currentPage = binding.slider.currentItem

        adapter = SliderPagerAdapter(this, sp.getSliderList("mySlider")) {
            binding.slider.setCurrentItem(currentPage++, true)
        }

        binding.slider.adapter = adapter
        binding.myTablayout.setupWithViewPager(binding.slider)
        binding.slider.offscreenPageLimit = 0


//        val handler = Handler()
//
//        val update = Runnable {
//
//            if (currentPage === sliderList.size) {
//                currentPage = 0
//            }
//            binding.slider.setCurrentItem(currentPage++, true)
//        }
//        try {
//
//            Timer().schedule(object : TimerTask() {
//                override fun run() {
//                    handler.post(update)
//                }
//            }, 100, 1000)
//
//        } catch (e: Exception) {
//
//        }
    }

    override fun onResume() {
        super.onResume()
        getPrayerTime()

        binding.tvVertical.text = sp["scroll_up_text"]
        binding.tvHorizontal.text = sp["running_text"]

        setSlider()
        setColors()
    }

    private fun setColors() {
        if (sp.getColor("mName") != -1) {
            binding.tvMosqueName.setTextColor(sp.getColor("mName"))
        }

        if (sp.getColor("timeClock") != -1) {
            binding.simpleDigitalClock.setTextColor(sp.getColor("timeClock"))
        }

        if (sp.getColor("date") != -1) {
            binding.txtAlamat.setTextColor(sp.getColor("date"))
        }

        if (sp.getColor("hText") != -1) {
            binding.tvHorizontal.setTextColor(sp.getColor("hText"))
        }

        if (sp.getColor("vText") != -1) {
            binding.tvVertical.setTextColor(sp.getColor("vText"))
        }
    }

    private fun getPrayerTime() {
        val json: String?
        try {
            val stream: InputStream = assets.open("PutrajayaZone.json")
            val size: Int = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val charset: Charset = Charsets.UTF_8
            json = String(buffer, charset)
            var obj = JSONObject(json)
            val jsonArray: JSONArray = obj.getJSONArray("PutrajayaZone")

            for (i in 0 until jsonArray.length()) {

                obj = jsonArray.getJSONObject(i)
                val date = obj.getString("Date")

                if (date.equals(Functions.getCurentDate())) {
                    break
                }

            }


            date =
                obj.getString("Day") + ", " + obj.getString("Date") + " / " + obj.getString("iDate")


            fajar = obj.getString("Fajar")
            zohar = obj.getString("Zohar")
            asar = obj.getString("Asar")
            maghrib = obj.getString("Maghrib")
            isha = obj.getString("Isha")
            ishraq = obj.getString("Ishrak")
            sunrise = obj.getString("Sun Rise")

            setData()
        } catch (ex: IOException) {
            Toast.makeText(this, "Error: " + ex.message, Toast.LENGTH_SHORT).show()
        }

    }

    private fun setData() {
        binding.tvFajar.text = "$fajar AM"
        binding.tvZohar.text = "$zohar PM"
        binding.tvAsar.text = "$asar PM"
        binding.tvMaghrib.text = "$maghrib PM"
        binding.tvIsha.text = "$isha PM"
        binding.tvIshraq.text = "$ishraq AM"
        binding.tvSunrise.text = "$sunrise AM"
        binding.txtAlamat.text = date

        setCurrentActive()
    }

    fun ivSettings(view: View) {
        startActivity(Intent(context, SettingsActivity::class.java))
    }

    private fun setCurrentActive() {
        binding.tvFajar.setBackgroundColor(Color.WHITE)
        binding.tvZohar.setBackgroundColor(Color.WHITE)
        binding.tvAsar.setBackgroundColor(Color.WHITE)
        binding.tvMaghrib.setBackgroundColor(Color.WHITE)
        binding.tvIsha.setBackgroundColor(Color.WHITE)
        binding.tvIshraq.setBackgroundColor(Color.WHITE)
        binding.tvSunrise.setBackgroundColor(Color.WHITE)


        binding.tvFajar.setTextColor(Color.BLACK)
        binding.tvSunrise.setTextColor(Color.BLACK)
        binding.tvIshraq.setTextColor(Color.BLACK)
        binding.tvZohar.setTextColor(Color.BLACK)
        binding.tvAsar.setTextColor(Color.BLACK)
        binding.tvMaghrib.setTextColor(Color.BLACK)
        binding.tvIsha.setTextColor(Color.BLACK)

        when (getIndex(fajar, sunrise, ishraq, zohar, asar, maghrib, isha)) {
            1 -> {
                binding.tvFajar.setBackgroundColor(resources.getColor(R.color.color_orange_A400))
                binding.tvFajar.setTextColor(Color.WHITE)
            }
            2 -> {
                /*      binding.tvSunrise.setBackgroundColor(resources.getColor(R.color.color_orange_A400))
                      binding.tvSunrise.setTextColor(Color.WHITE)*/

            }
            3 -> {
                /*          binding.tvIshraq.setBackgroundColor(resources.getColor(R.color.color_orange_A400))
                          binding.tvIshraq.setTextColor(Color.WHITE)*/
            }
            4 -> {
                binding.tvZohar.setBackgroundColor(resources.getColor(R.color.color_orange_A400))
                binding.tvZohar.setTextColor(Color.WHITE)
            }
            5 -> {
                binding.tvAsar.setBackgroundColor(resources.getColor(R.color.color_orange_A400))
                binding.tvAsar.setTextColor(Color.WHITE)
            }
            6 -> {
                binding.tvMaghrib.setBackgroundColor(resources.getColor(R.color.color_orange_A400))
                binding.tvMaghrib.setTextColor(Color.WHITE)
            }
            7 -> {
                binding.tvIsha.setBackgroundColor(resources.getColor(R.color.color_orange_A400))
                binding.tvIsha.setTextColor(Color.WHITE)
            }
        }

/*
        if (isGreater(fajar) && !isGreater(sunrise)) {
            binding.tvFajar.setBackgroundColor(resources.getColor(R.color.color_orange_A400))
            binding.tvFajar.setTextColor(Color.WHITE)

        } else if (isGreater(sunrise) && !isGreater(ishraq)) {
            binding.tvSunrise.setBackgroundColor(resources.getColor(R.color.color_orange_A400))
            binding.tvSunrise.setTextColor(Color.WHITE)

        } else if (isGreater(ishraq) && !isGreater(zohar,12)) {
            binding.tvIshraq.setBackgroundColor(resources.getColor(R.color.color_orange_A400))
            binding.tvIshraq.setTextColor(Color.WHITE)

        } else if (isGreater(zohar,12) && !isGreater(asar,12)) {
            binding.tvZohar.setBackgroundColor(resources.getColor(R.color.color_orange_A400))
            binding.tvZohar.setTextColor(Color.WHITE)

        } else if (isGreater(asar,12) && !isGreater(maghrib,12)) {
            binding.tvAsar.setBackgroundColor(resources.getColor(R.color.color_orange_A400))
            binding.tvAsar.setTextColor(Color.WHITE)

        } else if (isGreater(maghrib,12) && !isGreater(isha,12)) {
            binding.tvMaghrib.setBackgroundColor(resources.getColor(R.color.color_orange_A400))
            binding.tvMaghrib.setTextColor(Color.WHITE)

        } else if (isGreater(isha,12) && !isGreater(fajar)) {
            binding.tvIsha.setBackgroundColor(resources.getColor(R.color.color_orange_A400))
            binding.tvIsha.setTextColor(Color.WHITE)
        }

        val d=isGreater(fajar)*/

//        Toast.makeText(this, "" + d, Toast.LENGTH_SHORT).show()

    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        binding.animationView.visibility = View.VISIBLE

        Handler(Looper.getMainLooper()).postDelayed({
            binding.animationView.visibility = View.GONE
            getPrayerTime()
        }, 20000)
    }

    override fun onStart() {
        super.onStart()
        sp.registerPref(context, this)
    }

    override fun onStop() {
        super.onStop()
        sp.unregisterPref(context, this)
    }

    private fun automateViewPagerSwiping() {
        val DELAY_MS: Long = 500
        val PERIOD_MS: Long = 3000
        val handler = Handler()

        val update = Runnable {
            if (binding.slider.currentItem === adapter.count - 1) {
                binding.slider.currentItem = 0
            } else {
                binding.slider.setCurrentItem(binding.slider.currentItem + 1, true)
            }
        }

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, DELAY_MS, PERIOD_MS)
    }
}