package br.com.marcoaurelio.projecttest.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import br.com.marcoaurelio.projecttest.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val maxSplashTime: Long = 5000
        var progressSplash: Long = 0
        val percentResult = maxSplashTime / 101
        val pg_bar = findViewById<ProgressBar>(R.id.progressSplashBar)

        for (x in 0..100) {
            Handler(Looper.getMainLooper()).postDelayed({
                pg_bar.progress = x
            }, progressSplash)
            progressSplash += percentResult
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, EventActivity::class.java))
            finish()
        }, maxSplashTime)

    }
}