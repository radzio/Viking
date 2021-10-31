package net.droidlabs.viking.example

import android.content.Intent
import android.os.Bundle
import net.droidlabs.viking.annotations.AutoModule
import net.droidlabs.viking.example.databinding.ActMvvmdemoBinding
import net.droidlabs.viking.example.viewmodels.SecondViewModel
import net.droidlabs.viking.mvvm.ActivityView

@AutoModule
class MvvmSecondActivity : ActivityView<SecondViewModel, ActMvvmdemoBinding>(
    layoutIdRes = R.layout.act_mvvmdemo,
    viewModelClass = SecondViewModel::class.java
) {
  override fun bind(binding: ActMvvmdemoBinding) {}

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setResult(RESULT_OK, Intent().apply {
      putExtra("aaa", intent.getStringExtra("bla") ?: "mssing")
    })

  }

}