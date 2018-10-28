package com.arctouch.codechallenge.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.api.TmdbApi
import retrofit2.Call
import retrofit2.Response

class GenreRepository(private val api: TmdbApi) {

    fun fetchGenres(): LiveData<GenreResponse> {
        val data = MutableLiveData<GenreResponse>()
        var call: Call<GenreResponse> = api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
        call.enqueue(object : retrofit2.Callback<GenreResponse> {
            override fun onResponse(call: Call<GenreResponse>?, response: Response<GenreResponse>?) {
                if (response?.body() != null) {
                    Cache.cacheGenres(response?.body()!!.genres)
                    data.value = response?.body()
                }
            }

            override fun onFailure(call: Call<GenreResponse>?, t: Throwable?) {
                Log.e("GenreRepository","An exception error occurred due to ${t?.localizedMessage}")
            }

        })
        return data
    }
}