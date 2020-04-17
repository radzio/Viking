package net.droidlabs.viking.example

import android.os.Build
import android.util.Log
import androidx.lifecycle.Observer
import net.droidlabs.viking.annotations.AutoModule
import net.droidlabs.viking.example.databinding.ActMvvmdemoBinding
import net.droidlabs.viking.example.services.Navigator
import net.droidlabs.viking.example.viewmodels.MainViewModel
import net.droidlabs.viking.mvvm.ActivityView
import javax.inject.Inject

@AutoModule
class MvvmMainActivity : ActivityView<MainViewModel, ActMvvmdemoBinding>(R.layout.act_mvvmdemo, MainViewModel::class.java) {

  @Inject
  lateinit var navigator: Navigator

  override fun bind(binding: ActMvvmdemoBinding) {

    viewModel().test.observe(this, Observer { integer ->
      Log.d("test", String.format("%s", integer!!))
      //navigator!!.openSecondActivity()

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        enterPictureInPictureMode()
      }

    })

    binding.viewModel = viewModel()
  }
}