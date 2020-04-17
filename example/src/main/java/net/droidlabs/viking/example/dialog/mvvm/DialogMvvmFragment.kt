package net.droidlabs.viking.example.dialog.mvvm

import net.droidlabs.viking.annotations.AutoModule
import net.droidlabs.viking.example.R
import net.droidlabs.viking.example.TestViewModel2
import net.droidlabs.viking.example.databinding.FragmentVikingDialogBinding
import net.droidlabs.viking.mvvm.DialogFragmentView

@AutoModule
class DialogMvvmFragment : DialogFragmentView<TestViewModel2, FragmentVikingDialogBinding>(
    layoutIdRes = R.layout.fragment_viking_dialog,
    viewModelClass = TestViewModel2::class.java
) {
  override fun bind(binding: FragmentVikingDialogBinding) {}

  companion object {
    fun newInstance(): DialogMvvmFragment {
      return DialogMvvmFragment()
    }
  }
}