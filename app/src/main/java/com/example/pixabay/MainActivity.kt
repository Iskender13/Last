package com.example.pixabay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pixabay.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var adapter = ImageAdapter(mutableListOf())
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initClickers()
    }



    private fun initClickers() {
        with(binding){
            searchBtn.setOnClickListener {
                RetrofitService.api.getImages(keyWord = imageEd.text.toString())
                    .enqueue(object : Callback<PixaModel>{
                        override fun onResponse(
                            call: Call<PixaModel>,
                            response: Response<PixaModel>
                        ) {
                            if(response.isSuccessful){
                                response.body()?.let {
                                    adapter.putImages(it.hits)
                                }
                            }
                        }

                        override fun onFailure(call: Call<PixaModel>, t: Throwable) {
                            Log.e("ololo","onFailure: ${t.message}" )
                        }

                    })
            }
        }
    }
}