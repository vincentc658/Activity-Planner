package com.library.dailyactivityplanner

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import com.library.dailyactivityplanner.databinding.ActivityMainBinding
import java.text.DateFormat
import java.util.*

class
MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isStartTimeProperly = false
    private var isEndTimeProperly = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Eleplanner"
        val calendar: Calendar = Calendar.getInstance()
        val startDatePickerDialog =
            DatePickerDialog(
                this@MainActivity,
                startDatePickerListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        val endDatePickerDialog =
            DatePickerDialog(
                this@MainActivity,
                endDatePickerListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        binding.tvStartTime.setOnClickListener {
            startDatePickerDialog.show()

        }
        binding.tvEndTime.setOnClickListener {
            endDatePickerDialog.show()
        }
        binding.tvProceed.setOnClickListener {
            if (!isStartTimeProperly) {
                Toast.makeText(this, "Please set start datetime properly", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (!isEndTimeProperly) {
                Toast.makeText(this, "Please set end datetime properly", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.etActivity.text.isEmpty()) {
                Toast.makeText(this, "Please enter your activity", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            if (binding.etLocation.text.isEmpty()) {
                Toast.makeText(this, "Please enter your location", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val activity = ActivityModel()
            activity.id = System.currentTimeMillis()
            activity.activity = binding.etActivity.text.toString()
            activity.location = binding.etLocation.text.toString()
            val startTime = TimeConverter.getTime("${binding.tvStartTime.text}:00")
            activity.startTime = startTime
            activity.endTime = TimeConverter.getTime("${binding.tvEndTime.text}:00")
            val data = SessionManager.getActivities()
            data.add(activity)
            SessionManager.saveActivity(data)
            binding.etActivity.text.clear()
            binding.etLocation.text.clear()
            binding.tvStartTime.text=""
            binding.tvEndTime.text=""
            setAlarm(startTime)
            Toast.makeText(this, "Added to My Plan ", Toast.LENGTH_SHORT).show()

        }
        binding.cvMyPlan.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }


    }

    private val startDatePickerListener = DatePickerDialog.OnDateSetListener { p0, p1, p2, p3 ->
        val timePickerDialog = TimePickerDialog(
            this@MainActivity,
            startTimePickerListner,
            TimeConverter.getDate(System.currentTimeMillis(), TimeConverter.HH).toInt(),
            TimeConverter.getDate(System.currentTimeMillis(), TimeConverter.MM_).toInt(),
            true
        )
        timePickerDialog.show()
        binding.tvStartTime.text = "$p1-${p2+1}-$p3"
    }
    private val endDatePickerListener = DatePickerDialog.OnDateSetListener { p0, p1, p2, p3 ->
        val timePickerDialog = TimePickerDialog(
            this@MainActivity,
            endTimePickerListner,
            TimeConverter.getDate(System.currentTimeMillis(), TimeConverter.HH).toInt(),
            TimeConverter.getDate(System.currentTimeMillis(), TimeConverter.MM_).toInt(),
            true
        )
        binding.tvEndTime.text = "$p1-${p2+1}-$p3"
        timePickerDialog.show()
    }
    private val startTimePickerListner = TimePickerDialog.OnTimeSetListener { p0, p1, p2 ->
        binding.tvStartTime.text = "${binding.tvStartTime.text} $p1:$p2"
        isStartTimeProperly = true

    }
    private val endTimePickerListner = TimePickerDialog.OnTimeSetListener { p0, p1, p2 ->
        binding.tvEndTime.text = "${binding.tvEndTime.text} $p1:$p2"
        isEndTimeProperly = true

    }
    private fun setAlarm(timeInMillis: Long) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, MyAlarm::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            pendingIntent
        )
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show()
    }
    class MyAlarm : BroadcastReceiver() {
        override fun onReceive(
            context: Context,
            intent: Intent
        ) {
            val intent = Intent(context, SecondActivity::class.java)
            pushNotification(context, "channel",1,intent)
        }
        private fun pushNotification(
            context : Context,
            channelId: String,
            idNotification: Int,
            intent: Intent,

            ) {
            val contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val contentTitle ="Open your app and check your plan"
            val mBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(contentTitle)
                .setContentText("Start your activity")
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            mNotificationManager.notify(idNotification, mBuilder.build())
        }
    }

}