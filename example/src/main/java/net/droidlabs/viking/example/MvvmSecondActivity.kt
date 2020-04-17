package net.droidlabs.viking.example

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
}