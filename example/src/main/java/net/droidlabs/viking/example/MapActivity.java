package net.droidlabs.viking.example;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.fragment.app.FragmentManager;
import com.google.android.gms.maps.MapView;
import net.droidlabs.viking.annotations.AutoModule;
import net.droidlabs.viking.annotations.AutoProvides;
import net.droidlabs.viking.example.databinding.ActivityMapBinding;
import net.droidlabs.viking.example.dialog.mvp.VikingDialogFragment;
import net.droidlabs.viking.example.dialog.mvvm.DialogMvvmFragment;
import net.droidlabs.viking.example.services.Navigator;
import net.droidlabs.viking.map.views.MapAwareActivityView;

@AutoModule
public class MapActivity extends MapAwareActivityView<MapViewModel, ActivityMapBinding> {
  private static final String DIALOG_FRAGMENT_TAG = "fragment_dialog";

  public MapActivity() {
    super(R.layout.activity_map, MapViewModel.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState == null) {
      getSupportFragmentManager()
          .beginTransaction()
          .add(R.id.fragmentBla, MvvmFragment.newInstance())
          .commitAllowingStateLoss();
    }

    Button dialogMvpButton = findViewById(R.id.button_dialog_mvp);
    dialogMvpButton.setOnClickListener(listener -> {
      FragmentManager fragmentManager = getSupportFragmentManager();
      VikingDialogFragment fragment = VikingDialogFragment.newInstance();
      fragment.show(fragmentManager, DIALOG_FRAGMENT_TAG);
    });

    Button dialogMvvmButton = findViewById(R.id.button_dialog_mvvm);
    dialogMvvmButton.setOnClickListener(listener -> {
      FragmentManager fragmentManager = getSupportFragmentManager();
      DialogMvvmFragment fragment = DialogMvvmFragment.newInstance();
      fragment.show(fragmentManager, DIALOG_FRAGMENT_TAG);
    });
  }

  @Override
  protected void bind(ActivityMapBinding binding) {
    binding.setViewModel(viewModel());

    viewModel().getTestSingleLiveEvent().observe(this, integer -> {
      Log.d("test", String.format("%s", integer));
      integer.navigate(new Navigator(MapActivity.this));
    });

  }

  @Override
  public MapView mapView() {
    return binding().map;
  }

  @AutoProvides("test_string")
  public String test() {
    return "Hello world";
  }

  @AutoProvides("second_test_string")
  public String secondTest() {
    return "Goodbye cruel world";
  }
}