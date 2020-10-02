package net.droidlabs.viking.example

import net.droidlabs.viking.annotations.AutoModule
import net.droidlabs.viking.example.MapActivity
import net.droidlabs.viking.example.databinding.FragmentTestBinding
import net.droidlabs.viking.mvvm.FragmentView

@AutoModule(scopes = [MapActivity::class])
class MvvmFragment : FragmentView<TestViewModel2, FragmentTestBinding>(
    layoutIdRes = R.layout.fragment_test,
    viewModelClass = TestViewModel2::class.java) {
  override fun bind(binding: FragmentTestBinding) {}

  companion object {
    fun newInstance(): MvvmFragment {
      return MvvmFragment()
    }
  }
}