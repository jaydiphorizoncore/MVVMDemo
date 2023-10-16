package com.example.mvvmdemo.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmdemo.RetrofitInstance
import com.example.mvvmdemo.model.Movies
import com.example.mvvmdemo.model.Result
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MovieViewModel : ViewModel() {
    private val movieLiveData = MutableLiveData<List<Result>>()
    fun getPopularMovies() {
        RetrofitInstance.api.getPopularMovies("69d66957eebff9666ea46bd464773cf0")
            .enqueue(object : Callback,
                retrofit2.Callback<Movies> {
                override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                    if (response.body() != null) {
                        movieLiveData.value = response.body()!!.results
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<Movies>, t: Throwable) {
                    Log.e("TAG", "onFailure:${t.message.toString()}")
                }
            })
    }

    fun observeMovieLiveData(): LiveData<List<Result>> {
        return movieLiveData
    }
}
