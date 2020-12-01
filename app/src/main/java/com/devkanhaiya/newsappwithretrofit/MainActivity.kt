package com.devkanhaiya.newsappwithretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.*


const val BASE_URL = "https://newsapi.org"

class MainActivity : AppCompatActivity() {

    lateinit var countDownTimer : CountDownTimer
     private var titleList = mutableListOf<String>()
     private var detailsList = mutableListOf<String>()
     private var imageList = mutableListOf<String>()
     private var linksList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        makeapirequest()
    }
    private fun setUprecyclerView(){

        rv_recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        rv_recyclerView.adapter = RecyclerViewAdapter(titleList,detailsList,imageList,linksList)

    }
    private fun  addToList(title:String, details:String, images:String, links:String){
        titleList.add(title)
        detailsList.add(details)
        imageList.add(images)
        linksList.add(links)
    }

    private fun fadeInBlackOut(){
        v_blackScreen.animate().apply {
            alpha(0f)
            duration = 3000

        }.start()
    }


    private fun makeapirequest() {

        progressBar.visibility = View.VISIBLE
        val api= Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Apirequest::class.java)

        GlobalScope.launch(Dispatchers.IO){
            try {
                val response = api.getNews()

                for (article in response.articles){

                    Log.i("MainkANHAIYA","response = $article")

                    addToList(article.title, article.description,article.urlToImage,article.url)

                }
                withContext(Dispatchers.Main){

                    setUprecyclerView()
                    fadeInBlackOut()
                    progressBar.visibility = View.GONE
                }
            }catch (e: Exception){
                Log.e("MainActivity","Errer = ${e.toString()}")


            withContext(Dispatchers.Main) {
                attemptRequestAgain()

            }
            }

        }

    }

    private fun attemptRequestAgain() {
         countDownTimer = object : CountDownTimer(5 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Toast.makeText(
                    applicationContext,
                    "trying To Retrieve Datav ${millisUntilFinished/1000}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onFinish() {
                makeapirequest()
                countDownTimer.cancel()
            }

        }
        countDownTimer.start()
    }
}