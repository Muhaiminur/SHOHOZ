package com.muhaiminur.shohozmovie.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.muhaiminur.shohozmovie.R;
import com.muhaiminur.shohozmovie.data.Movie;
import com.muhaiminur.shohozmovie.databinding.RecyclerMovieBinding;
import com.muhaiminur.shohozmovie.utility.KeyWord;
import com.muhaiminur.shohozmovie.utility.Utility;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.Todo_View_Holder> {
    Context context;
    List<Movie> list;
    Utility utility;


    public MovieAdapter(List<Movie> to, Context c) {
        list = to;
        context = c;
        utility = new Utility(context);
    }

    public class Todo_View_Holder extends RecyclerView.ViewHolder {
        RecyclerMovieBinding binding;

        public Todo_View_Holder(RecyclerMovieBinding view) {
            super(view.getRoot());
            this.binding = view;
        }

        public void bind(Movie s) {
            binding.setMovie(s);
            binding.executePendingBindings();
        }
    }

    @Override
    public MovieAdapter.Todo_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerMovieBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_movie, parent, false);
        return new MovieAdapter.Todo_View_Holder(binding);
    }

    @Override
    public void onBindViewHolder(final MovieAdapter.Todo_View_Holder holder, int position) {
        final Movie bodyResponse = list.get(position);
        try {
            holder.bind(bodyResponse);
            Glide.with(context)
                    .load(bodyResponse.getPosterUrl())
                    .fitCenter().error(R.drawable.ic_default)
                    .into(holder.binding.movieImage);

            holder.binding.movieView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        NavController navController = Navigation.findNavController(holder.binding.getRoot());
                        if (navController != null) {
                            Gson gson = new Gson();
                            Bundle bundle = new Bundle();
                            bundle.putString(KeyWord.MOVIE_DETAILS, gson.toJson(bodyResponse));
                            navController.navigate(R.id.moviedetailspage, bundle);
                        }
                    } catch (Exception e) {
                        Log.d("Error Line Number", Log.getStackTraceString(e));
                    }
                }
            });
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Movie> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}