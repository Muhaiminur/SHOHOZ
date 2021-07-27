package com.muhaiminur.shohozmovie.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.muhaiminur.shohozmovie.R;
import com.muhaiminur.shohozmovie.data.Movie;
import com.muhaiminur.shohozmovie.databinding.ActivitySplashpageBinding;
import com.muhaiminur.shohozmovie.utility.Utility;
import com.muhaiminur.shohozmovie.viewmodel.MovielistPageViewModel;

import java.util.List;

public class SplashPage extends AppCompatActivity {
    Context context;
    Utility utility;
    ActivitySplashpageBinding binding;
    MovielistPageViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashpageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        try {
            context = this;
            utility = new Utility(context);
            viewModel = new ViewModelProvider(this).get(MovielistPageViewModel.class);
            viewModel.init();
            observeadd();
            binding.splashView.setImageAssetsFolder("assets/");
            binding.splashView.setAnimation("splash-animation.json");
            binding.splashView.playAnimation();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    //observer for data check
    private void observeadd() {
        viewModel.getmovieResponseLiveData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> volumesResponse) {
                if (volumesResponse != null && volumesResponse.size() > 0) {
                    startActivity(new Intent(context, Homepage.class));
                    finish();
                } else {
                    if (utility.isNetworkAvailable()) {
                        viewModel.getmovielistonline();
                    } else {
                        utility.showDialog(context.getResources().getString(R.string.no_internet));
                    }
                }
            }
        });
    }

    //request full screen for splash
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    //for full screen
    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}