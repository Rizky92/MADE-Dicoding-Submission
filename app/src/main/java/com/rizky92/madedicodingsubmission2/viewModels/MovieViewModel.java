package com.rizky92.madedicodingsubmission2.viewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.rizky92.madedicodingsubmission2.pojo.Movies;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieViewModel extends ViewModel {
    private static final String API_KEY = "4b71618fab6c4526517f0f17c5809762";
    private MutableLiveData<ArrayList<Movies>> listItems = new MutableLiveData<>();

    public LiveData<ArrayList<Movies>> getListItems() { return listItems; }

    public void setListItems() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movies> list = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject object = new JSONObject(result);
                    JSONArray array = object.getJSONArray("results");
                    Log.d("Response: ", String.format("%s", statusCode));
                    Log.d("Results: ", result);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject movie = array.getJSONObject(i);

                        Movies movies = new Movies();
                        movies.setTitle(movie.getString("title"));
                        movies.setDate(movie.getString("release_date"));
                        movies.setDesc(movie.getString("overview"));
                        movies.setLength(movie.getString("runtime"));
                        //movies.setPoster_path(movie.getString("poster_path"));

                        list.add(movies);
                    }
                    listItems.postValue(list);
                } catch (Exception e) {
                    Log.d("Fail: ", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Fail: ", error.getMessage());
            }
        });
    }
}
