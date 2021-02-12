package br.com.marcoaurelio.projecttest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.marcoaurelio.projecttest.R

class EventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_activity)
        setupToolbar()
    }

    private fun setupToolbar() {
        val actionBarEvents = findViewById<androidx.appcompat.widget.Toolbar>(R.id.tb_events);
        actionBarEvents.title = getString(R.string.textEvents)
    }


}