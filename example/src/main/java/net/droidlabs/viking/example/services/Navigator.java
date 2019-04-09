package net.droidlabs.viking.example.services;

import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;

import net.droidlabs.viking.example.MvvmMainActivity;
import net.droidlabs.viking.example.MvvmSecondActivity;


public class Navigator {

  private Context context;

  @Inject
  public Navigator(Context context) {
    this.context = context;
  }

  public void openMainActivity() {
    context.startActivity(new Intent(context, MvvmMainActivity.class));
  }

  public void openSecondActivity() {
    context.startActivity(new Intent(context, MvvmSecondActivity.class));
  }
}

