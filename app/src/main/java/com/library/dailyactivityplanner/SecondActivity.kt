package com.library.dailyactivityplanner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.library.dailyactivityplanner.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity(), SetOnDeleteListener {
    private lateinit var binding: ActivitySecondBinding
    private var state = 2
    private val adapter: AdapterList by lazy { AdapterList(this, this) }
    override fun onDeleteListener(id: Long) {
        val data = SessionManager.getActivities()
        val position = data.map { it.id }.indexOf(id)
        if (position != -1) {
            data.removeAt(position)
        }
        adapter.clearData()
        adapter.addData(data)
        SessionManager.saveActivity(data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = "My Plan"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = adapter
        adapter.addData(SessionManager.getActivities())
        (binding.toolbar as androidx.appcompat.widget.Toolbar).setNavigationOnClickListener {
            onBackPressed()
        }
        binding.llSort.setOnClickListener {
            val dataActivities = SessionManager.getActivities()
            adapter.clearData()
            if (state == 1) {
                state = 2
                binding.ivIconSort.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_baseline_keyboard_arrow_down_24
                    )
                )
                dataActivities.sortBy { it.startTime }
            } else {
                state = 1
                binding.ivIconSort.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_baseline_keyboard_arrow_up_24
                    )
                )

                dataActivities.sortByDescending { it.startTime }
            }
            adapter.addData(dataActivities)
        }
    }
}