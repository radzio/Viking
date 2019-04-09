package net.droidlabs.viking.mvp

import android.content.Intent

interface OnResultAction {
    fun execute(success: Boolean, data: Intent?)
}