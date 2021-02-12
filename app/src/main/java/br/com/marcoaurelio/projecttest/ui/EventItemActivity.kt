package br.com.marcoaurelio.projecttest.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.marcoaurelio.projecttest.R

class EventItemActivity : AppCompatActivity(), EventItemFragment.FragmentCallback {

    companion object {
        const val EVENT_ID = "event-id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.event_item_activity)
    }

    override fun setupToolbar(message: String) {
        var toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.tb_events_dtl)
        var textTitleItemEvent: String = getString(R.string.textEventItem)
        toolbar.title = "${textTitleItemEvent}: ${message}"
    }

}