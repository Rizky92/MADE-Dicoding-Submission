package com.rizky92.madedicodingsubmission2.viewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.rizky92.madedicodingsubmission2.pojo.Tvs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvViewModel extends ViewModel {

    private static final String API_KEY = "4b71618fab6c4526517f0f17c5809762";
    private MutableLiveData<ArrayList<Tvs>> listItems = new MutableLiveData<>();

    public LiveData<ArrayList<Tvs>> getListItems() {
        return listItems;
    }

    public void setListItems() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Tvs> list = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject object = new JSONObject(result);
                    JSONArray array = object.getJSONArray("results");
                    Log.d("Response: ", String.format("%s", statusCode));
                    Log.d("Results", result);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject tv = array.getJSONObject(i);

                        Tvs tvs = new Tvs();
                        tvs.setTitle(tv.getString("name"));
                        tvs.setDate(tv.getString("first_air_date"));
                        tvs.setDesc(tv.getString("overview"));
                        tvs.setVoteAverage((float) tv.getDouble("vote_average"));
                        tvs.setVoteCount(tv.getInt("vote_count"));
                        tvs.setPopularity(tv.getString("popularity"));
                        tvs.setLanguage(tv.getString("original_language"));
                        tvs.setPosterPath(String.format("%s%s", "https://image.tmdb.org/t/p/w780", tv.getString("poster_path")));

                        list.add(tvs);
                    }
                    listItems.postValue(list);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Fail: ", error.getMessage());
            }
        });
    }
}
