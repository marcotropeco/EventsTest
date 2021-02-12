package br.com.marcoaurelio.projecttest.util

import com.squareup.picasso.Callback

abstract class TargetCallback : Callback {

    override fun onSuccess() {
        onAfterLoad()
    }

    override fun onError(e: Exception?) {
        onAfterLoad()
    }

    abstract fun onAfterLoad()
}