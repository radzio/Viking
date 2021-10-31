package net.droidlabs.viking.example

import android.content.Intent
import android.os.Bundle
import android.util.Log
import net.droidlabs.viking.annotations.AutoModule
import net.droidlabs.viking.example.databinding.ActMvvmdemoBinding
import net.droidlabs.viking.example.services.Navigator
import net.droidlabs.viking.example.viewmodels.MainViewModel
import net.droidlabs.viking.mvvm.ActivityView
import timber.log.Timber
import javax.inject.Inject

@AutoModule
class MvvmMainActivity : ActivityView<MainViewModel, ActMvvmdemoBinding>(R.layout.act_mvvmdemo, MainViewModel::class.java) {

  @Inject
  lateinit var navigator: Navigator

  override fun bind(binding: ActMvvmdemoBinding) {
    binding.viewModel = viewModel()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d("TEST", "On activity create")
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    Log.d("TEST", "On activity post create")
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    Log.d("TEST", "On activity result")
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    Log.d("TEST", "On activity onRequestPermissionsResult")
  }
}