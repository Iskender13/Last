package com.example.pixabay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pixabay.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val dataList = mutableListOf<ImageModel>() // Hit - это ваша модель данных
    private var adapter = ImageAdapter(dataList)
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initClickers()
        addClicker()
    }

    private fun addClicker() {
        with(binding){
            addBtn.setOnClickListener {
                RetrofitService.api.getImages(keyWord = imageEd.text.toString())
                    .enqueue(object : Callback<PixaModel>{
                        override fun onResponse(
                            call: Call<PixaModel>,
                            response: Response<PixaModel>
                        ) {
                            if(response.isSuccessful){
                                response.body()?.let { pixaModel ->
                                    dataList.addAll(pixaModel.hits) // Добавляем новые данные
                                    adapter.notifyDataSetChanged() // Уведомляем адаптер об изменениях
                                }
                            }
                        }

                        override fun onFailure(call: Call<PixaModel>, t: Throwable) {
                            Log.e("ololo", "onFailure: ${t.message}")
                        }
                    })
            }
        }

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
                                    adapter= ImageAdapter(it.hits)
                                    imageRecycler.adapter=adapter
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