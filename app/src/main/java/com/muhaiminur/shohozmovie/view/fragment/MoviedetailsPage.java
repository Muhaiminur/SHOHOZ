package com.muhaiminur.shohozmovie.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.muhaiminur.shohozmovie.R;
import com.muhaiminur.shohozmovie.data.Movie;
import com.muhaiminur.shohozmovie.databinding.MoviedetailsPageFragmentBinding;
import com.muhaiminur.shohozmovie.utility.KeyWord;
import com.muhaiminur.shohozmovie.utility.Utility;
import com.muhaiminur.shohozmovie.viewmodel.MoviedetailsPageViewModel;

public class MoviedetailsPage extends Fragment {
    Utility utility;
    Context context;
    MoviedetailsPageFragmentBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;
    private MoviedetailsPageViewModel mViewModel;
    Gson gson = new Gson();
    Movie movie;

    public static MoviedetailsPage newInstance() {
        return new MoviedetailsPage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = MoviedetailsPageFragmentBinding.inflate(inflater, container, false);
            try {
                context = getActivity();
                utility = new Utility(context);
                navHostFragment = (NavHostFragment) ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.frag_homepage_view);
                navController = navHostFragment.getNavController();
                movie = gson.fromJson(getArguments().getString(KeyWord.MOVIE_DETAILS), Movie.class);
                if (movie != null) {
                    Glide.with(context).load(movie.getPosterUrl()).placeholder(R.drawable.ic_loading).error(R.drawable.ic_default).into(binding.moviedetailsImage);
                    binding.moviedetailsTittle.setText(movie.getTitle());
                    binding.moviedetailsYear.setText(movie.getYear());
                    binding.moviedetailsCategory.setText(movie.getGenres().toString());
                    binding.moviedetailsDirector.setText(movie.getDirector());
                    binding.moviedetailsActor.setText(movie.getActors());
                    binding.moviedetailsPlot.setText(movie.getPlot());
                    binding.moviedetailsRuntime.setText(movie.getRuntime());
                } else {
                    utility.showToast(context.getResources().getString(R.string.something_went_wrong));
                }
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MoviedetailsPageViewModel.class);
    }

}