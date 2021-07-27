package com.muhaiminur.shohozmovie.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.muhaiminur.shohozmovie.data.CinemaData;
import com.muhaiminur.shohozmovie.data.Genres;
import com.muhaiminur.shohozmovie.data.Movie;
import com.muhaiminur.shohozmovie.database.MovieDao;
import com.muhaiminur.shohozmovie.database.SHOHOZ_DATABASE;
import com.muhaiminur.shohozmovie.network.ApiService;
import com.muhaiminur.shohozmovie.network.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepo {
    private LiveData<List<Movie>> movieMutableLiveData;
    private LiveData<List<Genres>> generesMutableLiveData;
    MovieDao movieDao;
    ApiService apiInterface = Controller.getBaseClient().create(ApiService.class);
    Gson gson = new Gson();

    public MovieRepo(Application application) {
        SHOHOZ_DATABASE db = SHOHOZ_DATABASE.getDatabase(application);
        movieDao = db.movieDao();
        movieMutableLiveData = movieDao.getAllmovie();
        generesMutableLiveData = movieDao.getAllgenres();
    }

    public void get_movie_list() {
        try {
            Call<JsonElement> call = apiInterface.get_movie_list();
            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    if (response.body() != null) {
                        Log.d("from server", response.body().toString());
                        CinemaData cinemaData = gson.fromJson(response.body(), CinemaData.class);
                        if (cinemaData.getMovies().size() > 0) {
                            new insertallAsyncTask(movieDao).execute(cinemaData.getMovies());
                            generwork(cinemaData.getGenres());
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    Log.d("Error", t.toString());
                }
            });
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    private static class insertallAsyncTask extends AsyncTask<List<Movie>, Void, Void> {

        private MovieDao mAsyncTaskDao;

        insertallAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Movie>... params) {
            mAsyncTaskDao.insertallmovie(params[0]);
            return null;
        }
    }

    private static class insertallgenAsyncTask extends AsyncTask<List<Genres>, Void, Void> {

        private MovieDao mAsyncTaskDao;

        insertallgenAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Genres>... params) {
            mAsyncTaskDao.insertallgeneres(params[0]);
            return null;
        }
    }

    public void add_movie(Movie b) {
        //new insertAsyncTask(blogDao).execute(b);
        try {
            String d = setmovieData(b);
            Log.d("dara2", d.toString());
            if (d != null) {
            }
            //Log.d("dara", d.toString());
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void update_movie(Movie b) {
        //new insertAsyncTask(blogDao).execute(b);
        try {
            String d = updatemovieData(b);
            Log.d("dara1", d.toString());
            if (d != null) {
            }
            //Log.d("dara", d.toString());
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public LiveData<List<Movie>> getmovieData() {
        return movieMutableLiveData;
    }

    public LiveData<List<Genres>> getgenData() {
        return generesMutableLiveData;
    }

    public String setmovieData(Movie b) throws ExecutionException, InterruptedException {

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                String d = "done";
                try {
                    movieDao.insertmovie(b);
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                    d = "notdone";
                }
                return d;
            }
        };
        Future<String> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }

    void generwork(List<String> list) {
        try {
            List<Genres> g = new ArrayList<>();
            for (String s : list) {
                g.add(new Genres(s));
            }
            new insertallgenAsyncTask(movieDao).execute(g);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }

    }

    public String updatemovieData(Movie b) throws ExecutionException, InterruptedException {

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                String d = "done";
                try {
                    movieDao.updatemovie(b);
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                    d = "notdone";
                }
                return d;
            }
        };
        Future<String> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }
}