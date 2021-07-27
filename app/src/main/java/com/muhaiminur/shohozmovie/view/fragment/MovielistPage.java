package com.muhaiminur.shohozmovie.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muhaiminur.shohozmovie.R;
import com.muhaiminur.shohozmovie.adapter.MovieAdapter;
import com.muhaiminur.shohozmovie.data.Genres;
import com.muhaiminur.shohozmovie.data.Movie;
import com.muhaiminur.shohozmovie.databinding.MovielistPageFragmentBinding;
import com.muhaiminur.shohozmovie.utility.Utility;
import com.muhaiminur.shohozmovie.viewmodel.MovielistPageViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovielistPage extends Fragment {

    Utility utility;
    Context context;
    List<Movie> movie_list;
    MovieAdapter movieAdapter;
    MovielistPageFragmentBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;
    private MovielistPageViewModel mViewModel;
    ArrayList<Genres> genresArrayList = new ArrayList<>();
    ArrayAdapter<Genres> genadapter;
    private boolean isSpinnerInitial = true;

    public static MovielistPage newInstance() {
        return new MovielistPage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = MovielistPageFragmentBinding.inflate(inflater, container, false);
            try {
                context = getActivity();
                utility = new Utility(context);
                navHostFragment = (NavHostFragment) ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.frag_homepage_view);
                navController = navHostFragment.getNavController();
                initial_list();
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MovielistPageViewModel.class);
        mViewModel.init();
        get_movie_list();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    //recyclerview and spinner initi
    void initial_list() {
        movie_list = new ArrayList<>();
        movieAdapter = new MovieAdapter(movie_list, context);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.movieRecyler.setLayoutManager(mLayoutManager);
        binding.movieRecyler.setItemAnimator(new DefaultItemAnimator());
        binding.movieRecyler.setAdapter(movieAdapter);

        genadapter = new ArrayAdapter<Genres>(context, android.R.layout.simple_spinner_dropdown_item, genresArrayList);
        genadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.genSpinner.setAdapter(genadapter);
        binding.genSpinner.setSelection(Adapter.NO_SELECTION, true);
        binding.genSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isSpinnerInitial) {
                    isSpinnerInitial = false;
                } else {
                    String gen = ((Genres) parent.getAdapter().getItem(position)).getGen_name();
                    gen_filter(gen);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //getting movie list observer from viewmodel
    private void get_movie_list() {
        try {
            mViewModel.getmovieResponseLiveData().observe(getActivity(), new Observer<List<Movie>>() {
                @Override
                public void onChanged(List<Movie> volumesResponse) {
                    if (volumesResponse != null && volumesResponse.size() > 0) {
                        movie_list.clear();
                        movie_list.addAll(volumesResponse);
                        movieAdapter.notifyDataSetChanged();
                    } else {
                        utility.showDialog(context.getResources().getString(R.string.no_data));
                    }
                }
            });

            mViewModel.getgenResponseLiveData().observe(getActivity(), new Observer<List<Genres>>() {
                @Override
                public void onChanged(List<Genres> volumesResponse) {
                    if (volumesResponse != null && volumesResponse.size() > 0) {
                        utility.logger("gen size" + volumesResponse.size());
                        genresArrayList.clear();
                        genresArrayList.addAll(volumesResponse);
                        genadapter.notifyDataSetChanged();
                    }
                }
            });
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    //filter from spinner
    void gen_filter(String s) {
        try {
            List<Movie> filter_movie = new ArrayList<>();
            for (Movie movie : movie_list) {
                for (String find : movie.getGenres()) {
                    if (find.equalsIgnoreCase(s)) {
                        filter_movie.add(movie);
                    }
                }
            }
            movieAdapter.filterList(filter_movie);

        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }
}