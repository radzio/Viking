package net.droidlabs.viking.mvvm

import android.content.Intent

interface OnResultAction {
    fun execute(success: Boolean, data: Intent?)
}