package com.example.rk_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var currency : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewManager = LinearLayoutManager(this)
        viewAdapter = TrackListAdapter(applicationContext)
        (viewAdapter as TrackListAdapter).data = listOf()

        val apiInterface = API.retrofitService.getData("BTC", "USD","15");

        apiInterface.enqueue( object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>?, response: Response<ResponseData>?) {
                if(response?.body() != null) {
                    println(response.body()!!.Data.DataList.size)
                    val dataList = response.body()!!.Data.DataList
                    dataList.forEach{
                        it.currency="USD"
                    }
                    (viewAdapter as TrackListAdapter).data = dataList

                    (viewAdapter as TrackListAdapter).notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<ResponseData>?, t: Throwable?) {
                println("FAILED")
                println(t)
                println("-------")
            }
        })
        recyclerView = findViewById<RecyclerView>(R.id.trackByDays).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

    }

    fun getTrack(name:String){

    }

}